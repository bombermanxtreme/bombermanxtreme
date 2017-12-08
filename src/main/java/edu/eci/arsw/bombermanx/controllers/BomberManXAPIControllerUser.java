package edu.eci.arsw.bombermanx.controllers;

import edu.eci.arsw.bombermanx.model.game.attempt.CrearUsuarioAttempt;
import edu.eci.arsw.bombermanx.services.BomberManXServices;
import edu.eci.arsw.bombermanx.services.GameServicesException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sergioxl
 */
@RestController
@RequestMapping("/users")
public class BomberManXAPIControllerUser {

    @Autowired
    BomberManXServices gameServices;

    @Autowired
    SimpMessagingTemplate msmt;

    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "Ok";
    }

    private static final Logger LOG = Logger.getLogger(BomberManXAPIControllerUser.class.getName());

    /**
     * Permite validar datos de sesion
     *
     * @param correo
     * @param clave
     * @return
     */
    @RequestMapping(path = "/{correo}/{clave}", method = RequestMethod.GET)
    public ResponseEntity<?> loginJugador(@PathVariable String correo, @PathVariable String clave) {
        HttpStatus status;
        int id_login = gameServices.loginJugador(correo, clave);
        String nombre_jugador = "Sin definir";
        
        try {
            nombre_jugador = gameServices.getNombreJugador(id_login);

        } catch (GameServicesException ex) {
            Logger.getLogger(BomberManXAPIControllerUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (id_login >= 0) {
            status = HttpStatus.ACCEPTED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(Integer.toString(id_login)+"~~||~~"+nombre_jugador, status);
    }

    /**
     * crear usuario
     * @param cua
     * @return 
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> RegistrerJugador(@RequestBody CrearUsuarioAttempt cua) {
        HttpStatus status;
        int id_nuevo = gameServices.registrerJugador(cua.getNombre(), cua.getCorreo(), cua.getApodo(), cua.getClave(), cua.getImagen());

        if (id_nuevo >= 0) {
            status = HttpStatus.ACCEPTED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(id_nuevo, status);
    }

    /**
     * Devuelve la imagen url del personaje
     *
     * @param correo
     * @return ResponseEntity status
     */
    @RequestMapping(path = "/avatar/{correo}/view", method = RequestMethod.GET)
    public ResponseEntity<?> loadAvatar(@PathVariable String correo) {
        HttpStatus status;
        String url = gameServices.getUrl(correo);

        if (!"no_existe".equals(url)) {
            status = HttpStatus.ACCEPTED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(url, status);
    }

    /**
     * Cambia la imagen url del personaje
     *
     * @param correo
     * @return
     */
    @RequestMapping(path = "/avatar/{correo}/set", method = RequestMethod.GET)
    public ResponseEntity<?> setAvatar(@PathVariable String correo) {
        HttpStatus status;
        String url = gameServices.getUrl(correo);

        if (!"no_existe".equals(url)) {
            status = HttpStatus.ACCEPTED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(url, status);
    }
}
