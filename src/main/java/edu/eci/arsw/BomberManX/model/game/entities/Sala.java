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

    private ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    private ArrayList<Integer> idJugadoresListos = new ArrayList<Integer>();

    public void addJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public ArrayList<Jugador> getJugadores() {
        System.out.println("++++++++++++++++++++++++ Numero de jugadores: " + jugadores.size());
        return jugadores;
    }
}
