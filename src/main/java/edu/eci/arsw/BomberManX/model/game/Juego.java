/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game;

import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.Casilla;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Juego {

    public static final int ARRIBA=0;
    public static final int ABAJO=1;
    public static final int DERECHA=2;
    public static final int IZQUIERDA=3;
    private int tiempo;
    private Jugador[] jugadores;
    private Casilla[][] tablero;
    
    public Juego(Jugador[] jugadores, int ancho, int alto) {
        this.jugadores=jugadores;
        tablero=new Casilla[ancho][alto];
        tiempo=0;
    }
}