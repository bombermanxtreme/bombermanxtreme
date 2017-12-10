package edu.eci.arsw.bombermanx.model.game.entities;

import edu.eci.arsw.bombermanx.model.game.Juego;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Sala {

    private int id;
    private Jugador creador;
    private String nombre;
    private String codigo;
    private boolean equipos;
    private boolean friendFire;
    private boolean casiLista;

    private ArrayList<Jugador> jugadores;
    private ArrayList<Jugador> equipoA;
    private ArrayList<Jugador> equipoB;
    private ArrayList<Jugador> jugadoresListos;

    //minimo de jugadores "listos" que se necesitan en la sala para jugar
    private static final int MINIMOJUGADORES = 2;
    //tiempo en segundos, máximo que tienen los jugadores que no están listos en la sala para entrar al juego
    public static final int TIEMPOENSALAPARAEMPEZAR = 10; //segundos
    public static final int LONGITUDCODIGO = 10; //segundos
    private Object lockEquipos;

    /**
     * constructor
     *
     * @param id
     * @param creador
     * @param nombre
     * @param equipos
     * @param friendFire
     */
    public Sala(int id, Jugador creador, String nombre, boolean equipos, boolean friendFire) {

        jugadores = new ArrayList<>();
        equipoA = new ArrayList<>();
        equipoB = new ArrayList<>();
        jugadoresListos = new ArrayList<>();
        lockEquipos = 1;

        this.id = id;
        this.creador = creador;
        this.nombre = nombre;
        this.equipos = equipos;
        this.friendFire = friendFire;
        this.codigo = generarCodigo();
    }

    /**
     * agrega un jugador a la sala SI NO HA SIDO AGREGADO o NO está llena la
     * sala
     *
     * @param jugador
     * @return
     */
    public boolean addJugador(Jugador jugador) {
        boolean yaExiste = false;
        //revisamos que aún no esté en la lista
        for (Jugador entry : jugadores) {
            if (entry.equals(jugador)) {
                yaExiste = true;
                break;
            }
        }
        //si no está en la lista entonces lo agregamos
        if (!yaExiste) {
            //si la sala está llena no lo deja entrar
            if (Juego.MAXIMOJUGADORES <= jugadores.size()) {
                return false;
            }
            //lo agregamos a la lista
            jugadores.add(jugador);
            //lo agregamos a un equipo si es necesario
            if (equipos) {
                synchronized (lockEquipos) {
                    if (equipoA.size() > equipoB.size()) {
                        equipoB.add(jugador);
                    } else {
                        equipoA.add(jugador);
                    }
                }
            }
        }
        return true;
    }

    /**
     * retorna los jugadores que están en la sala
     *
     * @return
     */
    public ArrayList<Jugador> getJugadores() {
        //System.out.println("++++++++++++++++++++++++ Numero de jugadores: " + jugadores.size());
        return jugadores;
    }

    /**
     * retorna si está casi lista o faltan jugadores que estén listos para
     * empezar
     *
     * @return
     */
    public boolean casiLista() {
        return casiLista;
    }

    /**
     * retorna los jugadores listos
     *
     * @return
     */
    public ArrayList<Jugador> getJugadoresListos() {
        return jugadoresListos;
    }

    /**
     * establece que la sala está casi lista, es decir que los estudiantes
     * mínimos que están listos lo estén
     */
    public void lista() {
        casiLista = true;
    }

    /**
     * agrega que un jugador ya está listo
     *
     * @param jugadorListo
     */
    public void addJugadorListo(Jugador jugadorListo) {
        boolean yaExiste = false;
        //revisamos que aún no esté en la lista
        synchronized (jugadoresListos) {
            for (Jugador entry : jugadoresListos) {
                if (entry.equals(jugadorListo)) {
                    yaExiste = true;
                    break;
                }
            }
            //si no está en la lista entonces lo agregamos
            if (!yaExiste) {
                jugadoresListos.add(jugadorListo);
            }
            //System.out.println("xxxxx:)");
            //System.out.println(jugadoresListos);
        }
    }

    public boolean jugadorEstaListo(Jugador j) {
        int i = 0;
        synchronized (jugadoresListos) {
            while (i < jugadoresListos.size()) {
                if (jugadoresListos.get(i).equals(j)) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        ArrayList<String> strJugadores = new ArrayList<>();
        synchronized (jugadores) {
            int k = 0;
            while (k < jugadores.size()) {
                Jugador jugador = jugadores.get(k);
                //revisamos si ya está listo
                boolean listo = jugadorEstaListo(jugador);
                int equipo = getGrupoJugador(jugador);
                String json_ = "{\"nombre\":\"" + jugador.getNombre() + "\",\"img\":\"" + jugador.getImagen() + "\",\"record\":" + jugador.getRecord() + ",\"listo\":" + listo + ",\"equipo\":" + equipo + "}";
                strJugadores.add(json_);
                k++;
            }
        }
        return "{\"id\":\"" + id + "\",\"jugadores\":" + strJugadores.toString() + ",\"nombre\":\"" + nombre + "\",\"numJugadores\":\"" + jugadores.size() + "\",\"casiLista\":\"" + casiLista + "\",\"codigo\":\"" + codigo + "\",\"equipos\":\"" + equipos + "\",\"friendFire\":\"" + friendFire + "\",\"creador\":\"" + creador.getNombre() + "\"}";
    }

    /**
     * retorna true si la sala acaba de cerrarse y pasa a estar casi lista
     *
     * @return
     */
    public boolean cerrarSala() {
        //si ya están los jugadores mínimos requeridos para empezar
        if (!casiLista && jugadoresListos.size() >= MINIMOJUGADORES) {
            casiLista = true;
            return true;
        }
        return false;
    }

    private String generarCodigo() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < LONGITUDCODIGO) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    private int getGrupoJugador(Jugador jugador) {
        if (!equipos) {
            return -1;
        }
        synchronized (lockEquipos) {
            int i = 0;
            while (equipoA.size() > i) {
                if (equipoA.get(i).equals(jugador)) {
                    return 1;
                }
                i++;
            }
        }
        return 2;
    }

    public void eliminarJugador(Jugador j) {
        synchronized (jugadores) {
            jugadores.remove(j);
        }
        if (equipos) {
            synchronized (lockEquipos) {
                equipoA.remove(j);
                equipoB.remove(j);
            }
        }
    }

    public boolean cambiarDeGrupoJugador(Jugador jugador) {
        boolean cambio = false;
        synchronized (lockEquipos) {
            int idA = equipoA.indexOf(jugador);
            int idB = equipoB.indexOf(jugador);

            if (idA != -1) {
                if (equipoA.size() > 1) {
                    equipoA.remove(jugador);
                    equipoB.add(jugador);

                    cambio = true;
                }

            } else {

                if (equipoB.size() > 1) {
                    equipoB.remove(jugador);
                    equipoA.add(jugador);

                    cambio = true;
                }

            }

        }

        return cambio;
    }

    public boolean esEquipos() {
        return equipos;
    }
    
    public ArrayList<ArrayList<Jugador>> getJugadoresXequipos(){
        ArrayList<ArrayList<Jugador>> res= new ArrayList<>();
        res.add(equipoA);
        res.add(equipoB);
        return res;
    }
}
