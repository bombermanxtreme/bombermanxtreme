/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.cache;

import edu.eci.arsw.BomberManX.model.game.Juego;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.services.GameCreationException;
import edu.eci.arsw.BomberManX.services.GameServicesException;
import java.util.ArrayList;

/**
 *
 * @author Kvn CF <ECI>
 */
public interface BomberManXCache {

    /**
     * crea un juego nuevo
     *
     * @param id
     * @param jugadores
     * @throws GameCreationException
     */
    public void createGame(int id, ArrayList<Jugador> jugadores) throws GameCreationException;

    /**
     * retorna el juego número gameid
     * @param gameid
     * @return
     * @throws GameServicesException 
     */
    public Juego getGame(int gameid) throws GameServicesException;
    
    /**
     * retorna true si el juego número gameid ya existe
     * @param gameid
     * @return
     * @throws GameServicesException 
     */
    public boolean existeGame(int gameid) throws GameServicesException;
}
