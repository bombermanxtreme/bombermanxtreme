package edu.eci.arsw.bombermanx.model.game.entities;

/**
 * Clase abstracta para varios tipos de bombas
 *
 * @author sergioxl
 */
public abstract class Bomba implements Elemento {

    //private String color;
    private String color;
    int posCol;
    int posRow;
    boolean estallo=false;
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
    public int getPosRow() {
        return posRow;
    }

    @Override
    public int getPosCol() {
        return posCol;
    }
    
    @Override
    public String toString(){
        return "{\"y\":"+posRow+",\"x\":"+posCol+",\"estallo\":"+estallo+"}";
    }

    public void estalla() {
        estallo=true;
    }
}