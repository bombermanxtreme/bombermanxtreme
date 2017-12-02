/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game;

import edu.eci.arsw.BomberManX.model.game.entities.Caja;
import edu.eci.arsw.BomberManX.model.game.entities.Caja_Metalica;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.Casilla;
import edu.eci.arsw.BomberManX.model.game.entities.Elemento;
import edu.eci.arsw.BomberManX.model.game.entities.Espacio;
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
    // Kevin S. Sanchez: Cambio de Casilla por Elemento
    private Elemento[][] tablero;

    public Juego(ArrayList<Jugador> jugadores, String[][] tableroTemporal) {
        this.jugadores = jugadores;
        this.tablero = new Elemento[ALTO][ANCHO];
        tiempo = 0;
        // Mapear Tablero
        mapearTablero(tableroTemporal);
    }
    
    /**
     * Mapear tablero de String a Objetos
     * Author: Kevin S Sanchez
     * @param temp Matriz de Strings
     */
    private void mapearTablero(String[][] temp){
        //Recorrer Filas
        String letter;
        for (int row = 0; row < temp.length; row++){
            //Recorrer Columnas
            for (int col = 0; col < temp[row].length; col++){
                letter = temp[row][col];
                // Convenciones para hacer escenarios:
                // * {1,2,3,4,5,6.....} =  Numeros para representar jugadores.
                // * 'O' = Espacio vacio.
                // * 'B' = Bomba.
                // * 'C' = Caja que se puede destruir.
                // * 'X' = Bloque (No se puede destruir).
                // * 'R' = Poder de Correr.
                // * 'T' = Poder de expansion de explosion de Bomba.
                // * 'M' = Añadir cantidad de bombas que se pueden colocar al mismo tiempo
                // * {'@', '-', '/'} = Caracteres especiales para enemigos.
                if(isNumeric(letter)){
                    this.tablero[row][col] = new Man("red", jugadores.get(Integer.parseInt(letter)-1));
                }else{
                    switch (letter) {
                        case "O":
                            this.tablero[row][col] = new Espacio(); 
                            break;

                        case "C":
                            this.tablero[row][col] = new Caja();
                            break;
                            
                        case "X":
                            this.tablero[row][col] = new Caja_Metalica();
                            break;
                        
                        default:
                            this.tablero[row][col] = new Espacio(); 
                            break;
                    }
                }
            }
        }
    }
    
    /**
     * Verificar si String es Numerico
     * @param str Cadena de texto a verificar
     * @return True: Es numerico, False: NO es numerico
     */
    private boolean isNumeric(String str){  
      try{  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe){  
        return false;  
      }  
      return true;  
    }
    
    /**
     * Revisa que fila y columna del tablero no este ocuapda
     * @param fila
     * @param columna
     * @return 
     */
    public boolean hay_objeto(int fila, int columna){        
        //return tablero[fila][columna].hay_elemento();
        return false;
    }
    
    /**
     * Retorna los tipos de lo que hay 
     * @param fila
     * @param columna
     * @return 
     */
    public Object[] hay_objeto_tipo(int fila, int columna){
        Object[] lista = new Object[2];
        return lista;
    }
    
    public boolean mover(){
        return false;
    }
    
}
