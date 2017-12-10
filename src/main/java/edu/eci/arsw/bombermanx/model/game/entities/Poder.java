package edu.eci.arsw.bombermanx.model.game.entities;


/**
 *
 * @author Kvn CF <ECI>
 */
public abstract class Poder implements Elemento, DejaMover {

    private String key;
    private int posRow;
    private int posCol;
    private int velocidad;
    private int radioBomba;
    private int numBomba;

    /**
     * constructor
     *
     * @param key
     * @param posRow
     * @param posCol
     * @param velocidad
     * @param radioBomba
     * @param numBomba
     */
    public Poder(String key, int posRow, int posCol, int velocidad, int radioBomba, int numBomba) {
        this.key = key;
        this.posRow = posRow;
        this.posCol = posCol;
        this.velocidad = velocidad;
        this.radioBomba = radioBomba;
        this.numBomba = numBomba;
    }

    @Override
    public int getPosRow() {
        return this.posRow;
    }

    @Override
    public int getPosCol() {
        return this.posCol;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public int getRadioBomba() {
        return radioBomba;
    }

    public int getNumBomba() {
        return numBomba;
    }

    @Override
    public String toString() {
        return "{\"x\":" + posCol + ",\"y\":" + posRow + ",\"key\":\"" + key + "\"}";
    }
}
