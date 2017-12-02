/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

/**
<<<<<<< HEAD
 * Clase abstracta para varios tipos de bombas
=======
 * Abstracta para varios tipos de bombas
>>>>>>> 3915b96aeffe6d81ddcf866dd62e8f68cac7f38a
 * @author sergioxl
 */
public abstract class Bomba implements Elemento{
    
    private int id_jugador;
    private String color, key;
    private int radio;
    private int posRow, posCol;
    
    //public abstract void explotar();


    @Override
    public int getPosRow() {
        return this.posRow;
    }

    @Override
    public void setPosRow(int pos) {
        this.posRow = pos;
    }

    @Override
    public int getPosCol() {
        return this.posCol;
    }

    @Override
    public void setPosCol(int pos) {
        this.posCol = pos;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String k) {
        this.key = k;
    }

    public abstract Man get_man();

    public abstract String getColor();

    public abstract void setColor(String color);

    public abstract int getRadio();

    public abstract void setRadio(int radio);
    
    public abstract void setDisponible(boolean valor);
    
    public abstract boolean getDisponible();
}
