/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Persistencia.Impl;

import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.model.Jugador;
import java.util.ArrayList;

/**
 *
 * @author Kvn CF <ECI>
 */
public class PersistenciaImplJugador implements PersistenciaJugador{

    private ArrayList<Jugador> jugadores=new ArrayList<Jugador>();

    public PersistenciaImplJugador() {
        AgregarJugador("Kevin Alvarado", "ka@a.com", "123");
        AgregarJugador("Sergio Pérez", "sp@a.com", "456");
        AgregarJugador("Kevin Sánchez", "ks@a.com", "789");
    }
    
    @Override
    public void AgregarJugador(String nombre,String correo,String clave) {
        jugadores.add(new Jugador(nombre, correo, clave));
    }

    @Override
    public Jugador SeleccionarJugadorPorId(int idJugador) {
        return jugadores.get(idJugador);
    }
}
