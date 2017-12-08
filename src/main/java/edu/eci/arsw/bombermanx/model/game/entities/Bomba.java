package edu.eci.arsw.bombermanx.model.game.entities;

/**
 * Clase abstracta para varios tipos de bombas
 *
 * @author sergioxl
 */
public abstract class Bomba implements Elemento {

    private String color, key;
    int posCol;
    int posRow;
    private int radio;
    private Man man;

    public Bomba(Man man, String color, int radio) {
        this.man = man;
        this.color = color;
        this.radio = radio;
        posRow = man.getPosRow();
        posCol = man.getPosCol();
        System.out.println("dentro de bomba"+posCol+"-"+posRow);
        
    }

    public int getRadio() {
        return radio;
    }

    public Man get_man() {
        return man;
    }
    

    @Override
    public void setPosRow(int posRow) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPosCol(int posCol) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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