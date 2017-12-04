/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

/**
 * Bomba Normal
 * @author sergioxl
 */
public class Bomba_n extends Bomba{
    
    private final Man man;
    private String color;
    private int radio;
    private boolean disponible; // si esta disponible para usar
    private int posRow;
    private int posCol;
    private String key;

    public Bomba_n(Man man, String color, int radio) {
        this.man = man;
        this.color = color;
        this.radio = radio;
        disponible = true;
    }

    public Bomba_n(String key, int posRow, int posCol) {
        this.posRow = posRow;
        this.posCol = posCol;
        this.key = key;
        this.man = null;
    }
 
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
    
    @Override
    public Man get_man() {
        return man;
    }
    
    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public int getRadio() {
        return radio;
    }

    @Override
    public void setRadio(int radio) {
        this.radio = radio;
    }

    @Override
    public void setDisponible(boolean valor) {
        disponible=valor;
    }

    @Override
    public boolean getDisponible() {
        return disponible;
    }
    
}
