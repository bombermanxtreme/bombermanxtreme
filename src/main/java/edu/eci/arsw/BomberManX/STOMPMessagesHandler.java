/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX;

import edu.eci.arsw.BomberManX.model.game.Juego;
import edu.eci.arsw.BomberManX.Persistencia.Impl.PersistenciaImplJugador;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;

@Controller
public class STOMPMessagesHandler {

    private Juego juego;
    @Autowired
    private PersistenciaJugador PJ;
    private ConcurrentHashMap<Integer,ArrayList<Jugador>> jugadores=new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,ArrayList<Jugador>> listosParaEmpezar=new ConcurrentHashMap<>();

    @Autowired
    SimpMessagingTemplate msgt;

    /**
     * Permite agregar a lista de jugadores que quieren jugar
     * @param id_jugador
     * @param numjuego
     * @throws Exception 
     */
    @MessageMapping("/EntrarAJuego.{numjuego}")
    public void handleEntrarAJuego(int id_jugador, @DestinationVariable int numjuego) throws Exception {
        //si no se ha creado se crea
        if(!listosParaEmpezar.containsKey(numjuego)){
            ArrayList<Jugador> v=new ArrayList<>();
            listosParaEmpezar.put(numjuego, v);
        }
        if(listosParaEmpezar.get(numjuego).size()<2){
            Jugador j=PJ.SeleccionarJugadorPorId(id_jugador);
            jugadores.get(numjuego).add(j);
            enviarListadoJugadoresQuierenJugar(numjuego);
        }
    }

    /**
     * Permite indicar que un jugador ya está listo
     * @param id_jugador
     * @param numjuego
     * @throws Exception 
     */
    @MessageMapping("/JugadorListo.{numjuego}")
    public void handleJugadorListo(int id_jugador, @DestinationVariable int numjuego) throws Exception {
        listosParaEmpezar.get(numjuego).add(jugadores.get(numjuego).get(id_jugador));
        enviarListadoJugadoresQuierenJugar(numjuego);
    }
    
    /**
     * respondemos con TODOS los jugadores incluso el nuevo recibidos y los que están listos
     * @param numjuego 
     */
    public void enviarListadoJugadoresQuierenJugar(int numjuego){
        ArrayList<String> strJugadores=new ArrayList<>();
        synchronized(jugadores.get(numjuego)){
            int k=0;
            while(k<jugadores.get(numjuego).size()){
                Jugador jugador = jugadores.get(numjuego).get(k);
                //revisamos si ya está listo
                int i=0;
                boolean listo=false;
                ArrayList<Jugador> jugadoresListos=listosParaEmpezar.get(numjuego);
                synchronized(jugadoresListos){
                    while(!listo && i<jugadoresListos.size()){
                        if(jugadoresListos.get(i)==jugador)
                            listo=true;
                        i++;
                    }
                }

                String json_="{nombre:"+jugador.getNombre()+",record:"+jugador.getRecord()+",listo:"+listo+"}";
                strJugadores.add(json_);
                k++;
            }
        }
        
        msgt.convertAndSend("/topic/JugadoresQuierenJugar." + numjuego,strJugadores.toString());
    }
}