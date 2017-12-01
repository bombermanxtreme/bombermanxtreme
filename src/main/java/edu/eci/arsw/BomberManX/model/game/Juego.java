/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game;

import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.Elemento;
import java.util.ArrayList;

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
    public static final int MINIMOJUGADORES = 2;
    //tiempo en segundos, máximo que tienen los jugadores que no están listos en la sala para entrar al juego
    public static final int TIEMPOENSALAPARAEMPEZAR = 10; //segundos
    private int tiempo;
    private ArrayList<Jugador> jugadores;
    private Elemento[][] tablero;
    

    public Juego(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
        tablero = new Elemento[ALTO][ANCHO];
        tiempo = 0;
    }

    public boolean accionBomba(Jugador jugador){
        boolean pudo = false;
        
        jugador.getMan().accionBomba();
        
        
        return pudo;
    }
    
    public boolean mover(int id_jugador){
        return false;
    }
        
    /**
     * Revisa que fila y columna del tablero no este ocuapda
     * @param fila
     * @param columna
     * @return 
     */
    private boolean hay_objeto(int fila, int columna){        
        //return tablero[fila][columna].hay_elemento();
        return false;
    }
    
    /**
     * Retorna los tipos de lo que hay 
     * @param fila
     * @param columna
     * @return 
     */
    private Object[] hay_objeto_tipo(int fila, int columna){
        Object[] lista = new Object[2];
        return lista;
    }
    
}
