package edu.eci.arsw.bombermanx.model.game.entities;

/**
 *
 * @author Kevin S. Sanchez
 */
public class Caja_Metalica implements Elemento{
    private String key;
    private int posRow, posCol;

    public Caja_Metalica(String key, int posRow, int posCol) {
        this.key = key;
        this.posRow = posRow;
        this.posCol = posCol;
    }    

    @Override
    public int getPosRow() {
        return posRow;
    }

    @Override
    public int getPosCol() {
        return posCol;
    }    
}
