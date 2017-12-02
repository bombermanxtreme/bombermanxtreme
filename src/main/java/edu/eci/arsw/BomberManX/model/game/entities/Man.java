/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

import java.util.ArrayList;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Man implements Elemento {

    public static final String[] colores = {"red", "yellow", "blue"};
    private Jugador jugador;
    private Poder poder;
    private int posCol; // posicion en x
    private int posRow; // posicion en y
    private int bombas; //numero de bombas
    private String color, key;
    private int radio;
    private ArrayList<Bomba> bombas_man;
    private int indice;


    public Man(String color, Jugador jugador, String key, int posRow, int posCol) {
        this.color = color;
        this.jugador = jugador;
        bombas = 3;
        this.key = key;
        this.posRow = posRow;
        this.posCol = posCol;
        radio = 3;
        indice = 0;
    
        inicar_bombas("black", radio);
    }

    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Poner bomba, contar tiempo y explotar
     *
     * @return
     */
    public Bomba accionBomba() {
        Bomba bomba = bombas_man.get(siguiente_bomba_indice());
        bomba.setDisponible(false);
        System.out.println("accionó Bomba >>");
        return bomba;
    }

    public boolean moverse() {
        return false;

    }

    public boolean recoger_poder() {
        return false;

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

    public static String[] getColores() {
        return colores;
    }

    public Poder getPoder() {
        return poder;
    }

    public int getBombas() {
        return bombas;
    }

    public String getColor() {
        return color;
    }
    
    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setKey(String k) {
        this.key = k;
    }

    private void inicar_bombas(String color, int radio) {
        bombas_man = new ArrayList<>();

        for (int i = 0; i < bombas_man.size(); i++) {
            bombas_man.add(new Bomba_n(this, color, radio));
        }

    }

    /**
     * retorna el sigiente indice en la lista de las bombas
     *
     * @return
     */
    private int siguiente_bomba_indice() {
        // falta poner el timer de 5s
        return (int) (indice++ % (bombas_man.size() + 1));
    }

    public int getposCol() {
        return posCol;
    }

    public void setposCol(int posCol) {
        this.posCol = posCol;
    }

    public int getposRow() {
        return posRow;
    }

    public void setposRow(int posRow) {
        this.posRow = posRow;
    }

    @Override
    public Object getElemento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
