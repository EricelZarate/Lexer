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
public class Expresion {
    private String tipo;
    private ArrayList<Token> tokens = new ArrayList<Token>();
    
    @Override
    public String toString()
    {
        String st= tipo +" : ";
        for(Token t : tokens) st=st+t.getCategoria()+" ";
        return st;
    }
    
    
    public Expresion(String tipo){
        this.tipo = tipo;
    }
    public Token getTokenAt(int i){
        return tokens.get(i);
    }
    public void addToken(Token tok){
        tokens.add(tok);
    }
    public String getTipo(){
        return tipo;
    }
    public int size(){
        return tokens.size();
    }
    
    String comparacion = "[<=>][=]",
                comentario = "(#.*(\n)*)",
                id = "([(a-z)(A-Z)](\\w)*)",
                num = "((\\d)+)",
                dec = "((\\d)+(\\.)(\\d)+)",
                text = "((((#)[.\\W\\w\\s]*(#))|("+id+"))((\\s)*(\\+)((\\s)*((#)[.\\W\\w\\s]*(#))|("+id+")))*[\n]*)",
                imprime = "((\\s)*imprimir(\\s)*((((¿)[.\\W\\w\\s]*(?))|("+id+"))((\\s)*(\\+)((\\s)*((#)[.\\W\\w\\s]*(#))|("+id+")))*)(\\s)*(\n)*)",
                operaciones = "(("+id+"|"+num+"|"+dec+")(\\s)*([+-/*](\\s)*("+id+"|"+num+"|"+dec+"))+)",
                defVal = "((\\s)*"+id+"(\\s)*=(\\s)*("+id+"|"+text+"|"+operaciones+"|"+num+"|"+dec+")(\\s)*)",
                defValVar = "((\\s)*"+id+"(\\s)*=(\\s)*("+id+"|"+text+"|"+operaciones+"|"+num+"|"+dec+")(\\s)*)",
                var = "((\\s)*("+id+"|"+defValVar+")((\\s)*(,(\\s)*("+id+"|"+defValVar+")))*(\\s)*)",
                condicion = "("+id+"|"+num+"|"+dec+")(\\s)*"+comparacion+"(\\s)*("+id+"|"+num+"|"+dec+")((\\s)*([(&&)(||)](\\s)*"+id+"(\\s)*"+comparacion+"(\\s)*("+id+"|"+num+"|"+dec+")))*",
                main = "((\\s)*origen(\\b)(\\s)*inicio[.\\W\\w\\s]*fin(\\s)*$)",
                princ = "(\\s)*origen(\\b)(\\s)*inicio(\\s)*",
                inicio = "((\\s)*inicio(\\s)*)",
                fin = "((\\s)*fin(\\s)*(\\n)*)",
                iniciosi = "((\\s)*si(\\s)*(\\¿)(\\s)*"+condicion+"(\\s)*(\\?)(\\s)*(inicio)?(\\s)*)",
                //finsi = "((\\s)*"+fin+"*(\\s)*)",
                 entero = "[0-9]*",
                decimal = "[0-9]*.[0-9]+",
                salto = "(\n)*";
    
}
