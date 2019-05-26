package analizadorLexico;

import java.util.ArrayList;

public class AnalizadorLexico {
   private static final String [] PALABRAS_RESERVADAS={
   "inicio","fin","imprimir","si","sino", "origen"
   };
  

   private static final String [] COMPARACIONES={
   "=>","=<","!=","==","<",">"
   };

   
   private ArrayList<String> arregloLineas;
   private int contLinea;

    public AnalizadorLexico(String texto) {
        try {
              arregloLineas = cargarLineas(texto);
        } catch (Exception e) {
        }
        contLinea = 0;
    }
    
    //CARGA EN UN ARREGLO LINEA POR LINEA DEL CÓDIGO, NO OMITE LÍNEAS EN BLANCO
    public ArrayList<String> cargarLineas(String texto){
        ArrayList<String> arr = new ArrayList<String>();
        while(texto.length()>0){
            if(texto.indexOf("\n")>0){
            arr.add(texto.substring(0,texto.indexOf("\n")));
            texto=texto.substring(texto.indexOf("\n")+1,texto.length());
            }else if(texto.indexOf("\n")==0){
                arr.add(texto.substring(0,texto.indexOf("\n")));
                texto=texto.substring(texto.indexOf("\n")+1,texto.length());}
            else{
                arr.add(texto);
                texto="";
            }            
        }
        return arr;
    }
    public ArrayList<Token> analizarCodigo(){
        ArrayList<Token> tokens=new ArrayList();
                
        for (String linea: arregloLineas) {
            contLinea++;
            Integer i=0;
            while(i < linea.trim().length()){
                Token token = obtenerTokens(arregloLineas ,linea.trim() , i );
                if(!token.getCategoria().equals(Token.COMENTARIO)) 
                    tokens.add(token);
                i=token.getLinea();                
             while (i < linea.trim().length() && linea.trim().charAt(i) == ' ') {
                 i++;
             }                          
        }
       //INSERTA UN TOKEN SALTO DE LÍNEA AL FINAL DE CADA LÍNEA
       //tokens.add(new Token(Token.SALTO,"\\n",j-1,j));
     } 
        return tokens;
    }
    //SE ENCARGA DE EJECUTAR LOS MÉTODOS PARA IDENTIFICAR LOS TOKENS, ORDENADOS
    //POR PRIORIDAD PARA EVITAR CONFUSIONES DEL ANALIZADOR
    public Token obtenerTokens(ArrayList<String> arregloArchivo,String linea,Integer i){
     Token token;
     
     token=obtenerComentarios(linea, i);
     if(token!=null){
         return token;
  }  
     token=obtenerComparacion(linea, i);
     if(token!=null){
         return token;
  }
    token=esLlave(linea, i);
     if(token!=null){
         return token;
  }      
     token=obtenerPalabraReservada(linea, i);
     if(token!=null){
         return token;
  }
     token = obtenerIdentificador(linea, i);
        if (token != null) {
            return token;
        }
     token = obtenerDigito(linea,i) ;
     if(token!=null){
       return  token;
  }            
        token = obtenerOperadorMatematico(linea, i);
        if (token != null) {
            return token;
        }        
        token = obtenerOperadorDeAsignacion(linea, i);
        if (token != null) {
            return token;
        }                
          token = obtenerCadena(linea, i);
        if (token != null) {
            return token;   
        }        
        token = obtenerNoReconocido(linea, i);
        return token;
  } 
    //PALABRA RESERVADA
    public Token obtenerPalabraReservada(String linea, Integer i) {
        String lexema="";
        if (esLetra(linea.charAt(i))) {
            Integer j =i;
            while (j < linea.length() && (esLetra(linea.charAt(j)))) {
                j++;
            }
            lexema=linea.substring(i,j);
            for(String s:PALABRAS_RESERVADAS)
                if(s.equals(lexema))
                    return new Token(Token.PALABRAS_RESERVADAS, lexema , j, contLinea);}
        return null;
    }
     //EL IDENTIFICADOR PUEDE EMPEZAR CON LETRAS SOLAMENTE
    private Token obtenerIdentificador(String linea, Integer i) {
        if (esLetra(linea.charAt(i))) {
            Integer j =i;
            while (j < linea.length() && (esLetra(linea.charAt(j))
                    || esDigito(linea.charAt(j)) )){
                j++;
            }
            String lexema =  linea.substring(i, j);
            return new Token(Token.NOMBRES_DE_VARIABLES, lexema, j, contLinea);
        }
        return null;
    }
    
