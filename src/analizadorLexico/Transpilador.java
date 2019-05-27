/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorLexico;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Rogelio Ericel
 */
public class Transpilador {
    ArrayList<String> codigo;
    ArrayList<String> python;
    int si=0;
    int sino=0;
    int nesT=0;
    
    public Transpilador(ArrayList<String> codigo){
        this.codigo=codigo;
    }
    
    public ArrayList<String> transpila(){
        python=new ArrayList<String>();
        String temp="";
        
        for(String s: codigo){
            String linea="";
            for(int x=0;x<si;x++) linea+="\t";
            if(s.startsWith("imprimir(")){
                linea+="print("+s.substring(9);
                python.add(linea);
            }                    
            //"=>","=<","!=","==","<",">"
            else if(s.contains("=>")||s.contains("=<")||s.contains("!=")||s.contains("==")||s.contains(">")||s.contains("<")){
                temp=s.substring(s.indexOf("=")+1);
            }   
            else if(s.startsWith("if ")){
                linea+="if "+temp+":";
                temp="";
                python.add(linea);
                si++;
            }
            else if(s.startsWith("goto")){
                sino++;
            }
            else if(s.startsWith("label")){
                if(si>0){                    
                    if(sino>0){
                    linea=linea.substring(0,linea.length()-1)+"else:";
                    python.add(linea);
                    sino--;}
                    else si--;
                }
            }
            else if(!s.startsWith("T")){
                if(nesT>0){
                    linea+=s.substring(0,s.indexOf("="))+temp;
                    if(s.contains("+"))
                        temp=s.substring(s.indexOf("+"));
                    else if(s.contains("-"))
                        temp=s.substring(s.indexOf("-"));
                    else if(s.contains("*"))
                        temp=s.substring(s.indexOf("*"));
                    else if(s.contains("/"))
                        temp=s.substring(s.indexOf("/"));
                    linea+=temp;
                    temp="";
                    nesT=0;
                }
                else 
                    linea+=s;
                python.add(linea);                
            }          
            else{                
                nesT++;
                if(nesT>1){
                    if(s.contains("+"))
                        temp+=s.substring(s.indexOf("+"));
                    else if(s.contains("-"))
                        temp+=s.substring(s.indexOf("-"));
                    else if(s.contains("*"))
                        temp+=s.substring(s.indexOf("*"));
                    else if(s.contains("/"))
                        temp+=s.substring(s.indexOf("/"));
                }
                else
                    temp+=s.substring(s.indexOf("="));
                
            }
            
        }
        return python;
    }
       
}
