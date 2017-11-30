/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX;

import edu.eci.arsw.BomberManX.model.game.Juego;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    private PersistenciaJugador PJ;
    //lista jugadores que quieren jugar por "sala"
    private ConcurrentHashMap<Integer, ArrayList<Jugador>> jugadores = new ConcurrentHashMap<>();
    //lista jugadores que están listos para jugar por "sala"
    private ConcurrentHashMap<Integer, ArrayList<Jugador>> listosParaEmpezar = new ConcurrentHashMap<>();
    //lista idDeSalas que están con los mínimos jugadores listos para jugar, bloqueando el ingreso de nuevos jugadores 
    private ConcurrentHashMap<Integer, Integer> salasCasiListas = new ConcurrentHashMap<>();

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
        if (salasCasiListas.containsValue(idSala)) {
            enviarListadoJugadoresQuierenJugar(idSala, false);
            return false;
        }
        //si no se ha creado se crea (jugadores listos) y (jugadores)
        if (!listosParaEmpezar.containsKey(idSala)) {
            ArrayList<Jugador> tmp = new ArrayList<>();
            listosParaEmpezar.put(idSala, tmp);
            tmp = new ArrayList<>();
            jugadores.put(idSala, tmp);
        }
        Jugador j = PJ.SeleccionarJugadorPorId(id_jugador);
        jugadores.get(idSala).add(j);
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

        listosParaEmpezar.get(idSala).add(jugadorListo);
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
        synchronized (jugadores.get(idSala)) {
            int k = 0;
            while (k < jugadores.get(idSala).size()) {
                Jugador jugador = jugadores.get(idSala).get(k);
                //revisamos si ya está listo
                int i = 0;
                boolean listo = false;
                ArrayList<Jugador> jugadoresListos = listosParaEmpezar.get(idSala);
                synchronized (jugadoresListos) {
                    while (!listo && i < jugadoresListos.size()) {
                        if (jugadoresListos.get(i) == jugador) {
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
        if (!salasCasiListas.containsValue(idSala) && listosParaEmpezar.get(idSala).size() >= Juego.MINIMOJUGADORES) {
            msgt.convertAndSend("/topic/ListoMinimoJugadores." + idSala, Juego.TIEMPOENSALAPARAEMPEZAR);
            salasCasiListas.put(salasCasiListas.size(), idSala);
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
