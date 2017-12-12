package edu.eci.arsw.bombermanx.persistencia.impl;

import edu.eci.arsw.bombermanx.persistencia.PersistenciaJugador;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kvn CF <ECI>
 */
public class PersistenciaImplJugador {//implements PersistenciaJugador {

    private ArrayList<Jugador> jugadores;

    public PersistenciaImplJugador() {
        jugadores = new ArrayList<>();
        poblar();
    }

    //@Override
    public final void poblar() {
        //necesario para evitar error: overridable method call in constructor//
        agregarJugador("Kevin Alvarado", "ka@server.com", "Kevincito", "123", "https://avatarfiles.alphacoders.com/812/81220.jpg");
        agregarJugador("Sergio Pérez", "sp@server.com", "Finanzas", "123", "https://i.pinimg.com/736x/e4/a7/5a/e4a75a1b9ef50e99224830bd2dad8e65--cute-baby-elephant-baby-elephants.jpg");
        agregarJugador("Kevin Sánchez", "ks@server.com", "Quevin", "123", "https://i.pinimg.com/736x/cb/eb/46/cbeb46a7bcde12bea4ff0e7f06b70a03--cartoon-foxes-cartoon-fox-drawing.jpg");
        agregarJugador("Lina Álvarez", "la@server.com", "Anail", "123", "https://i.pinimg.com/originals/8c/06/10/8c061085178087b6a063bd6a72b69604.gif");
        agregarJugador("Fanny Pérez", "fp@server.com", "Ynnaf", "123", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRYGIRwH_hoWi2hLZ10SR_WkbQ4s35SiRaaVKd-YPXtibOj0-0i-g");
    }

    //@Override
    public void agregarJugador(String nombre, String correo, String apodo, String clave, String imagen) {
        jugadores.add(new Jugador(jugadores.size(),nombre, correo, apodo, clave, imagen));
    }

    //@Override
    public Jugador seleccionarJugadorPorId(int idJugador) {
        return jugadores.get(idJugador);
    }

    //@Override
    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    //@Override
    public int getIDPorCorreo(String correo) {

        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCorreo().equals(correo)) {
                return i;
            }
        }

        return -1;
    }

    //@Override
    public String getUrlPorCorreo(String correo) {
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getCorreo().equals(correo)) {

                return jugadores.get(i).getImagen();
            }
        }

        return "no_existe";
    }
}
