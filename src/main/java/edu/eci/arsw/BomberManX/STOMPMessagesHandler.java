/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX;

import edu.eci.arsw.BomberManX.model.game.Juego;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaJugador;
import edu.eci.arsw.BomberManX.Persistencia.PersistenciaSala;
import edu.eci.arsw.BomberManX.model.game.entities.Elemento;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.Man;
import edu.eci.arsw.BomberManX.model.game.entities.Sala;
import edu.eci.arsw.BomberManX.model.game.entities.TableroTexto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import java.util.ArrayList;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    private PersistenciaJugador PJ;
    @Autowired
    private PersistenciaSala PS;

    @Autowired
    SimpMessagingTemplate msgt;

    /**
     * Permite agregar a lista de jugadores que quieren jugar
     *
     * @param id_jugador
     * @param idSala
     * @return 
     * @throws Exception
     */
    @MessageMapping("/EntrarAJuego.{idSala}")
    public boolean handleEntrarAJuego(int id_jugador, @DestinationVariable int idSala) throws Exception {
        //si la sala está casi lista ya no pueden entrar más jugadores
        if (PS.estaCasiLista(idSala)) {
            enviarListadoJugadoresQuierenJugar(idSala, false);
            return false;
        }
        Jugador j = PJ.SeleccionarJugadorPorId(id_jugador);
        PS.addJugador(idSala,j);
        enviarListadoJugadoresQuierenJugar(idSala, true);
        return true;
    }

    /**
     * Permite indicar que un jugador ya está listo
     *
     * @param id_jugador
     * @param idSala
     * @throws Exception
     */
    @MessageMapping("/JugadorListo.{idSala}")
    public void handleJugadorListo(int id_jugador, @DestinationVariable int idSala) throws Exception {
        Jugador jugadorListo = PJ.SeleccionarJugadorPorId(id_jugador);

        PS.addJugadorListo(idSala,jugadorListo);
        enviarListadoJugadoresQuierenJugar(idSala, true);
    }

    /**
     * respondemos con TODOS los jugadores incluso el nuevo recibidos y los que
     * están listos
     *
     * @param idSala
     * @param salaAbierta
     * @return 
     */
    public boolean enviarListadoJugadoresQuierenJugar(int idSala, boolean salaAbierta) {
        //url de msgt.convertAndSend();
        String url = "/topic/JugadoresQuierenJugar." + idSala;
        if (!salaAbierta) {
            msgt.convertAndSend(url, false);
            return false;
        }
        ArrayList<String> strJugadores = new ArrayList<>();
        synchronized (PS.getJugadoresDeSala(idSala)) {
            int k = 0;
            while (k < PS.getJugadoresDeSala(idSala).size()) {
                Jugador jugador = PS.getJugadoresDeSala(idSala).get(k);
                //revisamos si ya está listo
                int i = 0;
                boolean listo = false;
                ArrayList<Jugador> jugadoresListos = PS.getJugadoresListos(idSala);
                synchronized (jugadoresListos) {
                    while (!listo && i < jugadoresListos.size()) {
                        if (jugadoresListos.get(i).equals(jugador)) {
                            listo = true;
                        }
                        i++;
                    }
                }

                String json_ = "{\"nombre\":\"" + jugador.getNombre() + "\",\"record\":" + jugador.getRecord() + ",\"listo\":" + listo + "}";
                strJugadores.add(json_);
                k++;
            }
        }
        //enviamos todos los jugadores
        msgt.convertAndSend(url, strJugadores.toString());
        if(PS.cerrarSala(idSala))
            msgt.convertAndSend("/topic/ListoMinimoJugadores." + idSala, Sala.TIEMPOENSALAPARAEMPEZAR);
        return true;
    }
    
    @MessageMapping("/AccionBomba.{idSala}")
    public boolean accionBomba(int id_jugador, @DestinationVariable int idSala) throws Exception {
        // para probar sala 100
        PJ.SeleccionarJugadorPorId(id_jugador);
        
        ArrayList<Jugador>  jugadorez = new ArrayList<>();
        jugadorez.add(new Jugador("Prueba", "pr@server.com", "jugador prueba", "123", ""));
        
        Juego juego = new Juego(jugadorez);
        
        return juego.accionBomba(jugadorez.get(0));
    }
    
    // Author: Kevin S. Sanchez (DOCUEMENTACION)
    @MessageMapping("/moverPersonaje.{idSala}")
    public void moverPersonaje(int id_jugador, Man player, @DestinationVariable int idSala) throws Exception {
        String[][] tablero = TableroTexto.muestraContenido(1);
        synchronized (msgt) {
            // Variables (pendiente definir si el man tendra posiciones en X y Y
            int posRow = 0;
            int posCol = 1;
            int key = 40;
            // Pendientes estas variables
            ArrayList<Elemento> cambios = new ArrayList();
            // Los jugadores solo tienen identificador numerico
            if (isNumeric(tablero[posRow][posCol])) {
                // Flecha Abajo
                if (key == 40) {
                    if (tablero[posRow + 1][posCol].equals("O")) {
                        // Definir si colocar el IdJugador
                        tablero[posRow + 1][posCol] = tablero[posRow][posCol];
                        tablero[posRow][posCol] = "O";
                        //Elemento e = new Elemento(posRow + 1, posCol, Integer.toString(id_jugador));
                        //Elemento e2 = new Elemento(posRow, posCol, "O");
                        //actualizaciones.add(e);
                        //actualizaciones.add(e2);
                        //msgt.convertAndSend("/topic/actualizarTablero", cambios);
                    }
                // Flecha Izquierda
                } else if (key == 37) {
                    if (!(tablero[posRow][posCol - 1]).equals("O")) {
                        tablero[posRow][posCol - 1] = tablero[posRow][posCol];
                        tablero[posRow][posCol] = "O";
                        //Elemento e = new Elemento(posRow, posCol - 1, Integer.toString(id_jugador));
                        //Elemento e2 = new Elemento(posRow, posCol, "O");
                        //actualizaciones.add(e);
                        //actualizaciones.add(e2);
                        //msgt.convertAndSend("/topic/actualizarTablero", cambios);
                    }
                // Flecha Arriba
                } else if (key == 38) {
                    if (!(tablero[posRow - 1][posCol]).equals("O")) {
                        tablero[posRow - 1][posCol] = tablero[posRow][posCol];
                        tablero[posRow][posCol] = "O";
                        //Elemento e = new Elemento(posRow - 1, posCol, Integer.toString(id_jugador));
                        //Elemento e2 = new Elemento(posRow, posCol, "O");
                        //actualizaciones.add(e);
                        //actualizaciones.add(e2);
                        //msgt.convertAndSend("/topic/actualizarTablero", cambios);
                    }
                // Flecha Derecha 
                } else if (key == 39) {
                    if (!(tablero[posRow][posCol + 1]).equals("O")) {
                        tablero[posRow][posCol + 1] = tablero[posRow][posCol];
                        tablero[posRow][posCol] = "O";
                        //Elemento e = new Elemento(posRow, posCol + 1, Integer.toString(id_jugador));
                        //Elemento e2 = new Elemento(posRow, posCol, "O");
                        //actualizaciones.add(e);
                        //actualizaciones.add(e2);
                        //msgt.convertAndSend("/topic/actualizarTablero", cambios);
                    }
                }
            }
        }
    }
    
    public static boolean isNumeric(String str){  
      try{  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe){  
        return false;  
      }  
      return true;  
    }
}
