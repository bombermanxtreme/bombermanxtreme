/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

/**
 *
 * @author sergioxl
 */
public abstract class Bomba {
    
    private int id_jugador;
    private String color;
    private int radio;
    
    
    public abstract void explotar();

    public abstract int getId_jugador();

    public abstract void setId_jugador(int id_jugador);

    public abstract String getColor();

    public abstract void setColor(String color);

    public abstract int getRadio();

    public abstract void setRadio(int radio);
    
    
}
