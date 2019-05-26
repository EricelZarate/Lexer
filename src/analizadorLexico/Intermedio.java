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
public class Intermedio {
    ArrayList<Expresion> exp;
    ArrayList<Temp> temp;
    int conL=0; //Contador de etiquetas
    int conT=0; //Contador de temporales
    int lab=0;
    int conSN=0;
    
    public Intermedio(ArrayList<Expresion> exp){
        this.exp=exp;    
        temp=new ArrayList<Temp>();
    }
    
    public ArrayList<String> generar(){
        ArrayList<String> codigo = new ArrayList<String>();
        for(Expresion e:exp){
            String tipo=e.getTipo();
            if(tipo.equals("asigndirecta"))               
                codigo.add(e.getTokenAt(0).getLexema()+"="+e.getTokenAt(2).getLexema());                
            else if(tipo.equals("imprimir"))
                codigo.add("imprimir("+e.getTokenAt(2).getLexema()+")");            
            else if(tipo.equals("iniciosi")){
                codigo.add("T"+conT+"="+e.getTokenAt(2).getLexema()+e.getTokenAt(3).getLexema()+e.getTokenAt(4).getLexema());               
                codigo.add("if false T"+conT+" goto L"+conL);
                conT++;                
                conL++;
                lab=conL;}
            else if(tipo.equals("finsi")){
                if(conSN==0){
                codigo.add("label L"+(lab-1));
                }
                else{
                    conSN--;                    
                    codigo.add("label L"+(lab));
                }
            }
            else if(tipo.equals("sino")){
                String aux =codigo.get(codigo.size()-1);
                codigo.remove(codigo.size()-1);
                codigo.add("goto L"+(conL++));
                codigo.add(aux);
                conSN++;}
            else if(tipo.equals("asigncomplex")){
                if(e.size()<=5){
                    codigo.add(e.getTokenAt(0).getLexema()+"="+e.getTokenAt(2).getLexema()+e.getTokenAt(3).getLexema()+e.getTokenAt(4).getLexema());                
                }else{                    
                    int ax=5;
                    codigo.add("T"+conT+"="+e.getTokenAt(2).getLexema()+e.getTokenAt(3).getLexema()+e.getTokenAt(4).getLexema());
                    while(ax<e.size()-2)                   
                        codigo.add("T"+conT+"="+"T"+conT+e.getTokenAt(ax++).getLexema()+e.getTokenAt(ax++).getLexema());                    
                    codigo.add(e.getTokenAt(0).getLexema()+"="+"T"+conT+e.getTokenAt(ax++).getLexema()+e.getTokenAt(ax++).getLexema());                  
                }
                conT++;
            }
        }               
        return codigo;
    }
   
    public int existe(Token t){
    int pos=0;
    for(Temp ls: temp){
       if(ls.getVariable().equals(t.getLexema()))
           return pos;
       pos++;
    }
    return -1;
}
        }