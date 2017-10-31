package edu.eci.arsw.BomberManX.services;

import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaSala;
import edu.eci.arsw.BomberManX.cache.BomberManXCache;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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

    //cache con los datos volatiles del juego
    @Autowired
    BomberManXCache cache;
    @Autowired
    PersistenciaJugador pj = null;
    @Autowired
    PersistenciaSala ps = null;

    public void setBpp(PersistenciaJugador bpp, PersistenciaSala ps) {
        this.pj = pj;
        this.ps = ps;
    }

    public Set<Jugador> getJugadoresDeSala(int id_sala) {
        Set<Jugador> r = new HashSet<>();
        ArrayList<Jugador> jugadores = ps.getJugadoresDeSala(id_sala);
        for (int i = 0; i < jugadores.size(); i++) {
            r.add(jugadores.get(i));
        }
        return r;
    }

    public int loginJugador(String correo, String clave) {
        int id_login=-1; //-1: no se encontro el correo, -2: clave incorrecta
        
        ArrayList<Jugador> jugadores = pj.getJugadores();
        
        //jugadores diponibles
        for (int i=0; i < jugadores.size(); i++){ System.out.println(jugadores.get(i)); }
        
        for (int i=0; i < jugadores.size(); i++)
            if (jugadores.get(i).getCorreo().equals(correo)) {                
                
                if(jugadores.get(i).getClave().equals(clave))id_login = pj.getIDPorCorreo(correo);      
                else id_login=-2;
            }
        return id_login;
    }

    public int registrerJugador(String nombre, String apodo, String correo, String clave, String nclave, String imagen) {
        int id_registro = -1;// -1: algo fallo, -2: ya existe el correo
        
        // No importa si no tiene imagen (avatar)
        boolean correo_valido = true;
        
        ArrayList<Jugador> jugadores = pj.getJugadores();

        //el correo debe ser unico
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCorreo().equals(correo) && correo.contains("@") && correo.length() > 5) {
                correo_valido = false;
                id_registro = -2;
            }
        }

        if (correo_valido) {
            if (nombre.length() > 2 && apodo.length() > 2 && clave.equals(nclave) && clave.length() > 2) {
                jugadores.add(new Jugador(nombre, correo, clave));
                id_registro = pj.getIDPorCorreo(correo);
            }
        }        
        
        return id_registro;
    }
}