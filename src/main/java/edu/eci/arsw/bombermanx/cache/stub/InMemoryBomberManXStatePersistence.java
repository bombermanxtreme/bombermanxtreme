package edu.eci.arsw.bombermanx.cache.stub;

import edu.eci.arsw.bombermanx.cache.BomberManXCache;
import edu.eci.arsw.bombermanx.model.game.Juego;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.recursos.TableroTexto;
import edu.eci.arsw.bombermanx.services.GameCreationException;
import edu.eci.arsw.bombermanx.services.GameServicesException;
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
    }

    @Override
    public void createGame(int id, ArrayList<Jugador> jugadores, boolean esEquipos) throws GameCreationException {
        if (gamesState.containsKey(id)) {
            throw new GameCreationException("el juego " + id + " ya existe.");
        } else {
            String[][] tablero; //= null;
            try {
                tablero = TableroTexto.muestraContenido(id);
                
                gamesState.put(id, new Juego(jugadores, tablero, esEquipos));
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
    
}
