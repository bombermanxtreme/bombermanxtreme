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
        Bomba bomba = new Bomba_n(this, color, radio);
        System.out.println("puso Bomba >>");
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

        for(int i = 0; i < bombas_man.size(); i++) {
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
    
    @Override
    public String toString(){
        return "{\"x\":" + posCol + ",\"y\":" + posRow + ",\"color\":\"" + color + "\",\"apodo_jugador\":\"" + jugador.getApodo() + "\"}";
    }

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
        this.posCol = posCol;
    }

    @Override
    public void explotaBomba() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
