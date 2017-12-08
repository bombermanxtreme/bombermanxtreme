package edu.eci.arsw.bombermanx.model.game.entities;

/**
 * Clase abstracta para varios tipos de bombas
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
