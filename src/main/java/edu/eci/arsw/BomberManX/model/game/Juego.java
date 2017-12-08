/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game;

import edu.eci.arsw.BomberManX.model.game.entities.Caja;
import edu.eci.arsw.BomberManX.model.game.entities.Caja_Metalica;
import edu.eci.arsw.BomberManX.model.game.entities.Espacio;
import edu.eci.arsw.BomberManX.model.game.entities.Bomba;
import edu.eci.arsw.BomberManX.model.game.entities.Jugador;
import edu.eci.arsw.BomberManX.model.game.entities.Elemento;
import edu.eci.arsw.BomberManX.model.game.entities.Man;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Juego {

    public static final int ARRIBA = 0;
    public static final int ABAJO = 1;
    public static final int DERECHA = 2;
    public static final int IZQUIERDA = 3;
    public static final int ANCHO = 20;
    public static final int ALTO = 10;
    private static final int TIEMPOEXPLOTARBOMBAS=5;
    private ArrayList<Jugador> jugadores;
    private Elemento[][] tablero;
    private ArrayList<Man> manes = new ArrayList<>();
	public static final int MAXIMOJUGADORES = 4;
	private static final int[][] POSJUGADORES={{0,0},{ALTO-1,ANCHO-1},{0,ANCHO-1},{ALTO-1,0}};
	private static final int[][] POSPROTEGIDAS={{0,0},{0,1},{1,0},{ALTO-1,ANCHO-1},{ALTO-2,ANCHO-1},{ALTO-1,ANCHO-2},{0,ANCHO-1},{1,ANCHO-1},{0,ANCHO-2},{ALTO-1,0},{ALTO-2,0},{ALTO-1,1}};
    
    public Juego(ArrayList<Jugador> jugadores, String[][] tableroTemporal) {
        this.jugadores = jugadores;
        this.tablero = new Elemento[ALTO][ANCHO];
        
        int x=0;
        int y=0;
		// creando Manes y agregándolos al tablero
        for(int i=0;i<jugadores.size();i++){
			x=POSJUGADORES[i][0];
			y=POSJUGADORES[i][1];
            Man manTMP=new Man("black", jugadores.get(i), "", x, y);
            tablero[x][y]=manTMP;
            manes.add(manTMP);
        }
        //String letter;
        //creando cajas Random
        Random rand = new Random();
        for (int row = 0; row < ALTO; row++){
            for (int col = 0; col < ANCHO; col++) {
                int[] tmp={row,col};
                if(Arrays.asList(POSPROTEGIDAS).contains(tmp))
                    continue;
                if(rand.nextInt(2) == 0) {
                    tablero[row][col] = new Caja("", row, col);
                }
            }
        }
        // Mapear Tablero
        //mapearTablero(tableroTemporal);
    }
    
    /**
     * Mapear tablero de String a Objetos
     * Author: Kevin S Sanchez
     * @param temp Matriz de Strings
     */
    private void mapearTablero(String[][] temp){
        //Recorrer Filas
        String letter;
        for (int row = 0; row < temp.length; row++){
            //Recorrer Columnas
            for (int col = 0; col < temp[row].length; col++){
                letter = temp[row][col];
                System.out.println("///////////////////////// Letra: " + letter);
                // Convenciones para hacer escenarios:
                // * {1,2,3,4,5,6.....} =  Numeros para representar jugadores.
                // * 'O' = Espacio vacio.
                // * 'B' = Bomba.
                // * 'C' = Caja que se puede destruir.
                // * 'X' = Bloque (No se puede destruir).
                // * 'R' = Poder de Correr.
                // * 'T' = Poder de expansion de explosion de Bomba.
                // * 'M' = Añadir cantidad de bombas que se pueden colocar al mismo tiempo
                // * {'@', '-', '/'} = Caracteres especiales para enemigos.
                if(isNumeric(letter)){
                    this.tablero[row][col] = new Man("red", jugadores.get(Integer.parseInt(letter)-1), letter, row, col);
                    System.out.println("---- POSX: " + this.tablero[row][col].getPosRow() + " + + + POSY: " + this.tablero[row][col].getPosCol());
                }else{
                    switch (letter) {
                        case "O":
                            this.tablero[row][col] = new Espacio(letter, row, col); 
                            System.out.println("---- POSX: " + this.tablero[row][col].getPosRow() + " + + + POSY: " + this.tablero[row][col].getPosCol());
                            break;

                        case "C":
                            this.tablero[row][col] = new Caja(letter, row, col);
                            System.out.println("---- POSX: " + this.tablero[row][col].getPosRow() + " + + + POSY: " + this.tablero[row][col].getPosCol());
                            break;
                            
                        case "X":
                            this.tablero[row][col] = new Caja_Metalica(letter, row, col);
                            System.out.println("---- POSX: " + this.tablero[row][col].getPosRow() + " + + + POSY: " + this.tablero[row][col].getPosCol());
                            break;
                        
                        default:
                            this.tablero[row][col] = new Espacio(letter, row, col); 
                            System.out.println("---- POSX: " + this.tablero[row][col].getPosRow() + " + + + POSY: " + this.tablero[row][col].getPosCol());
                            break;
                    }
                }
            }
        }
    }
    
    /**
     * Verificar si String es Numerico
     * @param str Cadena de texto a verificar
     * @return True: Es numerico, False: NO es numerico
     */
    private boolean isNumeric(String str){  
      try{  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe){  
        return false;  
      }  
      return true;  
    }

    /**
     * Ejecuta la accion de la bomba de ser posible, la coloca y luego temporiza 5s para explotar
     * @param jugador
     * @return 
     */
    public boolean accionBomba(Jugador jugador){
        Man man = manes.get(jugadores.indexOf(jugador));
        int coor_x = man.getPosCol();
        int coor_y = man.getPosRow();
        boolean puede = hay_objeto(coor_x,coor_y, man);
        
        if(puede){      
            System.out.println("Pudo poner bomba >>");
            tablero[coor_x][coor_y]= (Elemento) man.accionBomba();
            //Timeout timeout = new Timeout();
            //timeout.start(5000);
        }      
        
        /*
        ActionListener b = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //lanza ladrillo
				mover();
				timer.stop();
            }
        };
        timer = new Timer(100, b);
        timer.start();
        */
        
        return puede;
    }
    
    public boolean mover(int id_jugador){
        return false;
    }
        
    /**
     * Revisa que fila y columna del tablero no este ocuapda, expectuando por el Man
     * @param fila
     * @param columna
     * @return 
     */
    private boolean hay_objeto(int fila, int columna, Man man){        
        Bomba bomba = (Bomba) tablero[fila][columna];
        return bomba.get_man().equals(man);         // provisional solo mirando Man mientras se implementa para revisar si hay otra cosa
    }
    
        
    public boolean mover(){
        return false;
    }
    
    @Override
    public String toString() {
        ArrayList<String> cajas=new ArrayList<>();
        ArrayList<String> manes=new ArrayList<>();
        for (int i = 0; i < tablero.length; i++) {
            for (int k = 0; k < tablero[0].length; k++) {
                if(tablero[i][k] instanceof Caja){
                    cajas.add("{x:"+k+",y:"+i+"}");
                }
                if(tablero[i][k] instanceof Man){
                    manes.add(tablero[i][k].toString());
                }
            }
        }
        return "{\"cajas\":" + cajas.toString() + ",\"manes\":" + manes.toString() + ",\"ancho\":"+ANCHO+",\"alto\":"+ALTO+"}";
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Elemento[][] getTablero() {
        return tablero;
    }

    public void setTablero(Elemento[][] tablero) {
        this.tablero = tablero;
    }
}
