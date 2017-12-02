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
public class Man { // se crea el man el Jugador
    public static final String[] colores = {"red", "yellow", "blue"};
    private Jugador jugador;    
    private Poder poder;        
    private String color;    
    private int coor_x; // posicion en x
    private int coor_y; // posicion en y
    
    private int radio;  
    
    private ArrayList<Bomba> bombas_man;
    private int indice;
    private int bombas; //numero de bombas
    
    public Man(String color, Jugador jugador, int coor_x, int coor_y) {
        this.color = color;
        this.jugador = jugador;
        this.coor_x = coor_x; 
        this.coor_y = coor_x;
        bombas = 3; 
        radio=3; // unidades de tablero       
        indice = 0; // indice de donde van las bombas
        
        inicar_bombas("black",radio);        
    }
     
    public Jugador getJugador(){
        return jugador;
    } 
    
    /**
     * Poner bomba, contar tiempo y explotar
     */
    public Bomba accionBomba(){      
        Bomba bomba = bombas_man.get(siguiente_bomba_indice());
        bomba.setDisponible(false);
        return bomba;
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

    public int getCoor_x() {
        return coor_x;
    }

    public void setCoor_x(int coor_x) {
        this.coor_x = coor_x;
    }

    public int getCoor_y() {
        return coor_y;
    }

    public void setCoor_y(int coor_y) {
        this.coor_y = coor_y;
    }
    
    
    
}