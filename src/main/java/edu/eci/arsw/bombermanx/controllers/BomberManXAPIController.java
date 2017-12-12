package edu.eci.arsw.bombermanx.controllers;

import edu.eci.arsw.bombermanx.persistencia.PersistenciaJugador;
import edu.eci.arsw.bombermanx.persistencia.PersistenciaSala;
import edu.eci.arsw.bombermanx.model.game.attempt.CrearSalaAttempt;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.model.game.entities.Sala;
import edu.eci.arsw.bombermanx.services.BomberManXServices;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Kvn CF <ECI>
 */
@RestController
@RequestMapping(value = "/sala")
public class BomberManXAPIController {

    @Autowired
    BomberManXServices gc = null;
    @Autowired
    PersistenciaJugador PJ = null;
    @Autowired
    PersistenciaSala PS = null;
    @Autowired
    SimpMessagingTemplate msgt;

    private static final Logger LOG = Logger.getLogger(BomberManXAPIController.class.getName());
    
    /**
     * Responde a una petición get todos los Blueprints del author
     *
     * @param id_sala
     * @param model
     * @return
     */
    @RequestMapping(path = "/{id_sala}/Jugadores", method = RequestMethod.GET)
    public ResponseEntity<?> getJugadoresDeSala(@PathVariable int id_sala, Model model) {
        try {
            Set<Jugador> data = gc.getJugadoresDeSala(id_sala);
            if (data.isEmpty()) {
                throw new Exception("NOT FOUND");
            }
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BomberManXAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Sala no encontrada: " + id_sala, HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * retorna todas las salas
     * @param model
     * @return 
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<?> getSalas(Model model) {
        try {
            if(PS.getSalas().isEmpty()){
                PS.crearSala(PJ.seleccionarJugadorPorId(0),"Los BOMBERS",true,true);
                PS.crearSala(PJ.seleccionarJugadorPorId(0),"Los perdidos",false,true);
                PS.crearSala(PJ.seleccionarJugadorPorId(0),"Los Devs",false,true);
            }
            
            Set<Sala> data = gc.getSalas();
            return new ResponseEntity<>(data.toString(), HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(BomberManXAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Buscando salas", HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * crea una sala
     * @param model
     * @param csa
     * @return 
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> newSala(Model model, @RequestBody CrearSalaAttempt csa) {
        try {
            int id=gc.crearSala(PJ.seleccionarJugadorPorId(csa.getId_jugador()), csa.getNombre(), csa.isEquipos(), csa.isFuegoamigo());
            Set<Sala> dataTopic = gc.getSalas();
            msgt.convertAndSend("/topic/Salas", dataTopic.toString());
            return new ResponseEntity<>(id, HttpStatus.ACCEPTED);
        } catch (MessagingException ex) {
            Logger.getLogger(BomberManXAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Creando sala", HttpStatus.NOT_FOUND);
        }
    }
}
