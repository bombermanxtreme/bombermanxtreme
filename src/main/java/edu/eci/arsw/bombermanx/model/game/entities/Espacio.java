package edu.eci.arsw.bombermanx.model.game.entities;

/**
 *
 * @author Kevin S. Sanchez
 */
public class Espacio implements Elemento,DejaMover {
    private String key;
    private int posRow;
    private int posCol;

    public Espacio(String key, int posRow, int posCol) {
        this.key = key;
        this.posRow = posRow;
        this.posCol = posCol;
    }

    @Override
    public int getPosRow() {
        return this.posRow;
    }

    @Override
    public int getPosCol() {
        return this.posCol;
    }
    
    @Override
    public String toString() {
        return "{\"x\":\"" + posCol + "\", \"y\":\"" + posRow + "\", \"key\":\"" + key + "\"}";        
    }
}
