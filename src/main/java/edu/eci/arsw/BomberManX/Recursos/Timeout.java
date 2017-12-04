/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Recursos;


/**
 * Adaptacion de forosdelweb.com/f45/como-hacer-temporizador-java-1133653/
 * @author sergioxl
 */
import java.util.Timer;
import java.util.TimerTask;

public class Timeout {

    private int hour;
    private int minute;
    private int second;
    private Timer timer;
    private boolean isTimerRunning;
     private Timer referencia;
       
    /**
     * Clase para contabilizar el tiempo
     * @param second 
     */
    public Timeout(Timer referencia, int second) {
        timer = new Timer();
        
        this.referencia=referencia;
        this.second = second;
        hour = 0;
        minute = 0; 
        
        
        start();
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            isTimerRunning = true;
            if (second > 0) {
                second--;
            } else {
                second = 59;
                if (minute > 0) {
                    minute--;
                } else {
                    minute = 59;
                    if (hour > 0) {
                        hour--;
                    } // si segundo = 0, minuto = 0 y hora = 0,
                    // cancelamos el timer
                    else {
                        isTimerRunning = false;
                        timer.cancel();
                        timer.purge();
                    }
                }
            }
            if (isTimerRunning) {
                //display.printTime(hour, minute, second);
                System.out.println("---- BOMBA ----- Contador de tiempo: " + Integer.toString(second));
            }
            
            // hacer algo
        }
    }; // fin timertask

    /**
     *  {0}: task: para ejecutarlo
     *  {1}: delay, 0 para no demorar
     *  {2}: tiempo entre cada segundo, 1000ms para no demorar
     */
    public void start() {
    //public void start(int timeout, int interval) {
        timer.schedule(task, 0, 1000);
    }

} // fin clase
