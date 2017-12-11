/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.bombermanx.cache.redis; 

import edu.eci.arsw.bombermanx.cache.BomberManXCache;
import edu.eci.arsw.bombermanx.cache.stub.InMemoryBomberManXStatePersistence;
import edu.eci.arsw.bombermanx.model.game.Juego;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.persistencia.RedisException;
import edu.eci.arsw.bombermanx.recursos.TableroTexto;
import edu.eci.arsw.bombermanx.services.GameCreationException;
import edu.eci.arsw.bombermanx.services.GameServicesException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author 
 */
@Service
public class GameStateRedisCache implements BomberManXCache{
    @Autowired
    private StringRedisTemplate template;

    @Override
    public void createGame(int id, ArrayList<ArrayList<Jugador>> jugadores, boolean esEquipos) throws GameCreationException {
        String[][] tablero=null;
        try {
            tablero = TableroTexto.muestraContenido(0);
        } catch (IOException ex) {
            Logger.getLogger(GameStateRedisCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        new BombarmanXRedisGame(template, id, jugadores, tablero, esEquipos);
    }

    @Override
    public Juego getGame(int gameid) throws GameServicesException {
        BombarmanXRedisGame bxg=null;
        try {
            bxg=new BombarmanXRedisGame(template, gameid);
        } catch (RedisException ex) {
            Logger.getLogger(GameStateRedisCache.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage()
            );
        }
        return bxg;
    }

    @Override
    public boolean existeGame(int gameid) throws GameServicesException {
        return true;
    }
}
