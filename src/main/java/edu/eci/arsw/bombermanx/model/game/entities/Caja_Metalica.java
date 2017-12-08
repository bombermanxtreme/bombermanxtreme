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
    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    @Override
    public void setPosCol(int posCol) {
        this.posCol=posCol;
    }
    
}
