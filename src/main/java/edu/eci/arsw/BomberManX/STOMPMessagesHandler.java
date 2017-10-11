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
import edu.eci.arsw.BomberManX.model.Point;
import java.util.ArrayList;

@Controller
public class STOMPMessagesHandler {

    ArrayList<Point> lista = new ArrayList<>();

    @Autowired
    SimpMessagingTemplate msgt;

    @MessageMapping("/newpoint.{numdibujo}")
    public void handlePointEvent(Point pt, @DestinationVariable String numdibujo) throws Exception {
        synchronized (lista) {
            lista.add(pt);
            if (lista.size() == 4) {
                msgt.convertAndSend("/topic/newpolygon." + numdibujo, lista);
                lista = new ArrayList<>();
            }
        }
    }
}