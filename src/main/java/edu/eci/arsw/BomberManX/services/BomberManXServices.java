package edu.eci.arsw.BomberManX.services;

import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaSala;
import edu.eci.arsw.BomberManX.cache.BomberManXCache;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kvn CF <ECI>
 */
@Service
public class BomberManXServices {
   
    //cache con los datos volatiles del juego
    @Autowired
    BomberManXCache cache;
    @Autowired
    PersistenciaJugador pj=null;
    @Autowired
    PersistenciaSala ps=null;
    
    public void setBpp(PersistenciaJugador bpp,PersistenciaSala ps) {
        this.pj = pj;
        this.ps = ps;
    }
    
    public Set<Jugador> getJugadoresDeSala(int id_sala){
        Set<Jugador> r= new HashSet<>();
        ArrayList<Jugador> jugadores = ps.getJugadoresDeSala(id_sala);
        for (int i = 0; i < jugadores.size(); i++)
            r.add(jugadores.get(i));
        return r;
    }
}
