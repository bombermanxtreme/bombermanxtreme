package edu.eci.arsw.bombermanx.services;

import edu.eci.arsw.bombermanx.cache.BomberManXCache;
import edu.eci.arsw.bombermanx.model.game.Juego;
import edu.eci.arsw.bombermanx.model.game.entities.Bomba;
import edu.eci.arsw.bombermanx.model.game.entities.Caja;
import edu.eci.arsw.bombermanx.model.game.entities.Elemento;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.model.game.entities.Man;
import edu.eci.arsw.bombermanx.model.game.entities.Sala;
import edu.eci.arsw.bombermanx.persistencia.PersistenciaJugador;
import edu.eci.arsw.bombermanx.persistencia.PersistenciaSala;
import java.awt.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 *
 * @author Kvn CF <ECI>
 */
@Service
public class BomberManXServices {

    //cache con los datos volatiles del juego
    @Autowired
    private BomberManXCache cache;
    @Autowired
    private PersistenciaJugador pj;// = null;
    @Autowired
    private PersistenciaSala ps;// = null;
    @Autowired
    private SimpMessagingTemplate msgt;

    /**
     * crea un juego nuevo
     *
     * @param idSala
     * @throws GameCreationException
     */
    public void createGame(int idSala) throws GameCreationException {
        if (ps.getSala(idSala) != null) {
            cache.createGame(idSala, ps.getJugadoresListos(idSala), ps.esEquipos(idSala));
        } else {
            throw new GameCreationException("Sala de juego ya no existe");
        }
        //System.out.println("Juego creado en CreateGame");
    }

    /**
     * verifica si un juego ya existe
     *
     * @param idSala
     * @return
     * @throws GameCreationException
     * @throws GameServicesException
     */
    public boolean existeGame(int idSala) throws GameCreationException, GameServicesException {
        return cache.existeGame(idSala);
    }

    /**
     * retorna los jugadores que se encuentran en una sala
     *
     * @param idSala
     * @return
     */
    public Set<Jugador> getJugadoresDeSala(int idSala) {
        Set<Jugador> r = new HashSet<>();
        ArrayList<Jugador> jugadores = ps.getJugadoresDeSala(idSala);
        for (int i = 0; i < jugadores.size(); i++) {
            r.add(jugadores.get(i));
        }
        return r;
    }

