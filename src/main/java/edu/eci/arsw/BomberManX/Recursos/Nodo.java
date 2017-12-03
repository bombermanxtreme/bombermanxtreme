/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Recursos;


/**
 * Clase que define los elementos que debe tener un Nodo de la lista.
 * @author xavier
 */
public class Nodo {
    // Variable en la cual se va a guardar el valor.
    private Object valor;
    // Variable para enlazar los nodos.
    private Nodo siguiente;
    /**
     * Constructor que inicializamos el valor de las variables.
     */
    public void Nodo(){
        this.valor = new Object();
        this.siguiente = null;
    }
    
    // Métodos get y set para los atributos.
    
    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }   
}