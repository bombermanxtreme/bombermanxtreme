/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.controllers;

import edu.eci.arsw.BomberManX.services.BomberManXServices;
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

    @RequestMapping(path = "/{correo}/{clave}", method = RequestMethod.GET)
    public ResponseEntity<?> loginJugador(@PathVariable String correo, @PathVariable String clave) {
        HttpStatus status;
        int id_login = gameServices.loginJugador(correo, clave);

        if (id_login != -1) {
            status = HttpStatus.ACCEPTED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        System.out.println("retorno");
        System.out.println(correo);
        System.out.println(clave);
        System.out.println(id_login);
        System.out.println(status);
        return new ResponseEntity<>(id_login, status);

    }

    @RequestMapping(path = "/new/{nombre}/{apodo}/{correo}/{clave}/{nclave}/{imagen}", method = RequestMethod.GET)
    public ResponseEntity<?> RegistrerJugador(@PathVariable String nombre, @PathVariable String apodo, @PathVariable String correo, @PathVariable String clave, @PathVariable String nclave, @PathVariable String imagen) {
        HttpStatus status;
        int id_nuevo = gameServices.registrerJugador(nombre, apodo, correo, clave, nclave, imagen);

        if (id_nuevo >= 0) {
            status = HttpStatus.ACCEPTED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(id_nuevo, status);

    }

}
