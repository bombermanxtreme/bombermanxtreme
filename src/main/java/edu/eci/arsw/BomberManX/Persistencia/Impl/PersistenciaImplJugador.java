/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Persistencia.Impl;

import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kvn CF <ECI>
 */
@Service
public class PersistenciaImplJugador implements PersistenciaJugador{

    private ArrayList<Jugador> jugadores=new ArrayList<>();

    public PersistenciaImplJugador() {
        AgregarJugador("Kevin Alvarado", "ka@server.com", "123");
        AgregarJugador("Sergio Pérez", "sp@server.com", "456");
        AgregarJugador("Kevin Sánchez", "ks@server.com", "789");
        AgregarJugador("Lina Álvarez", "la@server.com", "321");
        AgregarJugador("Fanny Pérez", "fp@server.com", "654");
    }
    
    @Override
    public void AgregarJugador(String nombre,String correo,String clave) {
        jugadores.add(new Jugador(nombre, correo, clave));
    }

    @Override
    public Jugador SeleccionarJugadorPorId(int idJugador) {
        return jugadores.get(idJugador);
    }

    @Override
    public int loginJugador(String correo, String clave) {
        int correcto = -1;
        
        for(int i=0;i<jugadores.size();i++){
            if(jugadores.get(i).getCorreo().equals(correo) && jugadores.get(i).getClave().equals(clave)){
                correcto = i;
                System.out.println(correo);
                System.out.println(clave);
            }
        }
        
        return correcto;
    }
}
