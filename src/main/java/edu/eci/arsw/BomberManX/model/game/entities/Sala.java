/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

import edu.eci.arsw.BomberManX.model.game.Juego;
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
    //minimo de jugadores "listos" que se necesitan en la sala para jugar
    private static final int MINIMOJUGADORES = 2;
    //tiempo en segundos, máximo que tienen los jugadores que no están listos en la sala para entrar al juego
    public static final int TIEMPOENSALAPARAEMPEZAR = 10; //segundos

    /**
     * constructor
     * @param id
     * @param creador
     * @param nombre
     * @param equipos
     * @param friendFire 
     */
    public Sala(int id,Jugador creador, String nombre, boolean equipos, boolean friendFire) {
        this.id = id;
        this.creador = creador;
        this.nombre = nombre;
        this.equipos = equipos;
        this.friendFire = friendFire;
    }

    /**
     * agrega un jugador a la sala SI NO HA SIDO AGREGADO
     * @param jugador 
     */
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

    /**
     * retorna los jugadores que están en la sala
     * @return 
     */
    public ArrayList<Jugador> getJugadores() {
        System.out.println("++++++++++++++++++++++++ Numero de jugadores: " + jugadores.size());
        return jugadores;
    }
    
    /**
     * retorna si está casi lista o faltan jugadores que estén listos para empezar
     * @return 
     */
    public boolean casiLista(){
        return casiLista;
    }
    
    /**
     * retorna los jugadores listos
     * @return 
     */
    public ArrayList<Jugador> getJugadoresListos(){
        return jugadoresListos;
    }

    /**
     * establece que la sala está casi lista, es decir que los estudiantes mínimos que están listos lo estén
     */
    public void lista() {
        casiLista=true;
    }

    /**
     * agrega que un jugador ya está listo
     * @param jugadorListo 
     */
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

    /**
     * retorna true si la sala acaba de cerrarse y pasa a estar casi lista
     * @return 
     */
    public boolean cerrarSala() {
        //si ya están los jugadores mínimos requeridos para empezar
        if (!casiLista && jugadoresListos.size() >= MINIMOJUGADORES) {
            casiLista=true;
            return true;
        }
        return false;
    }
}
