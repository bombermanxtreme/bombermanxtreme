package edu.eci.arsw.bombermanx.persistencia.impl;

import edu.eci.arsw.bombermanx.persistencia.PersistenciaSala;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.model.game.entities.Sala;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kvn CF <ECI>
 */
@Service
public class PersistenciaImplSala implements PersistenciaSala {

    private ArrayList<Sala> salas;

    public PersistenciaImplSala() {
        salas = new ArrayList<>();
    }

    @Override
    public int crearSala(Jugador creador, String nombre, boolean equipos, boolean friendFire) {
        salas.add(new Sala(salas.size(),creador, nombre, equipos, friendFire));
        return salas.size()-1;
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
    public boolean addJugador(int id_sala, Jugador j) {
        return salas.get(id_sala).addJugador(j);
    }

    @Override
    public void addJugadorListo(int id_sala, Jugador jugadorListo) {
        salas.get(id_sala).addJugadorListo(jugadorListo);
    }

    @Override
    public boolean cerrarSala(int id_sala) {
        return salas.get(id_sala).cerrarSala();
    }

    @Override
    public boolean jugadorEstaListoEnSala(int idSala, Jugador jugador) {
        return salas.get(idSala).jugadorEstaListo(jugador);
    }

    @Override
    public void removeJugador(int idSala, Jugador j) {
		salas.get(idSala).eliminarJugador(j);
	}
	
	@Override
    public boolean cambiarDeGrupoJugador(int idSala, Jugador jugador) {
               
        return salas.get(idSala).cambiarDeGrupoJugador(jugador);
    }
}
