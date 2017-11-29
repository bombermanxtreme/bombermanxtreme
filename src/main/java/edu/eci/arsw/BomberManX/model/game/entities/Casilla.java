/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.model.game.entities;

/**
 *
 * @author Kvn CF <ECI>
 */
public class Casilla {

    private boolean bomba; //hay bomba encima
    private boolean npc; // hay un npc encima
    private boolean man; // hay un man encima

    public Casilla() {
        bomba = false;
        npc = false;
        man = false;
    }

    /**
     * revisa si hay objetos
     * 
     * @return  hay o no objetos en esa casilla
     */
    public boolean hay_objeto() {
        if (bomba || npc || man) {
            return false;
        } else {
            return true;
        }

    }

    public void setBomba(boolean valor) {
        bomba = valor;
    }

    public void setNpc(boolean valor) {
        npc = valor;
    }

    public void setMan(boolean valor) {
        man = valor;
    }

}
