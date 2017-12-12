package edu.eci.arsw.bombermanx.persistencia;

import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import java.util.ArrayList;

/**
 *
 * @author Kvn CF <ECI>
 */
public interface PersistenciaJugador {

    /**
     * Pobla jugadores
     *
     */
    public abstract void poblar();

    /**
     * Agregar un jugador nuevo
     *
     * @param nombre
     * @param correo
     * @param apodo
     * @param clave
     * @param imagen
     */
    abstract public void agregarJugador(String nombre, String correo, String apodo, String clave, String imagen);

    /**
     * busca dentro de los jugadores creados basado en el id
     *
     * @param idJugador
     * @return jugador
     */
    abstract public Jugador seleccionarJugadorPorId(int idJugador);

    /**
     * devuelve los jugadores registrados
     *
     * @return
     */
    public ArrayList<Jugador> getJugadores();

    /**
     * devuelve el id del jugador segun el correo, -1 si no esta
     *
     * @param correo
     * @return
     */
    public int getIDPorCorreo(String correo);

    /**
     * Retorna la url de la imagen del jugador
     *
     * @param correo
     * @return
     */
    public String getUrlPorCorreo(String correo);

}