    //IDENTIFICA SI ALGUN LEXEMA DE COMPARACIÓN SE ENCUENTRA EN LA LÍNEA
    public Token obtenerComparacion(String linea, Integer i) {
        for (String s : COMPARACIONES) {
            Integer k = 0;
            if (s.charAt(k) == linea.charAt(i)) {
                k++;
                Integer j = i + 1;
                while (k < s.length() && j < linea.length() && s.charAt(k) == linea.charAt(j)) {
                    j++;
                    k++;
                }
                String lexema =  linea.substring(i, j);
                if (s.equals(lexema))
                    return new Token(Token.COMPARACIONES, lexema , j, contLinea);
            }
        }
        return null;
    }
    
    
      public Token obtenerDigito(String linea, Integer i) {
          //IDENTIFICA VALORES NUMERICOS
          if (esDigito(linea.charAt(i))){ 
            Integer j = i + 1;
            while (j < linea.length() && esDigito(linea.charAt(j))) {//Que le sigan varios dígitos
                j++;
            }
            if (j < linea.length() && linea.charAt(j) == '.') {//Que siga un punto
                if (++j < linea.length() && esDigito(linea.charAt(j++)))//Seguido de un dígito
                    while (j < linea.length() && esDigito(linea.charAt(j)))//Que le sigan varios dígitos
                        j++;
                else
                    j--;
            }
            String lexema =  linea.substring(i, j);
            return new Token(Token.VALORES_NUMERICOS, lexema, j, contLinea);
        }
        //VALOR NUMERICO SI EMPIEZA POR '.'
        if (linea.charAt(i) == '.' && (i + 1 < linea.length()) && esDigito(linea.charAt(i + 1))) { //Que empiece por punto seguido de un dígito
            Integer j = i + 1;
            while (j < linea.length() && esDigito(linea.charAt(j))) {//Que le sigan varios dígitos
                j++;
            }
            String lexema =  linea.substring(i, j);
            return new Token(Token.VALORES_NUMERICOS, lexema, j, contLinea);
        }
        //SI EMPIEZA POR PUNTO PERO NO ES NUMERICO
        if(linea.charAt(i) == '.' && (i + 1 < linea.length()) && !esDigito(linea.charAt(i + 1))){
            int j=i+1;
            while(j<linea.length()){
                if(esLetra(linea.charAt(j)))
                j++;
                else break;
            }
            String lexema=linea.substring(i,j);
            return new Token(Token.SIMBOLOS_NO_RECONOCIDOS,lexema,j,contLinea);
        }
        return null;
    }
      //SI LA LINEA ES UN COMENTARIO
      //TAMBIEN SE COME EL RESTO DE LA LINEA SI APARECE A MITAD DE ESTA
      private Token obtenerComentarios(String linea, Integer i) {
          if (linea.charAt(i) == '#') {
            int j = linea.length();
            String lexema =  linea.substring(i, j);
            return new Token(Token.COMENTARIO, lexema, j, contLinea);
        }
        return null;
    }
     //SI ES UN OPERADOR MATEMATICO
      public Token obtenerOperadorMatematico(String linea, Integer i) {
        if (i < linea.length() && (linea.charAt(i) == '+'
                || linea.charAt(i) == '-' || linea.charAt(i) == '*'
                || linea.charAt(i) == '/')){
            Integer j = i + 1;
            String lexema =  linea.substring(i, j);
            return new Token(Token.OPERADORES_MATEMATICOS, lexema, j, contLinea);
        }
        return null;
    }
    //SI ES OPERADOR DE ASIGNACIÓN 
    public Token obtenerOperadorDeAsignacion(String linea, Integer i) {
        if (linea.charAt(i) == '=') {
            Integer j = i + 1;
            String lexema =  linea.substring(i, j);
            return new Token(Token.OPERADOR_ASIGNACION, lexema, j, contLinea);
        }
        return null;
    }
    //SI ES LLAVE
    public Token esLlave(String linea, Integer i) {
        if (linea.charAt(i) == '?'||linea.charAt(i) =='¿') {
            Integer j = i + 1;
            String lexema =  linea.substring(i, j);
            return new Token(Token.LLAVES, lexema, j, contLinea);
        }
        return null;
    }
    //SI ES COMENTARIO
      public Token obtenerComentario(String linea, Integer i) {
        if (linea.charAt(i) == '#') {
            Integer j = i+1;
            String lexema =  linea.substring(i, j);
            return new Token(Token.COMENTARIO, lexema, j, contLinea);
        }
        return null;
    }
    //SI ES UNA CADENA
    private Token obtenerCadena(String linea, Integer i) {
        if (linea.charAt(i) == '"') {
            Integer j = i + 1;
            while (j < linea.length() && linea.charAt(j) != '"') {
                j++;
            }
            if (j < linea.length() && linea.charAt(j++) == '"') {
                String lexema =  linea.substring(i, j);
                return new Token(Token.CADENA_PALABRAS, lexema, j, contLinea);
            }
        }
        return null;
    }

   //SI NO ES NINGUNO DE LOS ANTERIORES
    public Token obtenerNoReconocido(String linea, Integer i) {
        
        String lexema =  linea.substring(i, linea.length());
        int j = i+1;
        while(j<linea.length()){
            if((esLetra(lexema.charAt(j))||esDigito(lexema.charAt(j))))
            j++;
            else break;
        }
        lexema=linea.substring(i,j);
        return new Token(Token.SIMBOLOS_NO_RECONOCIDOS, lexema, j, contLinea);
    } 
    //IDENTIFICA SI EL CARACTER ES UN DIGITO
    public boolean esDigito(char caracter) {
        return caracter >= '0' && caracter <= '9';
    }
    
    //IDENTIFICA SI EL CARACTER ES UNA LETRA
    public boolean esLetra(char caracter) {
        return (caracter >= 'A' && caracter <= 'Z') || (caracter >= 'a' && caracter <= 'z');
    }
    
}
