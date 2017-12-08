package edu.eci.arsw.bombermanx.model.game.entities;

/**
 * Clase abstracta para varios tipos de bombas
 *
 * @author sergioxl
 */
public abstract class Bomba implements Elemento {

    private String color, key;
    private int posCol;
    private int posRow;
    private int radio;
    private Man man;

    public abstract Man get_man();

    public abstract String getColor();

    public abstract void setColor(String color);

    public abstract int getRadio();

    public abstract void setRadio(int radio);

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String k) {
        this.key = k;
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
    public String toString(){
        return "{\"y\":"+posRow+",\"x\":"+posCol+"}";
    }
}