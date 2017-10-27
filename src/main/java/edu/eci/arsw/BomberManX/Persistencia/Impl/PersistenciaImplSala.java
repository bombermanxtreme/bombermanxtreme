/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Persistencia.Impl;

import edu.eci.arsw.BomberManX.Persistencia.PersistenciaSala;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.Sala;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kvn CF <ECI>
 */
@Service
public class PersistenciaImplSala implements PersistenciaSala {

    private ArrayList<Sala> salas = new ArrayList<Sala>();

    public PersistenciaImplSala() {
        crearSala();
    }

    @Override
    public int crearSala() {
        salas.add(new Sala());
        return salas.size();
    }

    @Override
    public ArrayList<Jugador> getJugadoresDeSala(int idSala) {
        return salas.get(idSala).getJugadores();
    }

    @Override
    public void addJugadorASala(Jugador jugador, int idSala) {
        salas.get(idSala).addJugador(jugador);
    }
}
