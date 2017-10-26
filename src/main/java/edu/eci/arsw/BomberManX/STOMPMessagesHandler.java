/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX;

import edu.eci.arsw.BomberManX.Persistencia.Impl.PersistenciaImplJugador;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.model.Jugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import org.json.JSONArray;

@Controller
public class STOMPMessagesHandler {

    private Juego juego;
    @Autowired
    private PersistenciaJugador PJ;
    private ArrayList<Jugador> jugadores=new ArrayList<>();
    private boolean listoParaEmpezar=false;

    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/EntrarAJuego.{numjuego}")
    public void handleEntrarAJuego(int id_jugador, @DestinationVariable String numjuego) throws Exception {
        if(!listoParaEmpezar){
            Jugador j=PJ.SeleccionarJugadorPorId(id_jugador);
            jugadores.add(j);
            System.out.println(jugadores);
            JSONArray data=new JSONArray(jugadores);
            System.out.println(data);
            //respondemos con TODOS los jugadores incluso el nuevo recibido
            msgt.convertAndSend("/topic/EntraAJuego." + numjuego,jugadores.toString());
        }
    }

    @MessageMapping("/Empezar.{numjuego}")
    public void handleEmpezar(int id_jugador, @DestinationVariable String numjuego) throws Exception {

        msgt.convertAndSend("/topic/estadisticas." + numjuego, "{'hola':'se recibió una solicitud'}");
    }
}