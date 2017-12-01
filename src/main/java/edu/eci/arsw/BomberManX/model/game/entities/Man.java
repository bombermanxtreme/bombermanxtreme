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
public class Man {
    public static final String[] colores = {"red", "yellow", "blue"};
    private Jugador jugador;
    private int posicion; // posicion en la que se encuentra      
    private Poder poder;
    private int bombas; //numero de bombas
    private ArrayList<Bomba> bombas_jugador;
    private String color;    
    private int radio;
    
    public Man(String color, Jugador jugador) {
        this.color = color;
        this.jugador = jugador;
        posicion = 10; // posicion inicial
        bombas = 3; 
        radio=3;
        
        inicar_bombas("black",radio);
        
    }
     
    public Jugador getJugador(){
        return jugador;
    } 
    
    public void accionBomba(){
    
    }
    
    public boolean moverse(){
        return false;
    
    }
    
    public boolean recoger_poder(){
        return false;
    
    }
    
    private void inicar_bombas(String color, int radio){
        bombas_jugador = new ArrayList<>();
        
        for(int i=0;i<bombas;i++){
            bombas_jugador.add(new Bomba_n(this, color, radio));
        }
    
    }
}
