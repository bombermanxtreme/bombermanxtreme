/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.bombermanx.cache.redis; 

import edu.eci.arsw.bombermanx.model.game.Juego;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import java.util.ArrayList;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 *
 * @author 2095112
 * hset
 * game:1 palabra foot adivino ____ ganador null terminado 0
 * 0: false, 1: true
 */
public class HangmanRedisGame extends Juego{
  
    public HangmanRedisGame(ArrayList<ArrayList<Jugador>> jugadoresEquipos, String[][] tableroTemporal, boolean esEquipos) {
        super(jugadoresEquipos, tableroTemporal, esEquipos);
    }
  
}