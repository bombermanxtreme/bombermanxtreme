package edu.eci.arsw.bombermanx.model.game;

import edu.eci.arsw.bombermanx.model.game.entities.Caja;
import edu.eci.arsw.bombermanx.model.game.entities.Caja_Metalica;
import edu.eci.arsw.bombermanx.model.game.entities.Espacio;
import edu.eci.arsw.bombermanx.model.game.entities.Bomba;
import edu.eci.arsw.bombermanx.model.game.entities.Casilla;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.model.game.entities.Elemento;
import edu.eci.arsw.bombermanx.model.game.entities.Man;
import edu.eci.arsw.bombermanx.recursos.MessengerTh;
import edu.eci.arsw.bombermanx.services.GameServicesException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Juego {

    public static final int ARRIBA = 0;
    public static final int ABAJO = 1;
    public static final int DERECHA = 2;
    public static final int IZQUIERDA = 3;
    public static final int ANCHO = 20;
    public static final int ALTO = 10;
    public boolean esEquipos;
    public static final int TIEMPOEXPLOTARBOMBAS = 5000;
    private ArrayList<Jugador> jugadores;
    private Casilla[][] tablero;
    private ArrayList<Man> manes;
    public static final int MAXIMOJUGADORES = 4;
    private static final int[][] POSJUGADORES = {{0, 0}, {ALTO - 1, ANCHO - 1}, {0, ANCHO - 1}, {ALTO - 1, 0}};
    private Timer timer;

    public Juego(ArrayList<Jugador> jugadores, String[][] tableroTemporal, boolean esEquipos) {
        this.jugadores = jugadores;
        this.esEquipos = esEquipos;
        manes = new ArrayList<>();
        System.out.println("////////////////////// Numero de Jugadores: " + this.jugadores.size());
        this.tablero = new Casilla[ALTO][ANCHO];

        for (int i = 0; i < ALTO; i++) {
            for (int k = 0; k < ANCHO; k++) {
                tablero[i][k] = new Casilla();
            }
        }
        // Mapear Tablero
        mapearTablero(tableroTemporal);
    }

    /**
     * Mapear tablero de String a Objetos Author: Kevin S Sanchez
     *
     * @param temp Matriz de Strings
     */
    private void mapearTablero(String[][] temp) {
        //Recorrer Filas
        String letter;
        for (int row = 0; row < temp.length; row++) {
            //Recorrer Columnas
            for (int col = 0; col < temp[row].length; col++) {
                letter = temp[row][col];
                System.out.println("///////////////////////// Letra: " + letter);
                // Convenciones para hacer escenarios:
                // * {1,2,3,4,5,6.....} =  Numeros para representar jugadores.
                // * 'O' = Espacio vacio.
                // * 'B' = Bomba.
                // * 'C' = Caja que se puede destruir.
                // * 'X' = Bloque (No se puede destruir).
                // * 'R' = Poder de Correr.
                // * 'T' = Poder de expansion de explosion de Bomba.
                // * 'M' = Añadir cantidad de bombas que se pueden colocar al mismo tiempo
                // * {'@', '-', '/'} = Caracteres especiales para NPCs.
                if (isNumeric(letter)) {
                    int idJ=Integer.parseInt(letter);
                    Man manTMP=new Man("black", jugadores.get(idJ), letter, row, col);
                    this.tablero[row][col].reemplazar(manTMP);
                    manes.add(idJ, manTMP);
                } else {
                    switch (letter) {
                        case "O":
                            this.tablero[row][col].reemplazar(new Espacio(letter, row, col));
                            System.out.println("OOOO POSX: " + this.tablero[row][col].get().getPosRow() + " + + + POSY: " + this.tablero[row][col].get().getPosCol());
                            break;

                        case "C":
                            this.tablero[row][col].reemplazar(new Caja(letter, row, col));
                            System.out.println("CCCC POSX: " + this.tablero[row][col].get().getPosRow() + " + + + POSY: " + this.tablero[row][col].get().getPosCol());
                            break;

                        case "X":
                            this.tablero[row][col].reemplazar(new Caja_Metalica(letter, row, col));
                            System.out.println("XXXX POSX: " + this.tablero[row][col].get().getPosRow() + " + + + POSY: " + this.tablero[row][col].get().getPosCol());
                            break;

                        default:
                            this.tablero[row][col].reemplazar(new Espacio(letter, row, col));
                            System.out.println("EEEEE POSX: " + this.tablero[row][col].get().getPosRow() + " + + + POSY: " + this.tablero[row][col].get().getPosCol());
                            break;
                    }
                }
            }
        }
    }

    /**
     * Verificar si String es Numerico
     *
     * @param str Cadena de texto a verificar
     * @return True: Es numerico, False: NO es numerico
     */
    private boolean isNumeric(String str) {
        double de;
        try {
            de = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Ejecuta la accion de la bomba de ser posible, la coloca y luego temporiza
     * 5s para explotar
     *
     * @param jugador
     * @return
     * @throws edu.eci.arsw.bombermanx.services.GameServicesException
     */
    public Bomba accionBomba(Jugador jugador) throws GameServicesException {
        Man man = manes.get(jugadores.indexOf(jugador));
        if (man == null) {
            throw new GameServicesException("No se definió correctamente la relación entre jugador y man");
        }
        int mposCol = man.getPosCol();
        int mposRow = man.getPosRow();
        
        System.out.println("pos interno: "+mposCol+" "+mposRow);

        Bomba explotara = null;

        boolean puede = hay_objeto(mposRow, mposCol, man);

        if (puede) {
            System.out.println("Pudo poner bomba >>");

            explotara = man.accionBomba();
            if (explotara != null) {
                tablero[mposRow][mposCol].add(explotara);
            }
        }

        return explotara;
    }

    /**
     * Explota la bomba segun el TIEMPOEXPLOTARBOMBAS en 4 hilos, y en la trayectoria de la
     * explosion informa que daños causo
     *
     * @param explotara
     * @return 
     */
    public ArrayList<Object> explotar(Bomba explotara) {
        explotara.get_man().agregarBomba();
        // creando hilos para recorrer tablero
        MessengerTh izquierda = new MessengerTh();
        izquierda.iniciar(explotara, tablero, IZQUIERDA);

        MessengerTh derecha = new MessengerTh();
        derecha.iniciar(explotara, tablero, DERECHA);

        MessengerTh arriba = new MessengerTh();
        arriba.iniciar(explotara, tablero, ARRIBA);

        MessengerTh abajo = new MessengerTh();
        abajo.iniciar(explotara, tablero, ABAJO);

        // iniciando hilos
        izquierda.start();
        derecha.start();
        arriba.start();
        abajo.start();
        
        try {
            //esperamos
            izquierda.join();
            derecha.join();
            arriba.join();
            abajo.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }

        //unimos todo los afectados
        ArrayList<Object> afectados = new ArrayList<>();// 0-> elementos , 1 -> coordenadas
        afectados = izquierda.getAfectados();

        ArrayList<Elemento> tmp_eleme= (ArrayList<Elemento>) afectados.get(0);
        tmp_eleme.addAll((ArrayList<Elemento>) derecha.getAfectados().get(0));
        tmp_eleme.addAll((ArrayList<Elemento>) arriba.getAfectados().get(0));
        tmp_eleme.addAll((ArrayList<Elemento>) abajo.getAfectados().get(0));
        
        ArrayList<int[]> tmp_coords= (ArrayList<int[]>) afectados.get(1);
        tmp_coords.addAll((ArrayList<int[]>) derecha.getAfectados().get(1));
        tmp_coords.addAll((ArrayList<int[]>) arriba.getAfectados().get(1));
        tmp_coords.addAll((ArrayList<int[]>) abajo.getAfectados().get(1));

        return afectados;
    }

	public void daniarCaja(int x,int y){
		tablero[y][x].reemplazar(new Espacio("",y,x));
		//msgt.convertAndSend("/topic/Sala." + idSala, false);
	}

	public boolean mover(int id_jugador){
		return false;
	}

    /**
     * Revisa que fila y columna del tablero no este ocuapda, expectuando por el
     * Man
     *
     * @param fila
     * @param columna
     * @return
     */
    private boolean hay_objeto(int fila, int columna, Man man) {
        System.out.println("--------------------------"+fila+"++++"+columna);
        ArrayList<Elemento> e= tablero[fila][columna].getAll();
        boolean puede=true;
        for (int i = 0; i < e.size(); i++) {
            if(e.get(i) instanceof Bomba){
                puede=false;
                break;
            }
        }
        
        return puede;
    }

    public boolean mover() {
        return false;
    }

    @Override
    public String toString() {
        ArrayList<String> cajasS = new ArrayList<>();
        ArrayList<String> cajasM = new ArrayList<>();
        ArrayList<String> manesS = new ArrayList<>();
        for (int i = 0; i < tablero.length; i++) {
            for (int k = 0; k < tablero[0].length; k++) {
                System.out.println("");
                if (tablero[i][k].tieneTipo(Caja.class)) {
                    cajasS.add("{x:" + k + ",y:" + i + "}");
                } else if (tablero[i][k].tieneTipo(Caja_Metalica.class)) {
                    cajasM.add("{x:" + k + ",y:" + i + "}");
                } else if (tablero[i][k].tieneTipo(Man.class)) {
                    manesS.add(((Man) tablero[i][k].getTipo(Man.class)).toString());
                }
                if (tablero[i][k].tieneTipo(Bomba.class)) {
                    //manesS.add(tablero[i][k].toString());
                }
            }
        }
        return "{\"cajas\":" + cajasS.toString() + ",\"cajasFijas\":" + cajasM.toString() + ",\"manes\":" + manesS.toString() + ",\"ancho\":" + ANCHO + ",\"alto\":" + ALTO + "}";
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Casilla[][] getTablero() {
        return tablero;
    }

    public void setTablero(Casilla[][] tablero) {
        this.tablero = tablero;
    }
}