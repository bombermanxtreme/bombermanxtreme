/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.bombermanx.model.game.entities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Kevin S. Sanchez
 */
public class TableroTexto {
    
    public static String[][] muestraContenido(int idEscenario) throws FileNotFoundException, IOException {
        //             Convenciones para hacer escenarios:
        //             * {1,2,3,4,5,6.....} =  Numeros para representar jugadores.
        //             * 'O' = Espacio vacio.
        //             * 'B' = Bomba.
        //             * 'C' = Caja que se puede destruir.
        //             * 'c' = Caja que se puede destruir Dañada (efecto visual de 0.2 seg).
        //             * 'X' = Bloque (No se puede destruir).
        //             * 'R' = Poder de Correr.
        //             * 'T' = Poder de expansion de explosion de Bomba.
        //             * 'M' = Añadir cantidad de bombas que se pueden colocar al mismo tiempo
        //             * {'@', '-', '/'} = Caracteres especiales para enemigos.
        //             * /t = (Tabulador) para separar columnas dentro del archivo.
        //             * ',' = separador de objetos en una sola casilla.
        //             Por el momento se supone para el caso que solo tenga un objeto en una sola celda
        ArrayList<String[][]> escenarios = new ArrayList<>();
        String[][] tab = {{"0","0","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","0","0"},
                          {"0","1","O","C","C","C","C","C","C","C","C","C","C","C","C","C","C","O","2","0"},
                          {"C","O","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","O","C"},
                          {"C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C"},
                          {"C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C"},
                          {"C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C"},
                          {"C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C"},
                          {"C","O","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","O","C"},
                          {"0","C","O","C","C","C","C","C","C","C","C","C","C","C","C","C","C","O","C","0"},
                          {"0","0","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","0","0"}};
        escenarios.add(tab);
      
      return escenarios.get(0);
    }
    
}
