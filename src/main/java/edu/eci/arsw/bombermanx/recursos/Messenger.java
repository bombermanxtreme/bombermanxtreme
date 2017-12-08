package edu.eci.arsw.bombermanx.recursos;

import edu.eci.arsw.bombermanx.model.game.entities.Elemento;

/**
 *
 * @author sergioxl
 */
public class Messenger extends Thread {

    private int ancho;
    private int alto;
    private int radio;
    private int sentido;
    private int veces;
    private int ax;
    private int ay;

    private Elemento[][] tablero;
    private Elemento elemento;

    private int delivery; // variable que recorreo el tablero
    private int posRow;
    private int posCol;

    /**
     * Recorreo el tablero; para cajas quema y ellga hasta ahí, para el resto de
     * objetos, los quema y la "llama" sigue su recorrido 0: izquierda,    
     * 1: derecha, 2: arriba, 3: abajo, 4: todos los sentidos.
     *
     * @param elemento
     * @param tablero
     * @param radio
     * @param sentido
     */
    public Messenger(Elemento elemento, Elemento[][] tablero, int radio, int sentido) {
        this.tablero = tablero;
        alto = tablero.length;
        ancho = tablero[0].length;

        this.elemento = elemento;
        this.radio = radio;
        this.sentido = sentido;

        posRow = elemento.getPosRow();
        posCol = elemento.getPosCol();
        delivery = 0;
        veces = 0;
        ax = 0;
        ay = 0;
    }

    public void run() {

        switch (sentido) {
            case 0: izquierda();
                break;
            case 1: derecha();
                break;
            case 2: arriba();
                break;
            case 3: abajo();
                break;
            case 4: izquierda(); derecha(); arriba(); abajo();
                break;
            default:
                break;

        }

    }

    /**
     * Distancia geometrica entre dos puntos
     *
     * @param posRow1 origen
     * @param posCol1 origen
     * @param posRow2 destino
     * @param posCol2 destino
     * @return distancia
     */
    private int distancia(int posRow1, int posCol1, int posRow2, int posCol2) {
        return (int) Math.sqrt((int) Math.pow(posRow1 - posRow2, 2) + (int) Math.pow(posCol1 - posCol2, 2));
    }
    
    
    private void izquierda(){
        //izquierda
        ax = 0;
        ay = posCol;
        veces = 0;

        if (distancia(posRow, posCol, ax, ay) >= 1) {
            delivery = posCol - 1;
            while (delivery < ancho && delivery >= 0 && veces < radio) {
                System.out.println(tablero[posRow][delivery]);

                delivery -= 1;
                veces += 1;

            }
        }
    
    }
    
    private void derecha(){
        //derecha
        ax = posRow;
        ay = ancho - 1;
        veces = 0;

        if (distancia(posRow, posCol, ax, ay) >= 1) {
            delivery = posCol + 1;
            while (delivery < ancho && delivery >= 0 && veces < radio) {
                System.out.println(tablero[posRow][delivery]);

                delivery += 1;
                veces += 1;

            }
        }
    
    }
    
    private void arriba(){
        //arriba
        ax = 0;
        ay = posCol;
        veces = 0;

        if (distancia(posRow, posCol, ax, ay) >= 1) {
            delivery = posRow - 1;
            while (delivery < alto && delivery >= 0 && veces < radio) {
                System.out.println(tablero[delivery][posCol]);

                delivery -= 1;
                veces += 1;

            }
        }
    
    }
    
    private void abajo(){
        //abajo
        ax = posRow;
        ay = alto - 1;
        veces = 0;

        if (distancia(posRow, posCol, ax, ay) >= 1) {
            delivery = posRow + 1;
            while (delivery < alto && delivery >= 0 && veces < radio) {
                System.out.println(tablero[delivery][posCol]);

                delivery += 1;
                veces += 1;

            }
        }
    
    }
}
