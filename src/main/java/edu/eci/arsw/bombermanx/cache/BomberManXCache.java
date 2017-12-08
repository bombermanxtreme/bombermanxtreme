package edu.eci.arsw.bombermanx.cache;


import edu.eci.arsw.bombermanx.model.game.Juego;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.services.GameCreationException;
import edu.eci.arsw.bombermanx.services.GameServicesException;
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
