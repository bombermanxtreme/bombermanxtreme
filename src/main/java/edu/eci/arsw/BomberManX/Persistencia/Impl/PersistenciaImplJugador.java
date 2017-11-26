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
        AgregarJugador("Kevin Alvarado", "ka@server.com", "kevincito", "123", "https://vignette.wikia.nocookie.net/looneytunes/images/a/ad/Baby_Lola.png/revision/latest?cb=20110509194052");
        AgregarJugador("Sergio Pérez", "sp@server.com", "finanzas", "123", "https://i.pinimg.com/736x/e4/a7/5a/e4a75a1b9ef50e99224830bd2dad8e65--cute-baby-elephant-baby-elephants.jpg");
        AgregarJugador("Kevin Sánchez", "ks@server.com", "quevin", "123", "https://i.pinimg.com/736x/cb/eb/46/cbeb46a7bcde12bea4ff0e7f06b70a03--cartoon-foxes-cartoon-fox-drawing.jpg");
        AgregarJugador("Lina Álvarez", "la@server.com", "anail", "123", "https://upload.wikimedia.org/wikipedia/commons/d/d3/Bull_cartoon_04.svg");
        AgregarJugador("Fanny Pérez", "fp@server.com", "ynnaf", "123", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYGIRwH_hoWi2hLZ10SR_WkbQ4s35SiRaaVKd-YPXtibOj0-0i-g");
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
        
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCorreo().equals(correo)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String getUrlPorCorreo(String correo) {     
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCorreo().equals(correo)) {
                  
                return jugadores.get(i).getImagen();
            }
        }
        
        return "no_existe";
    }
}
