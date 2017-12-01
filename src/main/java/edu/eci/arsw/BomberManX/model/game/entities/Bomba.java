/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

/**
 * Interfaz para varios tipos de bombas
 * @author sergioxl
 */
public abstract class Bomba {
    
    public abstract Man get_man();

    public abstract String getColor();

    public abstract void setColor(String color);

    public abstract int getRadio();

    public abstract void setRadio(int radio);
    
    public abstract void setDisponible(boolean valor);
    
    public abstract boolean getDisponible();
    
    
}