    /**
     * permite verificar para el inicio de sesión del usuario
     *
     * @param correo
     * @param clave
     * @return
     */
    public int loginJugador(String correo, String clave) {
        int id_login = -1; //-1: no se encontro el correo, -2: clave incorrecta

        ArrayList<Jugador> jugadores = pj.getJugadores();

        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCorreo().equals(correo)) {

                if (jugadores.get(i).getClave().equals(clave)) {
                    id_login = pj.getIDPorCorreo(correo);
                } else {
                    id_login = -2;
                }
            }
        }
        return id_login;
    }

    /**
     * Registra un jugador
     *
     * @param nombre
     * @param correo
     * @param apodo
     * @param clave
     * @param imagen
     * @return id del usuario
     */
    public int registrerJugador(String nombre, String correo, String apodo, String clave, String imagen) {
        int id_registro = -1;// -1: algo fallo, -2: correo ya existe, -3: apodo ya existe

        // No importa si no tiene imagen (avatar)
        boolean correo_valido = true;
        boolean apodo_valido = true;

        ArrayList<Jugador> jugadores = pj.getJugadores();

        //el correo debe ser unico
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCorreo().equals(correo)) {
                correo_valido = false;
                id_registro = -2;
            }
        }

        //el apodo debe ser unico
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getApodo().equals(apodo)) {
                apodo_valido = false;
                id_registro = -3;
            }
        }

        if (correo_valido && apodo_valido) {
            jugadores.add(new Jugador(nombre, correo, apodo, clave, imagen));
            id_registro = pj.getIDPorCorreo(correo);

            //jugadores diponibles
            //System.out.println("----- jugadores disponibles ---");
            for (int i = 0; i < jugadores.size(); i++) {
                //System.out.println(jugadores.get(i));
            }

        }

        return id_registro;
    }

    /**
     * retorna la URL de la foto de un usuario
     *
     * @param correo
     * @return
     */
    public String getUrl(String correo) {
        String url = pj.getUrlPorCorreo(correo);
        return url;
    }

    /**
     * retorna las salas existentes
     *
     * @return
     */
    public Set<Sala> getSalas() {
        Set<Sala> r = new HashSet<>();
        ArrayList<Sala> salas = ps.getSalas();
        for (int i = 0; i < salas.size(); i++) {
            r.add(salas.get(i));
        }
        return r;
    }

    /**
     * permite crear una sala
     *
     * @param creador
     * @param nombre
     * @param equipos
     * @param friendFire
     * @return
     */
    public int crearSala(Jugador creador, String nombre, boolean equipos, boolean friendFire) {
        return ps.crearSala(creador, nombre, equipos, friendFire);
    }

    /**
     * retorna un juego
     *
     * @param idSala
     * @return
     * @throws GameServicesException
     */
    public Juego getGame(int idSala) throws GameServicesException {
        return cache.getGame(idSala);
    }

    public String getNombreJugador(int id_jugador) throws GameServicesException {
        return pj.seleccionarJugadorPorId(id_jugador).getApodo();
    }

    public boolean accionBomba(int idSala, Jugador j) throws GameServicesException {
        Juego juego = cache.getGame(idSala);
        Bomba bomba = juego.accionBomba(j);
        boolean res = false;

        if (bomba != null) {
            msgt.convertAndSend("/topic/AccionBomba." + idSala, "{\"bomba\":" + bomba.toString() + "}");
            msgt.convertAndSend("/topic/Estadisticas." + idSala, juego.estadisticasJuego());

            res = true;
            Timer t = new Timer(Juego.TIEMPOEXPLOTARBOMBAS, (ActionEvent e) -> {
                bomba.getTimer().stop();
                bomba.estalla();

                String strCoords = new String();

                ArrayList<Object> afectados = juego.explotar(bomba);

                ArrayList<int[]> listaTemp = ((ArrayList<int[]>) afectados.get(1));
                int x;
                int y;

                for (int i = 0; i < listaTemp.size(); i++) {
                    y = listaTemp.get(i)[0];
                    x = listaTemp.get(i)[1];

                    strCoords += "{\"x\":" + x + ",\"y\":" + y + "},";
                }

                msgt.convertAndSend("/topic/AccionBomba." + idSala,
                        "{\"bomba\":" + bomba.toString() + ",\"coords\":[" + strCoords + "]}");

                ArrayList<Elemento> tmp_eleme = (ArrayList<Elemento>) afectados.get(0);
                for (int i = 0; i < tmp_eleme.size(); i++) {
                    Elemento ele = tmp_eleme.get(i);
                    Elemento quedaPoder = juego.explotarElemento(ele);
                    if (ele instanceof Caja) {
                        msgt.convertAndSend("/topic/DaniarCaja." + idSala,
                                "{\"caja\":" + ele.toString() + ",\"queda\":" + quedaPoder.toString() + "}");
                    }
                    if (ele instanceof Man) {
                        msgt.convertAndSend("/topic/ManQuemado." + idSala, ele.toString());
                        if (quedaPoder != null) {
                            msgt.convertAndSend("/topic/DaniarCaja." + idSala,
                                    "{\"caja\":false,\"queda\":" + quedaPoder.toString() + "}");
                        }
                    }
                }
                msgt.convertAndSend("/topic/Estadisticas." + idSala, juego.estadisticasJuego());
            });
            t.start();
            bomba.setTimer(t);
        }

        return res;
    }

    public boolean accionMover(int idSala, Jugador j, int key) throws GameServicesException {
        Juego juego = cache.getGame(idSala);
        boolean res = false;
        if (juego != null) {
            res = true;
            ArrayList<Elemento> changes = juego.moverPersonaje(j, key);
            //System.out.println("///// Tamaño cambios: " + changes.size());
            if (changes.size() > 0) {
                ////System.out.println("++++ Me pude mover :D: " + changes.get(0).toString() + " - " + changes.get(1).toString());
                msgt.convertAndSend("/topic/actualizar." + idSala, changes.toString());
                msgt.convertAndSend("/topic/Estadisticas." + idSala,juego.estadisticasJuego());
            }
        }
        return res;
    }    
    
}
