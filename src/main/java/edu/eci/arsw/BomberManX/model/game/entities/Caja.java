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
public class Caja implements Elemento{
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

    @Override
    public void setPosRow(int pos) {
        this.posRow = pos;
    }

    @Override
    public int getPosCol() {
        return this.posCol;
    }

    @Override
    public void setPosCol(int pos) {
        this.posCol = pos;
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
    public Object getElemento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
