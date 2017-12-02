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
        //aca se pueden crear salas para probar
    }

    @Override
    public int crearSala(Jugador creador, String nombre, boolean equipos, boolean friendFire) {
        salas.add(new Sala(creador, nombre, equipos, friendFire));
        return salas.size();
    }

    @Override
    public ArrayList<Jugador> getJugadoresDeSala(int idSala) {
        return getSala(idSala).getJugadores();
    }

    @Override
    public void addJugadorASala(Jugador jugador, int idSala) {
        getSala(idSala).addJugador(jugador);
    }

    @Override
    public ArrayList<Sala> getSalas() {
        return salas;
    }

    @Override
    public Sala getSala(int id) {
        return salas.get(id);
    }

    @Override
    public ArrayList<Jugador> getJugadoresListos(int idSala) {
        return salas.get(idSala).getJugadoresListos();
    }

    @Override
    public boolean estaCasiLista(int id_sala) {
        return salas.get(id_sala).casiLista();
    }

    @Override
    public void setLista(int id_sala) {
        salas.get(id_sala).lista();
    }

    @Override
    public void addJugador(int id_sala, Jugador j) {
        salas.get(id_sala).addJugador(j);
    }

    @Override
    public void addJugadorListo(int id_sala, Jugador jugadorListo) {
        salas.get(id_sala).addJugadorListo(jugadorListo);
    }
}
