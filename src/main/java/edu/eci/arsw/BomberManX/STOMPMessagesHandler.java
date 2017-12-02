/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX;

import edu.eci.arsw.BomberManX.model.game.Juego;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaSala;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    private PersistenciaJugador PJ;
    @Autowired
    private PersistenciaSala PS;

    @Autowired
    SimpMessagingTemplate msgt;

    /**
     * Permite agregar a lista de jugadores que quieren jugar
     *
     * @param id_jugador
     * @param idSala
     * @return 
     * @throws Exception
     */
    @MessageMapping("/EntrarAJuego.{idSala}")
    public boolean handleEntrarAJuego(int id_jugador, @DestinationVariable int idSala) throws Exception {
        //si la sala está casi lista ya no pueden entrar más jugadores
        if (PS.estaCasiLista(idSala)) {
            enviarListadoJugadoresQuierenJugar(idSala, false);
            return false;
        }
        Jugador j = PJ.SeleccionarJugadorPorId(id_jugador);
        PS.addJugador(idSala,j);
        enviarListadoJugadoresQuierenJugar(idSala, true);
        return true;
    }

    /**
     * Permite indicar que un jugador ya está listo
     *
     * @param id_jugador
     * @param idSala
     * @throws Exception
     */
    @MessageMapping("/JugadorListo.{idSala}")
    public void handleJugadorListo(int id_jugador, @DestinationVariable int idSala) throws Exception {
        Jugador jugadorListo = PJ.SeleccionarJugadorPorId(id_jugador);

        PS.addJugadorListo(idSala,jugadorListo);
        enviarListadoJugadoresQuierenJugar(idSala, true);
    }

    /**
     * respondemos con TODOS los jugadores incluso el nuevo recibidos y los que
     * están listos
     *
     * @param idSala
     * @param salaAbierta
     * @return 
     */
    public boolean enviarListadoJugadoresQuierenJugar(int idSala, boolean salaAbierta) {
        //url de msgt.convertAndSend();
        String url = "/topic/JugadoresQuierenJugar." + idSala;
        if (!salaAbierta) {
            msgt.convertAndSend(url, false);
            return false;
        }
        ArrayList<String> strJugadores = new ArrayList<>();
        synchronized (PS.getJugadoresDeSala(idSala)) {
            int k = 0;
            while (k < PS.getJugadoresDeSala(idSala).size()) {
                Jugador jugador = PS.getJugadoresDeSala(idSala).get(k);
                //revisamos si ya está listo
                int i = 0;
                boolean listo = false;
                ArrayList<Jugador> jugadoresListos = PS.getJugadoresListos(idSala);
                synchronized (jugadoresListos) {
                    while (!listo && i < jugadoresListos.size()) {
                        if (jugadoresListos.get(i).equals(jugador)) {
                            listo = true;
                        }
                        i++;
                    }
                }

                String json_ = "{\"nombre\":\"" + jugador.getNombre() + "\",\"record\":" + jugador.getRecord() + ",\"listo\":" + listo + "}";
                strJugadores.add(json_);
                k++;
            }
        }
        //enviamos todos los jugadores
        msgt.convertAndSend(url, strJugadores.toString());
        //si ya están los jugadores mínimos requeridos para empezar
        if (!PS.estaCasiLista(idSala) && PS.getJugadoresListos(idSala).size() >= Juego.MINIMOJUGADORES) {
            msgt.convertAndSend("/topic/ListoMinimoJugadores." + idSala, Juego.TIEMPOENSALAPARAEMPEZAR);
            PS.setLista(idSala);
        }
        return true;
    }
    
    @MessageMapping("/ponerBomba.{idSala}")
    public boolean ponerBomba(int id_jugador, @DestinationVariable int idSala) throws Exception {
        String casa;
        return true;
    }
    
    @MessageMapping("/moverPersonaje.{idSala}")
    public boolean moverPersonaje(int id_jugador, @DestinationVariable int idSala) throws Exception {
        String casa;
        return true;
    }
}
