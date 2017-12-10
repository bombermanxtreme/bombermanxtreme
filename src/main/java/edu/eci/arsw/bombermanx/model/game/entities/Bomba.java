package edu.eci.arsw.bombermanx.model.game.entities;

import javax.swing.Timer;

/**
 * Clase abstracta para varios tipos de bombas
 *
 * @author sergioxl
 */
public abstract class Bomba implements Elemento {

    //private String color;
    private String color;
    int posCol;
    Timer timer;
    int posRow;
    boolean estallo;
    private int radio;
    private Man man;
    private String key;

    public Bomba(Man man, String color, int radio) {
        this.man = man;
        this.color = color;
        this.radio = radio;
        posRow = man.getPosRow();
        posCol = man.getPosCol();
        key = "B";
        estallo = false;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getRadio() {
        return radio;
    }

    public Man get_man() {
        return man;
    }

    @Override
    public int getPosRow() {
        return posRow;
    }

    @Override
    public int getPosCol() {
        return posCol;
    }

    @Override
    public String toString() {
        return "{\"y\":" + posRow + ",\"x\":" + posCol + ",\"estallo\":" + estallo + ",\"key\":\"" + key + "\"}";
    }

    public void estalla() {
        estallo = true;
    }
}
