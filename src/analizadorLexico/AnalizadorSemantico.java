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
public class AnalizadorSemantico {
    ArrayList<Expresion> arr;
    ArrayList<Variable> var = new ArrayList<Variable>();
    String errcode= "Semantico OK";
    
    public AnalizadorSemantico(ArrayList<Expresion> arr){
        this.arr=arr;
    }
    public boolean analizar(){
        int i=0;
        for (Expresion e : arr){
            if(e.getTipo().equals("asigndirecta")){
                if(e.getTokenAt(2).getCategoria().equals(Token.NOMBRES_DE_VARIABLES)){
                    int def = definida(e.getTokenAt(2));
                    if(def!=-1){
                        int pos =definida(e.getTokenAt(0));
                        if(pos!=-1){
                            Variable v =var.get(pos);
                            if(var.get(def).getTipo().equals("cadena")){
                                v.setTipo(2);
                                v.setValor(var.get(def).getValor());
                            }else{
                            if(var.get(def).getTipo().equals("entero")){
                                v.setTipo(0);
                                v.setValor(var.get(def).getValor());
                            }
                        } var.set(pos, v);
                    } else {
                            if(var.get(def).getTipo().equals("cadena")){
                                Variable v = new Variable(2,var.get(def).getValor(),e.getTokenAt(0).getLexema());
                                var.add(v);
                            }
                            else if(var.get(def).getTipo().equals("entero")){
                                    Variable v = new Variable(0,var.get(def).getValor(),e.getTokenAt(0).getLexema());
                                    var.add(v); 
                        }   
                        }
                    }else{
                        errcode="Variable "+e.getTokenAt(2).getLexema()+" no definida";
                        return false;
                    }
                           
                }
                else{
                    int pos = definida(e.getTokenAt(0));
                    if(pos!=-1){
                        Variable v =var.get(pos);
                        if(e.getTokenAt(2).getCategoria().equals(Token.CADENA_PALABRAS)){
                            v.setTipo(2);
                            v.setValor(e.getTokenAt(2).getLexema());
                        } else {
                            if(!e.getTokenAt(2).getLexema().contains(".")){
                                v.setTipo(0);
                                v.setValor(e.getTokenAt(2).getLexema());
                            } else {
                                errcode="Valores decimales no soportados";
                                return false;
                            }
                        }
                        var.set(pos, v);
                    }else{
                        if(e.getTokenAt(2).getCategoria().equals(Token.CADENA_PALABRAS)){
                            Variable v = new Variable(2,e.getTokenAt(2).getLexema(),e.getTokenAt(0).getLexema());
                            var.add(v);
                        }
                        else {
                            if(!e.getTokenAt(2).getLexema().contains(".")){
                                Variable v = new Variable(0,e.getTokenAt(2).getLexema(),e.getTokenAt(0).getLexema());
                                var.add(v);
                            } else {
                                errcode="Valores decimales no soportados";
                                return false;
                            }
                        }
                    }                    
                }
            }
            i++;
        }
        return true;
    }
    
    public int definida(Token vari){
        int x=0;
        for(Variable v : var){
            if(vari.getLexema().equals(v.getNombre()))
                return x;
            x++;
        }
        return -1;
    }

    public String getErrCode() {
        return errcode;
    }

    public void dumpVar() {
        for(Variable v : var){
            System.out.println("Token Name: "+v.getNombre()+"\tToken Type: "+v.getTipo()+"\tToken Value: "+v.getValor());
        }
    }
    
}