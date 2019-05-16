/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizadorLexico;

/**
 *
 * @author Rogelio Ericel
 */
public class Variable {
    private final String[] tipos = {"entero","decimal","cadena"};
    private String tipo,valor,nombre;

    public Variable(int tipo, String valor, String nombre) {
        this.tipo = tipos[tipo];
        this.valor = valor;
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipos[tipo];
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
       
}    
