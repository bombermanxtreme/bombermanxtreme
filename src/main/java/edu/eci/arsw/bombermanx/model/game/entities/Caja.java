package edu.eci.arsw.bombermanx.model.game.entities;

/**
 *
 * @author Kevin S. Sanchez
 */
public class Caja implements Elemento,Destruible{
    private String key;
    private int posRow;
    private int posCol;

    public Caja(String key, int posRow, int posCol) {
        this.key = key;
        this.posRow = posRow;
        this.posCol = posCol;
    }
  
    @Override
    public int getPosRow() {
        return this.posRow;
    }

    public void setPosRow(int pos) {
        this.posRow = pos;
    }

    @Override
    public int getPosCol() {
        return this.posCol;
    }

    public void setPosCol(int pos) {
        this.posCol = pos;
    }

    @Override
    public void explotaBomba() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

	@Override
	public String toString(){
		return "{\"x\":"+posCol+",\"y\":"+posRow+"}";
	}
}
