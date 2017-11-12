/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Jugador {

    private String nombre;
    private String correo;
    private String apodo;
    private String clave;
    private String imagen;
    private int record;

    /*
    private String apodo;
    private String imagen;
     */
    public Jugador(String nombre, String correo, String apodo, String clave, String imagen) {
        this.nombre = nombre;
        this.correo = correo;
        this.apodo = apodo;
        this.clave = clave;
        this.imagen = imagen;
        this.record = record <= 0 ? 0 : record;        
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

    public int getRecord() {
        return record;
    }

   
    public String getApodo() {
        return apodo;
    }

    public String getImagen() {
        return imagen;
    }   
     
    @Override
    public String toString() {
        //return "{\"nombre\":\"" + nombre + "\", \"record\":\"" + record + "\"}";
        return "{\"Nombre\":\"" + nombre + "\", \"Record\":\"" + record + "\", Correo\":\"" + "\", Apodo\":\"" + apodo + "\"}";        
    }
}
