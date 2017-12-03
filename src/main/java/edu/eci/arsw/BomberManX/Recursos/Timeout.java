/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.BomberManX.Recursos;

/**
 *
 * @author forosdelweb.com/f45/como-hacer-temporizador-java-1133653/
 */
import java.util.Timer;
import java.util.TimerTask;

public class Timeout {

    private int hour;
    private int minute;
    private int second;
    private Timer timer;
    private boolean isTimerRunning;
    //private Display display;

    public Timeout() {
        timer = new Timer();
        second = 11;
        hour = 0;
        minute = 0;
        //display = new Display();
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
        }
    }; // fin timertask

    public void start(int interval) {
    //public void start(int timeout, int interval) {
        timer.schedule(task, 0, interval);
    }

} // fin clase
