/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.controllers;

import edu.eci.arsw.BomberManX.model.game.Attempt.CrearUsuarioAttempt;
import edu.eci.arsw.BomberManX.services.BomberManXServices;
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
     *  Permite validar datos de sesion
     * 
     * @param correo
     * @param clave
     * @return 
     */
    @RequestMapping(path = "/{correo}/{clave}", method = RequestMethod.GET)
    public ResponseEntity<?> loginJugador(@PathVariable String correo, @PathVariable String clave) {
        HttpStatus status;
        int id_login = gameServices.loginJugador(correo, clave);

        if (id_login >= 0) {
            status = HttpStatus.ACCEPTED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        
        return new ResponseEntity<>(id_login, status);
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