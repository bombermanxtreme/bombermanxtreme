package edu.eci.arsw.bombermanx.controllers;

import edu.eci.arsw.bombermanx.model.game.Juego;
import edu.eci.arsw.bombermanx.services.BomberManXServices;
import edu.eci.arsw.bombermanx.services.GameCreationException;
import edu.eci.arsw.bombermanx.services.GameServicesException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kevin S. Sanchez
 */
@RestController
@RequestMapping("/canvas")
public class BomberManXAPIControllerCanvas {

    @Autowired
    private BomberManXServices gameServices;

    @Autowired
    private SimpMessagingTemplate msmt;

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "Ok";
    }

    private static final Logger LOG = Logger.getLogger(BomberManXAPIControllerCanvas.class.getName());
    
    // Author: Kevin S. Sanchez (DOCUMENTACIÓN)
    @RequestMapping(path = "/tablero/{id_sala}", method = RequestMethod.GET)
    public ResponseEntity<?> getTablero(@PathVariable int id_sala) throws GameCreationException {
        
        try{
            try {
                
                if(!gameServices.existeGame(id_sala)){
                    gameServices.createGame(id_sala);
                }
            } catch (GameCreationException ex) {
                Logger.getLogger(BomberManXAPIControllerCanvas.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(gameServices.existeGame(id_sala)){
                Juego j = gameServices.getGame(id_sala);
                return new ResponseEntity<>(j.toString(), HttpStatus.ACCEPTED);
            }
            else return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); 
        } catch (GameServicesException | NumberFormatException ex) {
            Logger.getLogger(BomberManXAPIControllerCanvas.class.getName()).log(Level.SEVERE, null, ex);     
        }
        return null;
    }
}