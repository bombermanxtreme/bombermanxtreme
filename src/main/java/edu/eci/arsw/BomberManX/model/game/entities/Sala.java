/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

import java.util.ArrayList;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Sala {

    private int id;
    private Jugador creador;
    private String nombre;
    private boolean equipos;
    private boolean friendFire;
    private boolean casiLista;
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private ArrayList<Jugador> jugadoresListos = new ArrayList<Jugador>();
    private ArrayList<Integer> idJugadoresListos = new ArrayList<Integer>();

    public Sala(int id,Jugador creador, String nombre, boolean equipos, boolean friendFire) {
        this.id = id;
        this.creador = creador;
        this.nombre = nombre;
        this.equipos = equipos;
        this.friendFire = friendFire;
    }

    public void addJugador(Jugador jugador) {
        boolean yaExiste = false;
        //revisamos que aún no esté en la lista
        for (Jugador entry : jugadores) {
            if(entry.equals(jugador)){
                yaExiste=true;
                break;
            }
        }
        //si no está en la lista entonces lo agregamos
        if(!yaExiste)
        jugadores.add(jugador);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    public boolean casiLista(){
        return casiLista;
    }
    
    public ArrayList<Jugador> getJugadoresListos(){
        return jugadoresListos;
    }

    public void lista() {
        casiLista=true;
    }

    public void addJugadorListo(Jugador jugadorListo) {
        boolean yaExiste = false;
        //revisamos que aún no esté en la lista
        for (Jugador entry : jugadoresListos) {
            if(entry.equals(jugadorListo)){
                yaExiste=true;
                break;
            }
        }
        //si no está en la lista entonces lo agregamos
        if(!yaExiste)
        jugadoresListos.add(jugadorListo);
    }
    
    @Override
    public String toString() {
        return "{\"id\":\""+id+"\",\"nombre\":\""+nombre+"\",\"numJugadores\":\""+jugadores.size()+"\",\"casiLista\":\""+casiLista+"\"}";
    }
}
