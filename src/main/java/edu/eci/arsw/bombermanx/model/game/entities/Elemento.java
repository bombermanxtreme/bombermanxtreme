package edu.eci.arsw.bombermanx.model.game.entities;

/**
 *
 * @author Kevin S. Sanchez
 */
public interface Elemento {

    public void setKey(String k);

    public String getKey();

    public int getPosRow();

    public int getPosCol();

    public void setPosRow(int posRow);

    public void setPosCol(int posCol);

}
