/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.bombermanx.model.game.entities;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Casilla {
    
    private ArrayList<Elemento> elementos;

    public Casilla() {
    }
    
    /**
     * reemplaza todos los elementos que tenga la casilla
     * @param e 
     */
    public void reemplazar(Elemento e){
        elementos=new ArrayList<>();
        add(e);
    }
    
    /**
     * agrega el elemento a la casilla
     * @param e 
     */
    public void add(Elemento e){
        elementos.add(e);
    }
    
    public boolean tieneTipo(Class<?> tipo){
        boolean res=false;
        for (Iterator<Elemento> e = elementos.iterator(); e.hasNext();) {
            if(e.next().getClass()==tipo){
                res=true;
                break;
            }
        }
        return res;
    }
    
    public Object getTipo(Class<?> tipo){
        Object res=null;
        for (Iterator<Elemento> e = elementos.iterator(); e.hasNext();) {
            Elemento ele=e.next();
            if(ele.getClass()==tipo){
                res=ele;
                break;
            }
        }
        return res;
    }
    
    public Elemento get(){
        return elementos.get(0);
    }
    
    public int size(){
        return elementos.size();
    }
    
    public ArrayList<Elemento> getAll(){
        return elementos;
    }
}
