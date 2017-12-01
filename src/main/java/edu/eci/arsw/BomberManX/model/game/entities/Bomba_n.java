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
public class Bomba_n extends Bomba implements Elemento{
    
    private Man man;
    private String color;
    private int radio;

    public Bomba_n(Man man, String color, int radio) {
        this.man = man;
        this.color = color;
        this.radio = radio;
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

    
}
