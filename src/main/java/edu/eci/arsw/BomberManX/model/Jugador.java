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
    private String clave;
    private int record;
    private int idSalaJugando;

    public Jugador(String nombre,String correo,String clave) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.record = record<=0?0:record;
        idSalaJugando=-1;
    }
    
    public boolean setIdSalaJugando(int idSalaJugando){
        boolean res=false;
        if(this.idSalaJugando==-1){
            this.idSalaJugando=idSalaJugando;
            res=true;
        }
        return res;
    }

    @Override
    public String toString() {
        return "{\"nombre\":\"" + nombre + "\", \"record\":\"" + record + "\", \"idSalaJugando\":\""+idSalaJugando+"\"}";
    }
}
