/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

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
