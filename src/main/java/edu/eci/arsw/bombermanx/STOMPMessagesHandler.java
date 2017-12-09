package edu.eci.arsw.bombermanx;

import edu.eci.arsw.bombermanx.persistencia.PersistenciaJugador;
import edu.eci.arsw.bombermanx.persistencia.PersistenciaSala;
import edu.eci.arsw.bombermanx.model.game.entities.Elemento;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.model.game.entities.Man;
import edu.eci.arsw.bombermanx.model.game.entities.Sala;
import edu.eci.arsw.bombermanx.model.game.entities.TableroTexto;
import edu.eci.arsw.bombermanx.services.BomberManXServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    private PersistenciaJugador PJ;
    @Autowired
    private PersistenciaSala PS;

    @Autowired
    SimpMessagingTemplate msgt;
    
    @Autowired
    BomberManXServices gameServices;

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
        Jugador j = PJ.seleccionarJugadorPorId(id_jugador);
        if (PS.estaCasiLista(idSala) || !PS.addJugador(idSala,j)) {
            enviarListadoJugadoresQuierenJugar(idSala, false);
            return false;
        }
        enviarListadoJugadoresQuierenJugar(idSala, true);
        return true;
    }
    /**
     * permite salir de la sala
     * @param id_jugador
     * @param idSala
     * @throws Exception 
     */
    @MessageMapping("/Salir/Sala.{idSala}")
    public void handleSalirDeSala(int id_jugador, @DestinationVariable int idSala) throws Exception {
        //si la sala está casi lista ya no pueden entrar más jugadores
        Jugador j = PJ.seleccionarJugadorPorId(id_jugador);
        PS.removeJugador(idSala,j);
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
        Jugador jugadorListo = PJ.seleccionarJugadorPorId(id_jugador);
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
        String url = "/topic/Sala." + idSala;
        if (!salaAbierta) {
            msgt.convertAndSend(url, false);
            return false;
        }
        
        //enviamos todas las características de la sala incluido los jugadores
        msgt.convertAndSend(url, PS.getSala(idSala).toString());
        if(PS.cerrarSala(idSala))
            msgt.convertAndSend("/topic/ListoMinimoJugadores." + idSala, Sala.TIEMPOENSALAPARAEMPEZAR);
        return true;
    }
    
    @MessageMapping("/CambiarGrupo/Sala.{idSala}")
    public void handleCambiarGrupo(int id_jugador, @DestinationVariable int idSala) throws Exception {
        Jugador jugador = PJ.seleccionarJugadorPorId(id_jugador);
        PS.cambiarDeGrupoJugador(idSala,jugador);
        enviarListadoJugadoresQuierenJugar(idSala, true);
    }
    
    @MessageMapping("/AccionBomba.{idSala}")
    public boolean accionBomba(int id_jugador, @DestinationVariable int idSala) throws Exception {
        System.out.println("entra a java----- para bomba");
        System.out.println(id_jugador);
        Jugador j=PJ.seleccionarJugadorPorId(id_jugador);
        return gameServices.accionBomba(idSala,j);
    }
    
    // Author: Kevin S. Sanchez (DOCUEMENTACION)
    @MessageMapping("/mover.{idSala}.{key}")
    public boolean moverPersonaje(@DestinationVariable int idSala, @DestinationVariable int key, int id_jugador) throws Exception {
        System.out.println("///// edu.eci.arsw.bombermanx.STOMPMessagesHandler.moverPersonaje(): Sala: " + idSala + " - Jugador: " + id_jugador + " - Tecla: " + key);
        synchronized (msgt) {
            Jugador j = PJ.seleccionarJugadorPorId(id_jugador);
            return gameServices.accionMover(idSala, j, key);
        }
    }
    
    public static boolean isNumeric(String str){  
      try{  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe){  
        return false;  
      }  
      return true;  
    }
}
