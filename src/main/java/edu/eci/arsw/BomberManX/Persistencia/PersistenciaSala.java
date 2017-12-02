/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Persistencia;

import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.Sala;
import java.util.ArrayList;

/**
 *
 * @author Kvn CF <ECI>
 */
public interface PersistenciaSala {

    /**
     * permite crear una sala
     * @param creador
     * @param nombre
     * @param equipos
     * @param friendFire
     * @return 
     */
    abstract public int crearSala(Jugador creador, String nombre, boolean equipos, boolean friendFire);
    
    /**
     * retorna una sala pedida
     * @param id
     * @return 
     */
    abstract public Sala getSala(int id);

    /**
     * retorna los jugadores actuales de una sala
     *
     * @param idSala
     * @return lista de jugadores de la sala específica
     */
    abstract public ArrayList<Jugador> getJugadoresDeSala(int idSala);

    /**
     * agrega un jugador a la sala
     *
     * @param jugador
     * @param idSala
     */
    abstract public void addJugadorASala(Jugador jugador, int idSala);
    
    /**
     * retorna las salas
     * @return 
     */
    abstract public ArrayList<Sala> getSalas();

    /**
     * retorna los jugadores que ya están listos para empezar a jugar
     * @param idSala
     * @return 
     */
    public ArrayList<Jugador> getJugadoresListos(int idSala);

    public boolean estaCasiLista(int id_sala);

    public void setLista(int id_sala);

    public void addJugador(int id_sala, Jugador j);

    public void addJugadorListo(int id_sala, Jugador jugadorListo);
}
