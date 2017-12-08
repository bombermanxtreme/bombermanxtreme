package edu.eci.arsw.bombermanx.model.game.entities;

/**
 *
 * @author Kevin S. Sanchez
 */
public interface Elemento {
    
    public void setKey(String k);
    
    public String getKey();
    
    public int getPosRow();
    
    public void setPosRow(int pos);
    
    public int getPosCol();
    
    public void setPosCol(int pos); 
}
