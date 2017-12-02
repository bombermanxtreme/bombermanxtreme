/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game;

import edu.eci.arsw.BomberManX.model.game.entities.Bomba;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.Elemento;
import edu.eci.arsw.BomberManX.model.game.entities.Man;
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
        Man man = jugador.getMan();
        int coor_x = jugador.getMan().getCoor_x();
        int coor_y = jugador.getMan().getCoor_y();
        boolean puede = false; // puede hacer accion
        
        puede = hay_objeto(coor_x,coor_y, man);
        
        if(puede){      
            System.out.println("Pudo moverse >>");
            tablero[coor_x][coor_y]= (Elemento) man.accionBomba();
        }        
        
        return puede;
    }
    
    public boolean mover(int id_jugador){
        return false;
    }
        
    /**
     * Revisa que fila y columna del tablero no este ocuapda, expectuando por el Man
     * @param fila
     * @param columna
     * @return 
     */
    private boolean hay_objeto(int fila, int columna, Man man){        
        Bomba bomba = (Bomba) tablero[fila][columna].getElemento();
        return bomba.get_man()!=man ;         // provisional solo mirando Man mientras se implementa para revisar si hay otra cosa
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
