/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Persistencia;

import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import java.util.ArrayList;

/**
 *
 * @author Kvn CF <ECI>
 */
public interface PersistenciaSala {
    
    /**
     * crea una sala nueva
     * @return idSala
     */
    abstract public int crearSala();
    
    /**
     * retorna los jugadores actuales de una sala
     * @param idSala
     * @return lista de jugadores de la sala específica
     */
    abstract public ArrayList<Jugador> getJugadoresDeSala(int idSala);
    
    /**
     * agrega un jugador a la sala
     * @param jugador
     * @param idSala
     */
    abstract public void addJugadorASala(Jugador jugador, int idSala);
}
