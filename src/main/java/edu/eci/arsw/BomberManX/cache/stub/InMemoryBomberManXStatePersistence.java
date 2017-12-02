/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.cache.stub;

import edu.eci.arsw.BomberManX.cache.BomberManXCache;
import edu.eci.arsw.BomberManX.model.game.Juego;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.TableroTexto;
import edu.eci.arsw.BomberManX.services.GameCreationException;
import edu.eci.arsw.BomberManX.services.GameServicesException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        preloadGames();
    }

    @Override
    public void createGame(int id, ArrayList<Jugador> jugadores) throws GameCreationException {
        if (gamesState.containsKey(id)) {
            throw new GameCreationException("el juego " + id + " ya existe.");
        } else {
            try {
                String[][] tablero = TableroTexto.muestraContenido(id);
                
                gamesState.put(id, new Juego(jugadores, tablero));
            } catch (IOException ex) {
                Logger.getLogger(InMemoryBomberManXStatePersistence.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Juego getGame(int gameid) throws GameServicesException {
        if (!existeGame(gameid)) {
            throw new GameServicesException("El juego " + gameid + " no existe.");
        } else {
            return gamesState.get(gameid);
        }
    }

    @Override
    public boolean existeGame(int gameid) throws GameServicesException {
        return gamesState.containsKey(gameid);
	}
    
    private void preloadGames(){
        //para probar sala 100
           
        ArrayList<Jugador>  jugadores = new ArrayList<>();
        
        jugadores.add(new Jugador("Prueba", "pr@server.com", "jugador prueba", "123", ""));
        
        Juego juego = new Juego(jugadores);
       
    }
}
