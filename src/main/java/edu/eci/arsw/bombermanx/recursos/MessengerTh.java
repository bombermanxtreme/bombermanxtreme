package edu.eci.arsw.bombermanx.recursos;

import edu.eci.arsw.bombermanx.model.game.entities.Bomba;
import edu.eci.arsw.bombermanx.model.game.entities.Elemento;


/**
 * Clase que recibe el bomba, tablero, el radio de explosion y el sentido para
 * buscar revisar la trayectoria de la explosion si encuentra un objeto
 * inamovile se detiene/destruye y si no entonces, lo destruye y sigue el
 * trayecto.
 *
 * @author sergioxl
 */
public class MessengerTh extends Thread {

    private int ancho;
    private int alto;
    private int radio;
    private int sentido;
    private int veces;
    private int ax;
    private int ay;

    private Elemento[][] tablero;
    private Bomba bomba;

    private int delivery; // variable que recorreo el tablero
    private int posRow;
    private int posCol;

    /**
     * Recorreo el tablero; para cajas quema y ellga hasta ahí, para el resto de
     * objetos, los quema y la "llama" sigue su recorrido 0: izquierda, 1:
     * derecha, 2: arriba, 3: abajo, 4: todos los sentidos.
     *
     * @param bomba
     * @param tablero
     * @param radio
     * @param sentido
     */
    public Messenger(Bomba bomba, Elemento[][] tablero, int radio, int sentido) {
        this.tablero = tablero;
        alto = tablero.length;
        ancho = tablero[0].length;

        this.bomba = bomba;
        this.radio = radio;
        this.sentido = sentido;

        posRow = bomba.getPosRow();
        posCol = bomba.getPosCol();
        delivery = 0;
        veces = 0;
        ax = 0;
        ay = 0;
    }

    public void run() {

        if (sentido == 0 || sentido == 4) {
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
        } else if (sentido == 1 || sentido == 4) {
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
        } else if (sentido == 2 || sentido == 4) {
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
        } else if (sentido == 3 || sentido == 4) {
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
        } else {
            System.err.println("Thread {Messenger.java} Case desconocido...");
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

}
