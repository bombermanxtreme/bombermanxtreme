/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Jugador {
    private String nombre;
    private String correo;
    private int record;

    public Jugador(String nombre,String correo,int record) {
        this.nombre = nombre;
        this.correo = correo;
        this.record = record<0?0:record;
    }

}
