package edu.eci.arsw.bombermanx.model.game.attempt;

/**
 *
 * @author kevin
 */
public class CrearSalaAttempt {
    private int id_jugador;
    private String nombre;
    private boolean equipos;
    private boolean fuegoamigo;

    public CrearSalaAttempt() {
    }
    
    public CrearSalaAttempt(int id_jugador, String nombre, boolean equipos, boolean fuegoamigo) {
        this.id_jugador = id_jugador;
        this.nombre = nombre;
        this.equipos = equipos;
        this.fuegoamigo = fuegoamigo;
    }

    public int getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEquipos() {
        return equipos;
    }

    public void setEquipos(boolean equipos) {
        this.equipos = equipos;
    }

    public boolean isFuegoamigo() {
        return fuegoamigo;
    }

    public void setFuegoamigo(boolean fuegoamigo) {
        this.fuegoamigo = fuegoamigo;
    }
}
