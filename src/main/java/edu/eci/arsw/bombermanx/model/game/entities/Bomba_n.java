package edu.eci.arsw.bombermanx.model.game.entities;

/**
 * Bomba Normal
 * @author sergioxl
 */
public class Bomba_n extends Bomba{
    
    private final Man man;
    private String color;
    private int radio;    
    private int posRow;
    private int posCol;
    private String key;

    public Bomba_n(Man man, String color, int radio) {
        this.man = man;
        this.color = color;
        this.radio = radio;       
    }

    public Bomba_n(String key, int posRow, int posCol) {
        this.posRow = posRow;
        this.posCol = posCol;
        this.key = key;
        this.man = null;
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
    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    @Override
    public void setPosCol(int posCol) {
        this.posCol = posCol;
    }
    
}
