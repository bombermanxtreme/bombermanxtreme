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
public class PersistenciaImplJugador implements PersistenciaJugador {

    private ArrayList<Jugador> jugadores = new ArrayList<>();

    
    public PersistenciaImplJugador() {        
         Poblar();
    }
    
    @Override
    public final void Poblar() {
        //necesario para evitar error: overridable method call in constructor//
        AgregarJugador("Kevin Alvarado", "ka@server.com", "kevincito", "123", "ninguna imagen");
        AgregarJugador("Sergio Pérez", "sp@server.com", "finanzas", "456", "ninguna imagen");
        AgregarJugador("Kevin Sánchez", "ks@server.com", "quevin", "789", "ninguna imagen");
        AgregarJugador("Lina Álvarez", "la@server.com", "anail", "321", "ninguna imagen");
        AgregarJugador("Fanny Pérez", "fp@server.com", "ynnaf", "654", "ninguna imagen");
    }
    

    @Override
    public void AgregarJugador(String nombre, String correo, String apodo, String clave, String imagen) {
        jugadores.add(new Jugador(nombre, correo, apodo, clave, imagen));
    }

    @Override
    public Jugador SeleccionarJugadorPorId(int idJugador) {
        return jugadores.get(idJugador);
    }

    @Override
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    @Override
    public int getIDPorCorreo(String correo) {
        int id_jugador = -1;

        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCorreo().equals(correo)) {
                id_jugador = i;
            }
        }

        return id_jugador;
    }
}
