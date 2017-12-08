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

/**
 *
 * @author Kevin S. Sanchez
 */
public class TableroTexto {
    
    public static String[][] muestraContenido(int idEscenario) throws FileNotFoundException, IOException {
      String[][] tab = new String[10][20];
      int iRow = 0;
      String cadena;
      FileReader f = new FileReader("../resources/static/media/escenarios/Escenario0.txt");
      BufferedReader b = new BufferedReader(f);
      while((cadena = b.readLine())!=null) {
            System.out.println("Esta es la linea de texto = " + cadena);
            String[] row = cadena.split("\t");

            for(int col = 0; col < row.length; col++){
                tab[iRow][col] = row[col];
            }
            iRow++;
      }
      b.close();
      
      return tab;
}

//    public static String[][] muestraContenido(int idEscenario) {
//        String[][] tab = new String[10][20];
//        FileReader f= null;
//        try {
////             Convenciones para hacer escenarios:
////             * {1,2,3,4,5,6.....} =  Numeros para representar jugadores.
////             * 'O' = Espacio vacio.
////             * 'B' = Bomba.
////             * 'C' = Caja que se puede destruir.
////             * 'c' = Caja que se puede destruir Dañada (efecto visual de 0.2 seg).
////             * 'X' = Bloque (No se puede destruir).
////             * 'R' = Poder de Correr.
////             * 'T' = Poder de expansion de explosion de Bomba.
////             * 'M' = Añadir cantidad de bombas que se pueden colocar al mismo tiempo
////             * {'@', '-', '/'} = Caracteres especiales para enemigos.
////             * /t = (Tabulador) para separar columnas dentro del archivo.
////             * ',' = separador de objetos en una sola casilla.
////             Por el momento se supone para el caso que solo tenga un objeto en una sola celda
//            
//            String cadena;
//            f = new FileReader("Escenario" + idEscenario + ".txt");
//            BufferedReader b = new BufferedReader(f);
//            
//            while((cadena = b.readLine()) != null) {
//                System.out.println("Esta es la linea de texto = " + cadena);
//                String[] row = cadena.split("\t");
//                
//                for (int col = 0; col < row.length; col++){
//                    tab[iRow][col] = row[col];
//                }
//                iRow++;
//            }
//            b.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger("No se encontro archivo de texto para cargar escenario " + idEscenario);
//        } catch (IOException ex) {
//            Logger.getLogger(TableroTexto.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                f.close();
//            } catch (IOException ex) {
//                Logger.getLogger(TableroTexto.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        String[][] tab = {{"1","O","O","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
//                        {"O","O","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
//                        {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
//                        {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
//                        {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
//                        {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
//                        {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
//                        {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X"},
//                        {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","C"},
//                        {"X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","X","C"}};
//        
//        return tab;
//    }
    
}
