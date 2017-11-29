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

    public static final int ARRIBA = 0;
    public static final int ABAJO = 1;
    public static final int DERECHA = 2;
    public static final int IZQUIERDA = 3;
    public static final int ANCHO = 15;
    public static final int ALTO = 10;
    //minimo de jugadores "listos" que se necesitan en la sala para jugar
    public static final int MINIMOJUGADAORES = 2;
    //tiempo en segundos, máximo que tienen los jugadores que no están listos en la sala para entrar al juego
    public static final int TIEMPOENSALAPARAEMPEZAR = 10; //segundos
    private int tiempo;
    private Jugador[] jugadores;
    private Casilla[][] tablero;

    public Juego(Jugador[] jugadores) {
        this.jugadores = jugadores;
        tablero = new Casilla[ALTO][ANCHO];
        tiempo = 0;
    }
    
    /**
     * Recibe el numero de casilla y revisa que la coordenada del tablero no este ocuapda
     * @param casilla
     * @return 
     */
    public boolean hay_objeto(int casilla){
        int[] coor = get_coordenada(casilla);
        
        return tablero[coor[0]][coor[1]].hay_objeto();
    }
    
    /**
     * Convierte las coordenadas del tablero a una posicion 
     * @param fila coordenada
     * @param columna coodenada
     * @return 
     */
    public int get_posicion(int fila, int columna){
    int posicion = fila*ANCHO+columna;
        
    return posicion;
    }
    
    public int[] get_coordenada(int casilla){
        int[] coor = new int[2];
        coor[0] = (int)(casilla/ANCHO);; //fila
        coor[1] = casilla%ANCHO;;  //columna
        return coor;
    }
    
}
