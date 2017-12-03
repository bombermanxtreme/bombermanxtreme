/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.Attempt;

/**
 *
 * @author kevin
 */
public class CrearSalaAttempt {
    String nombre;
    boolean equipos;
    boolean fuegoamigo;

    public CrearSalaAttempt(String nombre, boolean equipos, boolean fuegoamigo) {
        this.nombre = nombre;
        this.equipos = equipos;
        this.fuegoamigo = fuegoamigo;
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
