package edu.eci.arsw.bombermanx.model.game.entities;

import java.util.ArrayList;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Man implements Elemento,Destruible {

    public static final String[] colores = {"red", "yellow", "blue"};
    private Jugador jugador;
    private Poder poder;
    private int posCol; // posicion en x
    private int posRow; // posicion en y
    private Integer bombas; //numero de bombas
    private String color, key;
    private int radio;
    private int indice;


    public Man(String color, Jugador jugador, String key, int posRow, int posCol) {
        this.color = color;
        this.jugador = jugador;
        bombas = 3;
        this.key = key;
        this.posRow = posRow;
        this.posCol = posCol;
        radio = 2;
        indice = 0;
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
        Bomba bomba=null;
        synchronized(bombas){
            if(bombas>0){
                bombas--;
                bomba = new Bomba_n(this, color, radio);
                System.out.println("puso Bomba >>");
            }else System.out.println("no tiene mas bombas");
        }
        return bomba;
    }

    public boolean moverse() {
        return false;

    }

    public boolean recoger_poder() {
        return false;

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
    
    public String getKey() {
        return this.key;
    }

    public void setPosCol(int posCol) {
        this.posCol = posCol;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }
    
    @Override
    public String toString(){
        return "{\"x\":" + posCol + ",\"y\":" + posRow + ",\"color\":\"" + color + "\",\"apodo_jugador\":\"" + jugador.getApodo() + "\",\"key\":\"" + key + "\"}";
    }

    public int getPosRow() {
        return posRow;
    }

    @Override
    public int getPosCol() {
        return posCol;
    }

    @Override
    public void explotaBomba() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void agregarBomba() {
        synchronized(bombas){
            bombas++;
        }
        
    }
}
