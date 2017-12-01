/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.cache.stub;

import edu.eci.arsw.BomberManX.cache.BomberManXCache;
import edu.eci.arsw.BomberManX.model.game.Juego;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.services.GameCreationException;
import edu.eci.arsw.BomberManX.services.GameServicesException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kvn CF <ECI>
 */
@Service
public class InMemoryBomberManXStatePersistence implements BomberManXCache {

    private final ConcurrentHashMap<Integer, Juego> gamesState;

    public InMemoryBomberManXStatePersistence() {
        gamesState = new ConcurrentHashMap<>();
    }

    @Override
    public void createGame(int id, ArrayList<Jugador> jugadores) throws GameCreationException {
        if (gamesState.containsKey(id)) {
            throw new GameCreationException("el juego " + id + " ya existe.");
        } else {
            gamesState.put(id, new Juego(jugadores));
        }
    }

    @Override
    public Juego getGame(int gameid) throws GameServicesException {
        if (!gamesState.containsKey(gameid)) {
            throw new GameServicesException("El juego " + gameid + " no existe.");
        } else {
            return gamesState.get(gameid);
        }
    }
}
