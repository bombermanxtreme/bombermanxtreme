package edu.eci.arsw.BomberManX.services;

import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kvn CF <ECI>
 */
@Service
public class BomberManXServices {
   
    @Autowired
    PersistenciaJugador bpp=null;
    
    public void setBpp(PersistenciaJugador bpp) {
        this.bpp = bpp;
    }
    
}
