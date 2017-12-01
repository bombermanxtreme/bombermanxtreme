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
    private ArrayList<Bomba> bombas_man;
    private String color;    
    private int radio;
    
    private int tiempo; // tiempo de explotar bomba
    private int indice;
    
    public Man(String color, Jugador jugador) {
        this.color = color;
        this.jugador = jugador;
        posicion = 10; // posicion inicial
        bombas = 3; 
        radio=3; // unidades de tablero
        tiempo = 5; // segundos
        indice = 0; // indice de donde van las bombas
        
        inicar_bombas("black",radio);
        
    }
     
    public Jugador getJugador(){
        return jugador;
    } 
    
    /**
     * Poner bomba, contar tiempo y explotar
     */
    public void accionBomba(){
        
        bombas_man.get(siguiente_bomba_indice()).setDisponible(false);
    }
    
    public boolean moverse(){
        return false;
    
    }
    
    public boolean recoger_poder(){
        return false;
    
    }
    
    private void inicar_bombas(String color, int radio){
        bombas_man = new ArrayList<>();
        
        for(int i=0;i<bombas_man.size();i++){
            bombas_man.add(new Bomba_n(this, color, radio));
        }
    
    }
    
    /**
     * retorna el sigiente indice en la lista de las bombas
     * @return 
     */
    private int siguiente_bomba_indice(){    
        // falta poner el timer de 5s
       return (int)(indice++%(bombas_man.size()+1));
    }
    
}