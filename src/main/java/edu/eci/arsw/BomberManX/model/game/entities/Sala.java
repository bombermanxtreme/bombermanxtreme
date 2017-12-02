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

    private Jugador creador;
    private String nombre;
    private boolean equipos;
    private boolean friendFire;
    private boolean casiLista;
    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private ArrayList<Jugador> jugadoresListos = new ArrayList<Jugador>();
    private ArrayList<Integer> idJugadoresListos = new ArrayList<Integer>();

    public Sala(Jugador creador, String nombre, boolean equipos, boolean friendFire) {
        this.creador = creador;
        this.nombre = nombre;
        this.equipos = equipos;
        this.friendFire = friendFire;
    }

    public void addJugador(Jugador jugador) {
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
}
