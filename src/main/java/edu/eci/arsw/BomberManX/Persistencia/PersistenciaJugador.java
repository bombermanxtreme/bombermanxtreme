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
public interface PersistenciaJugador {

    /**
     * Agregar un jugador nuevo
     *
     * @param nombre
     * @param correo
     * @param clave
     */
    abstract public void AgregarJugador(String nombre, String correo, String clave);

    /**
     * busca dentro de los jugadores creados basado en el id
     *
     * @param idJugador
     * @return jugador
     */
    abstract public Jugador SeleccionarJugadorPorId(int idJugador);
    
    /**
     * devuelve los jugadores registrados
     *
     * @return
     */
    public ArrayList<Jugador> getJugadores();

    /**
     * devuelve el id del jugador segun el correo, -1 si no esta
     *
     * @param correo
     * @return
     */
    public int getIDPorCorreo(String correo);
}
