/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Man {
    public static final String[] colores = {"red", "yellow", "blue"};
    private Jugador jugador;
    private int posX;
    private int posY;    
    private Poder poder;
    private int bombas; //numero de bombas
    private String color;
    
    public Man(String color, Jugador jugador) {
        this.color = color;
        this.jugador = jugador;
        bombas = 3;
        posX = 0;
        posY = 0;
    }
}
