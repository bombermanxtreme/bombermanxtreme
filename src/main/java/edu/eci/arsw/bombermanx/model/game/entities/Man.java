package edu.eci.arsw.bombermanx.model.game.entities;

import edu.eci.arsw.bombermanx.model.game.Juego;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Man implements Elemento,Destruible {
    private Jugador jugador;
    private int vida;
    private int velocidad;
    private int posCol; // posicion en x
    private int posRow; // posicion en y
    private Integer bombas; //numero de bombas
    private String color;
    private String key;
    private int radio;
    private int indice;
    private Timer timer;
    private boolean bloqueado;
    private boolean equipoB;

    public Man(String color, Jugador jugador, String key, int posRow, int posCol, boolean equipoB) {
        this.color = color;
        this.jugador = jugador;
        this.equipoB = equipoB;
        this.vida = 15;
        bombas = 1;
        this.key = key;
        this.posRow = posRow;
        this.posCol = posCol;
        radio = 1;
        indice = 0;
        bloqueado=false;
    }

    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Poner bomba, contar tiempo y explotar
     *
     * @return
     */
    public Bomba accionBomba() {
        Bomba bomba=null;
        if(estaVivo()){
            synchronized(bombas){
                if(bombas>0){
                    bombas--;
                    bomba = new Bomba_n(this, color, radio);
                    //System.out.println("puso Bomba >>");
                }else System.err.println("no tiene mas bombas");
            }
        }
        return bomba;
    }
    
    public String getKey() {
        return this.key;
    }

    public void setPosCol(int posCol) {
        this.posCol = posCol;
    }

    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }
    
    @Override
    public String toString(){
        return "{\"x\":" + 
                posCol + ",\"y\":" + 
                posRow + ",\"vida\":" + 
                vida + 
                ",\"color\":\""+ 
                color + 
                "\",\"apodo_jugador\":\"" + 
                jugador.getApodo()+ 
                "\",\"key\":\"" + 
                key + 
                "\",\"tiempo\":"+ 
                Juego.TIEMPOXDANIO+
                ",\"bombas\":"+ 
                bombas+
                ",\"equipoB\":"+ 
                equipoB+
                ",\"energia\":"+ 
                radio + 
                ",\"velocidad\":"+ 
                velocidad+
                ",\"img\":\""+
                jugador.getImagen()+
                "\"}";
    }

    public int getPosRow() {
        return posRow;
    }

    public void bloquear(int tiempo) {
        bloqueado=true;
        timer = new Timer(tiempo, (ActionEvent e) -> {
            timer.stop();
            if(estaVivo())bloqueado=false;
        });
        timer.start();
    }
    
    public boolean isBloqueado() {
        return bloqueado;
    }

    @Override
    public int getPosCol(){
        return posCol;
    }

    @Override
    public void explotaBomba() {
        if(estaVivo()){
            this.vida = this.vida - Juego.DANIO;
            if(estaVivo()){
                bloquear(Juego.TIEMPOXDANIO);
            }else bloqueado=true;
        }
    }
    
    public boolean estaVivo(){
        return (this.vida > 0);
    }

    public void agregarBomba() {
        synchronized(bombas){
            bombas++;
        }
    }

    public void setPoder(Poder p) {
        velocidad+=p.getVelocidad();
        radio+=p.getRadioBomba();
        bombas+=p.getNumBomba();
        if(velocidad<1)velocidad=1;
        if(radio<1)radio=1;
        if(bombas<1)bombas=1;
    }

    public int getVida() {
        return vida;
    }

    public Integer getBombas() {
        return bombas;
    }

    public int getRadio() {
        return radio;
    }

    
    public int getVelocidad() {
        return velocidad;
    }

    public boolean equipoB() {
        return equipoB;
    }
}
