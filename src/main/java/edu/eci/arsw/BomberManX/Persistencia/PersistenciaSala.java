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

    /**
     * retorna true si ya están el mínimo número de jugadores LISTOS
     * @param id_sala
     * @return 
     */
    public boolean estaCasiLista(int id_sala);

    /**
     * agrega un jugador a la sala
     * @param id_sala
     * @param j
     * @return  true si es posible o false si ya está llena
     */
    public boolean addJugador(int id_sala, Jugador j);

    /**
     * indicar que un jugador que está en la sala, YA ESTÁ LISTO!
     * @param id_sala
     * @param jugadorListo 
     */
    public void addJugadorListo(int id_sala, Jugador jugadorListo);

    /**
     * cierra una sala y retorna true SOLO LA PRIMERA vez, sala cerra NO permite entrar a nadie más
     * @param id_sala
     * @return 
     */
    public boolean cerrarSala(int id_sala);

    /**
     * revisa si un jugador ya está listo para empezar a jugar en una sala
     * @param idSala
     * @param jugador
     * @return 
     */
    public boolean jugadorEstaListoEnSala(int idSala, Jugador jugador);

    /**
     * elimina el jugador de la sala
     * @param idSala
     * @param j 
     */
    public void removeJugador(int idSala, Jugador j);
    
    /**
     * El jugador cambia de sala 
     * @param idSala
     * @param jugador 
     * @return  
     */
    public boolean cambiarDeGrupoJugador(int idSala, Jugador jugador);
}
