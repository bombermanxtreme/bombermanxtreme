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
        System.out.println("***************************************** Estoy entrando a obtener jugadres de SALA " + idSala + " Numero de salas: " + salas.get(0));
        return salas.get(idSala-1).getJugadores();
    }

    @Override
    public void addJugadorASala(Jugador jugador, int idSala) {
        salas.get(idSala-1).addJugador(jugador);
    }
}
