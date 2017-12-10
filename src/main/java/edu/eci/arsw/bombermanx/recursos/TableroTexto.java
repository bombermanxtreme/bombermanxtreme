package edu.eci.arsw.bombermanx.recursos;

import java.io.FileNotFoundException;
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
        //             * 'b' = Bomba fuego.
        //             * 'C' = Caja que se puede destruir.
        //             * 'c' = Caja que se puede destruir Dañada (efecto visual de 0.2 seg).
        //             * 'X' = Bloque (No se puede destruir).
        //             * 'R' = Poder de Correr.
        //             * 'r' = Poder de Correr menos.
        //             * 'T' = Poder de expansion de explosion de Bomba.
        //             * 't' = Poder de reduccion de explosion de Bomba.
        //             * 'N' = mas numero Bomba.
        //             * 'n' = menos numero Bomba.
        //             * 'S' = Poder super, mejora todo
        //             * 'M' = Añadir cantidad de bombas que se pueden colocar al mismo tiempo
        //             * {'@', '-', '/'} = Caracteres especiales para enemigos.
        //             * /t = (Tabulador) para separar columnas dentro del archivo.
        //             * ',' = separador de objetos en una sola casilla.
        //             Por el momento se supone para el caso que solo tenga un objeto en una sola celda
        ArrayList<String[][]> escenarios = new ArrayList<>();
        String[][] tab = {{"0","O","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","O","O"},
                          {"O","O","O","C","C","C","C","C","C","C","C","C","C","C","C","C","C","O","O","O"},
                          {"X","O","X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","O","X"},
                          {"X","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","X"},
                          {"X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","C","X"},
                          {"X","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","X"},
                          {"X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","C","X"},
                          {"X","O","C","C","C","C","C","C","C","C","C","1","C","C","C","C","C","C","O","X"},
                          {"X","X","O","C","C","C","C","C","C","C","C","C","C","C","C","C","C","O","X","O"},
                          {"O","O","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","O","O"}};
        escenarios.add(tab);
        
        String[][] tabNewVersion = {{"0","O","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","O","O"},
                          {"O","O","O","C","C","C","C","C","C","C","C","C","C","C","C","C","C","O","O","O"},
                          {"X","O","X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","O","X"},
                          {"X","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","X"},
                          {"X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","C","X"},
                          {"X","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","C","X"},
                          {"X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","X","C","C","X"},
                          {"X","O","C","C","C","C","C","C","C","C","C","O","C","C","C","C","C","C","O","X"},
                          {"X","X","O","C","C","C","C","C","C","C","C","1","C","C","C","C","C","O","X","O"},
                          {"O","O","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","O","O"}};
        escenarios.add(tabNewVersion);
      
      return escenarios.get(0);
    }
    
}
