package edu.eci.arsw.bombermanx.cache.stub;

import edu.eci.arsw.bombermanx.cache.BomberManXCache;
import edu.eci.arsw.BomberManX.model.game.Juego;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.services.GameCreationException;
import edu.eci.arsw.bombermanx.services.GameServicesException;
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
            /*
            try {
                String[][] tablero = TableroTexto.muestraContenido(id);
                
                gamesState.put(id, new Juego(jugadores, tablero));
            } catch (IOException ex) {
                Logger.getLogger(InMemoryBomberManXStatePersistence.class.getName()).log(Level.SEVERE, null, ex);
            }
            */
            gamesState.put(id, new Juego(jugadores, null));
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
