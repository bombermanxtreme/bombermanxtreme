/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

import java.io.Serializable;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Jugador implements Serializable {
    private int id;
    private String nombre;
    private String correo;
    private String clave;
    private int record;

    public Jugador(String nombre,String correo,String clave) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.record = record<=0?0:record;
    }

    public String getNombre() {
        return nombre;
    }

    public int getRecord() {
        return record;
    }

    
    
    @Override
    public String toString() {
        return "{\"nombre\":\"" + nombre + "\", \"record\":\"" + record + "\"}";
    }

    public boolean getId(int id_jugador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
