/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorLexico;

import java.util.ArrayList;

/**
 *
 * @author Rogelio Ericel
 */
public class AnalizadorSintactico {
    private ArrayList<Token> tokens;
    private String errcode = "Analisis OK";
    ArrayList<Expresion> ex=new ArrayList<Expresion>();
    int i=0;
    public AnalizadorSintactico(ArrayList<Token> tok){
        tokens=tok;
    }
    public void imprimeExp(){
        for(Expresion exp : ex){
            System.out.println(exp.toString());
        }
    }
    public ArrayList<Expresion> analizar(){
        if (tokens.get(0).getLexema().equals("origen")){
            if((tokens.get(1).getLexema().equals("inicio"))&&(tokens.get(tokens.size()-1).getLexema().equals("fin"))){
                Expresion e = new Expresion("origen");
                e.addToken(tokens.get(0));
                e.addToken(tokens.get(1));
                ex.add(e);
            }
            i = 2;
            while(i<tokens.size()-1&&errcode.equals("Analisis OK")){
                if(tokens.get(i).getLexema().equals("imprimir")){
                    if(tokens.get(i+1).getLexema().equals("¿")){
                        if((tokens.get(i+2).getCategoria().equals(Token.CADENA_PALABRAS))||(tokens.get(i+2).getCategoria().equals(Token.NOMBRES_DE_VARIABLES))||(tokens.get(i+2).getCategoria().equals(Token.VALORES_NUMERICOS))){
                            if(tokens.get(i+3).getLexema().equals("?")){
                                Expresion e = new Expresion("imprimir");
                                    e.addToken(tokens.get(i));
                                    e.addToken(tokens.get(i+1));
                                    e.addToken(tokens.get(i+2));
                                    e.addToken(tokens.get(i+3));
                                    ex.add(e);
                                    i=i+3;
                            } else errcode="Error en el metodo imprimir: "+tokens.get(i).getLineaSiguiente();
                } else errcode="Error en el metodo imprimir: "+tokens.get(i).getLineaSiguiente();
              } else errcode="Error en el metodo imprimir: "+tokens.get(i).getLineaSiguiente();
            }
                else if(tokens.get(i).getLexema().equals("si")){
                    if(tokens.get(i+1).getLexema().equals("¿")){
                        if((tokens.get(i+2).getCategoria().equals(Token.NOMBRES_DE_VARIABLES))||(tokens.get(i+2).getCategoria().equals(Token.VALORES_NUMERICOS))){
                            if(tokens.get(i+3).getCategoria().equals(Token.COMPARACIONES)){
                                if((tokens.get(i+4).getCategoria().equals(Token.NOMBRES_DE_VARIABLES))||(tokens.get(i+4).getCategoria().equals(Token.VALORES_NUMERICOS))){
                                    if(tokens.get(i+5).getLexema().equals("?")){
                                        if(tokens.get(i+6).getLexema().equals("inicio")){
                                            Expresion e = new Expresion("iniciosi");
                                                e.addToken(tokens.get(i));
                                                e.addToken(tokens.get(i+1));
                                                e.addToken(tokens.get(i+2));
                                                e.addToken(tokens.get(i+3));
                                                e.addToken(tokens.get(i+4));
                                                e.addToken(tokens.get(i+5));
                                                e.addToken(tokens.get(i+6));
                                                ex.add(e);
                                                i=i+6;
                                        } else errcode="Error en el si : "+tokens.get(i).getLineaSiguiente();
                                    } else errcode="Error en el si : "+tokens.get(i).getLineaSiguiente();
                                } else errcode="Error en el si : "+tokens.get(i).getLineaSiguiente();
                            } else errcode="Error en el si : "+tokens.get(i).getLineaSiguiente();
                        } else errcode="Error en el si : "+tokens.get(i).getLineaSiguiente();
                    } else errcode="Error en el si : "+tokens.get(i).getLineaSiguiente();
                }else if (tokens.get(i).getLexema().equals("sino")){
                    if(tokens.get(i+1).getLexema().equals("inicio")){
                        Expresion e = new Expresion("sino");
                        e.addToken(tokens.get(i));
                        e.addToken(tokens.get(i+1));
                        ex.add(e);
                        i++;
                    } else errcode=" Si no mal definido : "+tokens.get(i).getLineaSiguiente();
                }else if (tokens.get(i).getLexema().equals("fin")){
                    int nmsi =0;
                    int nmfin =0;
                    for (Expresion e : ex){
                        if((e.getTipo().equals("iniciosi"))||(e.getTipo().equals("sino")))
                            nmsi++;
                        else if(e.getTipo().equals("finsi"))
                            nmfin++;
                    }
                    if (nmsi>nmfin){
                        Expresion e = new Expresion("finsi");
                                    e.addToken(tokens.get(i));
                                    ex.add(e);
                    } else errcode="Error, finsi no esperado en : "+tokens.get(i).getLineaSiguiente();
                } else if (tokens.get(i).getCategoria().equals(Token.NOMBRES_DE_VARIABLES)){
                    if(tokens.get(i+1).getCategoria().equals(Token.OPERADOR_ASIGNACION)){
                        if(tokens.get(i+3).getCategoria().equals(Token.OPERADORES_MATEMATICOS)){
                            Expresion e = new Expresion("asigncomplex");
                            if ((tokens.get(i+2).getCategoria().equals(Token.CADENA_PALABRAS))||(tokens.get(i+2).getCategoria().equals(Token.NOMBRES_DE_VARIABLES))||(tokens.get(i+2).getCategoria().equals(Token.VALORES_NUMERICOS))){
                                if ((tokens.get(i+4).getCategoria().equals(Token.CADENA_PALABRAS))||(tokens.get(i+4).getCategoria().equals(Token.NOMBRES_DE_VARIABLES))||(tokens.get(i+4).getCategoria().equals(Token.VALORES_NUMERICOS))){                                
                                e.addToken(tokens.get(i));
                                e.addToken(tokens.get(i+1));
                                e.addToken(tokens.get(i+2));
                                e.addToken(tokens.get(i+3));
                                e.addToken(tokens.get(i+4));
                                } else errcode="Asignación mal definida : "+tokens.get(i).getLineaSiguiente();
                            } else errcode="Asignación mal definida : "+tokens.get(i).getLineaSiguiente();
                            int z= i+5;
                            while(tokens.get(z).getLineaSiguiente()==tokens.get(i).getLineaSiguiente()){
                            if(tokens.get(z).getCategoria().equals(Token.OPERADORES_MATEMATICOS)){
                                if((tokens.get(z+1).getLineaSiguiente()==tokens.get(i).getLineaSiguiente())&&((tokens.get(z+1).getCategoria().equals(Token.CADENA_PALABRAS))||(tokens.get(z+1).getCategoria().equals(Token.NOMBRES_DE_VARIABLES))||(tokens.get(z+1).getCategoria().equals(Token.VALORES_NUMERICOS)))){
                                    e.addToken(tokens.get(z));
                                    e.addToken(tokens.get(z+1));
                                    z++;
                                }else errcode= "Token esperado : " +tokens.get(i).getLineaSiguiente(); 
                                }else errcode= "Asignación mal definida : " +tokens.get(i).getLineaSiguiente();
                            z++;
                        }
                            ex.add(e);
                            i=z-1;
                        }else if ((tokens.get(i+2).getCategoria().equals(Token.CADENA_PALABRAS))||(tokens.get(i+2).getCategoria().equals(Token.NOMBRES_DE_VARIABLES))||(tokens.get(i+2).getCategoria().equals(Token.VALORES_NUMERICOS))){
                            Expresion e = new Expresion("asigndirecta");
                                                e.addToken(tokens.get(i));
                                                e.addToken(tokens.get(i+1));
                                                e.addToken(tokens.get(i+2));
                                                ex.add(e);
                                                i=i+2;
                        }
                    } else errcode = "Se esperaba asignación : " + tokens.get(i).getLineaSiguiente();
                } else errcode="Cadena inesperada en : " + tokens.get(i).getLineaSiguiente();
                i++;
            }
            int nmsi=0;
            int nmfin=0;
            for (Expresion e : ex){
                
                        if((e.getTipo().equals("iniciosi"))||(e.getTipo().equals("sino")))
                            nmsi++;
                        else if(e.getTipo().equals("finsi"))
                            nmfin++;
                    }
            if (nmsi!=nmfin){
                        errcode="Si o sino sin llave de cierre";
                    }
        }else {
            errcode="Inicio y Fin del programa no definidos";
            return null;
        } //Error de definición del programa
        return ex;
    }
    public String getErrCode(){
        return errcode;
    }

}