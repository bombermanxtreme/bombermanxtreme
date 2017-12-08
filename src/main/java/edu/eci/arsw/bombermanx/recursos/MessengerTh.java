package edu.eci.arsw.bombermanx.recursos;

import edu.eci.arsw.bombermanx.model.game.entities.Bomba;
import edu.eci.arsw.bombermanx.model.game.entities.Caja;
import edu.eci.arsw.bombermanx.model.game.entities.Casilla;
import edu.eci.arsw.bombermanx.model.game.entities.Elemento;
import edu.eci.arsw.bombermanx.model.game.entities.Destruible;
import edu.eci.arsw.bombermanx.model.game.entities.Man;
import java.util.ArrayList;

/**
 * Clase que recibe el bomba, tablero, el radio de explosion y el sentido para
 * buscar revisar la trayectoria de la explosion si encuentra un objeto
 * inamovile se detiene/destruye y si no entonces, lo destruye y sigue el
 * trayecto.
 *
 * @author sergioxl
 */
public class MessengerTh extends Thread {

    public static final int ARRIBA = 0;
    public static final int ABAJO = 1;
    public static final int DERECHA = 2;
    public static final int IZQUIERDA = 3;
    private ArrayList<Object> afectados;
    private ArrayList<Elemento> elementos;
    private ArrayList<int[]> coords;

    private int ancho;
    private int alto;
    private int radio;
    private int sentido;
    private int veces;
    private int ax;
    private int ay;

    private Casilla[][] tablero;
    private Bomba bomba;

    private int delivery; // variable que recorreo el tablero
    private int posRow;
    private int posCol;

    /**
     * Recorreo el tablero; para cajas quema y llega hasta ahí, para el resto de
     * objetos, los quema y la "llama" sigue su recorrido 0: izquierda, 1:
     * derecha, 2: arriba, 3: abajo, 4: todos los sentidos.
     */
    public MessengerTh() {

    }

    public void iniciar(Bomba bomba, Casilla[][] tablero, int sentido) {
        this.tablero = tablero;
        alto = tablero.length;
        ancho = tablero[0].length;

        this.bomba = bomba;
        this.radio = bomba.getRadio();
        this.sentido = sentido;

        afectados = new ArrayList<>();
        elementos = new ArrayList<>();
        coords = new ArrayList<>();
        posRow = bomba.getPosRow();
        posCol = bomba.getPosCol();
        delivery = 0;
        veces = 0;
        ax = 0;
        ay = 0;
    }

    public void run() {
        boolean detiene = false;
        if (sentido == IZQUIERDA || sentido == 4) {
            //izquierda
            ax = 0;
            ay = posCol;
            veces = 0;

            if (distancia(posRow, posCol, ax, ay) >= 1) {
                delivery = posCol - 1;
                while (delivery < ancho && delivery >= 0 && veces < radio) {
                    detiene = revisarCelda(tablero[posRow][delivery]);
                    if (detiene) {
                        break;
                    }

                    delivery -= 1;
                    veces += 1;
                }
            }
        } else if (sentido == DERECHA || sentido == 4) {
            //derecha
            ax = posRow;
            ay = ancho - 1;
            veces = 0;

            if (distancia(posRow, posCol, ax, ay) >= 1) {
                delivery = posCol + 1;
                while (delivery < ancho && delivery >= 0 && veces < radio) {
                    detiene = revisarCelda(tablero[posRow][delivery]);
                    if (detiene) {
                        break;
                    }

                    delivery += 1;
                    veces += 1;
                }
            }
        } else if (sentido == ARRIBA || sentido == 4) {
            //abajo
            ax = posRow;
            ay = alto - 1;
            veces = 0;

            if (distancia(posRow, posCol, ax, ay) >= 1) {
                delivery = posRow + 1;
                while (delivery < alto && delivery >= 0 && veces < radio) {
                    detiene = revisarCelda(tablero[delivery][posCol]);
                    if (detiene) {
                        break;
                    }

                    delivery += 1;
                    veces += 1;
                }
            }
        } else if (sentido == ABAJO || sentido == 4) {
            //arriba
            ax = 0;
            ay = posCol;
            veces = 0;

            if (distancia(posRow, posCol, ax, ay) >= 1) {
                delivery = posRow - 1;
                while (delivery < alto && delivery >= 0 && veces < radio) {
                    detiene = revisarCelda(tablero[delivery][posCol]);
                    if (detiene) {
                        break;
                    }

                    delivery -= 1;
                    veces += 1;
                }
            }
        } else {
            System.err.println("Thread {Messenger.java} Caso desconocido...");
        }

    }

    /**
     * revisa que elemento en la casilla afecta y retorna true si es un elemento
     * que bloquee la expanción de la bomba
     *
     * @param e
     * @return
     */
    private boolean revisarCelda(Casilla e) {
        boolean res = false;
        int[] tempCoor = new int[2];

        synchronized (e) {
            if (e.tieneTipo(Destruible.class)) {

                // PARA TIPO MAN
                Destruible d = (Destruible) e.getTipo(Man.class);

                if (d != null) {
                    d.explotaBomba();
                }
                elementos.add((Elemento) d);
                tempCoor[0] = ((Elemento) d).getPosRow();
                tempCoor[1] = ((Elemento) d).getPosCol();
                coords.add(tempCoor);

                // PARA TIPO CAJA
                d = (Destruible) e.getTipo(Caja.class);

                if (d != null) {
                    d.explotaBomba();
                }
                elementos.add((Elemento) d);
                tempCoor[0] = ((Elemento) d).getPosRow();
                tempCoor[1] = ((Elemento) d).getPosCol();
                coords.add(tempCoor);
            }
            if (e.tieneTipo(Caja.class)) {
                res = true;
            }
        }
        return res;
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

    /**
     * get afectados
     *
     * @return
     */
    public ArrayList<Object> getAfectados() {
        afectados.add(elementos);
        afectados.add(coords);
        return afectados;
    }

}
