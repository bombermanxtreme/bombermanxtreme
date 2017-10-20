/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;

@Controller
public class STOMPMessagesHandler {

	private Juego juego;

    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/Empezar.{numjuego}")
    public void handlePointEvent(int prueba, @DestinationVariable String numjuego) throws Exception {
		
		msgt.convertAndSend("/topic/estadisticas." + numjuego, "{'hola':'se recibió una solicitud'}");
    }
    
    public void hola(){
        msgt.convertAndSend("/topic/estadisticas.1", "holaa");
    }
}