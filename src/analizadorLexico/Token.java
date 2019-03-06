package analizadorLexico;

/**
 * Clase encargada de contener los atributos y constantes que son los tokens
 * para el analizador lexico
 */
public class Token {
    /**
     * Variables de tipo String categoria del token y el lexema el valor de los tokens
     */
    private String categoria,lexema;
    /**
     * Variables de tipo entero linea y linea siguiente que tendran el valor de la linea donde 
     * el token
     */
    private Integer linea,lineaSiguiente;
    /**
     * Variables constantes de tipo String para los tokens del anaizador
     */
   public static final String PALABRAS_RESERVADAS ="Palabra reservada";
   public static final String VALORES_NUMERICOS ="Numerico";
   public static final String OPERADORES_MATEMATICOS ="Operador Matematico";
   public static final String OPERADOR_ASIGNACION ="Operador de Asignacion";
   public static final String CADENA_PALABRAS ="Cadena de palabra";
   public static final String TIPOS_VARIABLES ="Variable";
   public static final String NOMBRES_DE_VARIABLES ="Nombre de variable ";
   public static final String SIMBOLOS ="Simbolo ";
    public static final String SIMBOLOS_NO_RECONOCIDOS ="Simbolo no reconocido ";
    public static final String METODO_IMPRIMIR="Metodo de imprimir";
    public static final String LETRA="Letra";
     public static final String COMENTARIO="comentario";
     public static final String SALTO="Salto de línea";
     public static final String COMPARACIONES="Operadores de comparacion";
          public static final String LLAVES="Llaves de inicio y cierre";

    
   
   
    /**
     * Metodo constructor de la clase TokensAnalizador
     * @param categoria
     * @param lexema
     * @param linea
     * @param lineaSiguiente 
     */
    public Token(String categoria, String lexema, Integer linea, Integer lineaSiguiente) {
        this.categoria = categoria;
        this.lexema = lexema;
        this.linea = linea;
        this.lineaSiguiente = lineaSiguiente;
    }
    
    /**
     * Metodos getters y setters de los atributos
     */
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public Integer getLineaSiguiente() {
        return lineaSiguiente;
    }

    public void setLineaSiguiente(Integer lineaSiguiente) {
        this.lineaSiguiente = lineaSiguiente;
    }


   

   
   
    
    
}
