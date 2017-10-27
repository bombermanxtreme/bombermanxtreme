/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Persistencia;

import edu.eci.arsw.BomberManX.model.game.entities.Jugador;

/**
 *
 * @author Kvn CF <ECI>
 */
public interface PersistenciaJugador {
    /**
     * Agregar un jugador nuevo
     * @param nombre
     * @param correo
     * @param clave 
     */
    abstract public void AgregarJugador(String nombre,String correo,String clave);
    
    /**
     * busca dentro de los jugadores creados basado en el id
     * @param idJugador
     * @return jugador
     */
    abstract public Jugador SeleccionarJugadorPorId(int idJugador);
    
    /**
     * busca el jugador en los jugadores existentes
     * @param correo
     * @param clave
     * @return
     */
    abstract public int loginJugador(String correo, String clave);
}
