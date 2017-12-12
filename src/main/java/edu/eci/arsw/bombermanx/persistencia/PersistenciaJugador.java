package edu.eci.arsw.bombermanx.persistencia;

import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Kvn CF <ECI>
 */
public interface PersistenciaJugador extends MongoRepository<Jugador, Integer>{

    /**
     * Agregar un jugador nuevo
     * @param entity 
     */
    public void insert(PersistenciaJugador entity);

    /**
     * busca dentro de los jugadores creados basado en el id
     *
     * @param idJugador
     * @return jugador
     */
    public Jugador findById(Integer idJugador);

    /**
     * devuelve los jugadores registrados
     *
     * @return
     */
    @Override
    public List<Jugador> findAll();

    /**
     * devuelve el id del jugador segun el correo, -1 si no esta
     *
     * @param correo
     * @return
     */
    public Jugador findFirstByCorreo(String correo);
}
