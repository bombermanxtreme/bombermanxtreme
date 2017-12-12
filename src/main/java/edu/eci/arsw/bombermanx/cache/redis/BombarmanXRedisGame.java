/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.bombermanx.cache.redis; 

import edu.eci.arsw.bombermanx.model.game.Juego;
import static edu.eci.arsw.bombermanx.model.game.Juego.ABAJO;
import static edu.eci.arsw.bombermanx.model.game.Juego.ALTO;
import static edu.eci.arsw.bombermanx.model.game.Juego.ANCHO;
import static edu.eci.arsw.bombermanx.model.game.Juego.ARRIBA;
import static edu.eci.arsw.bombermanx.model.game.Juego.CENTRO;
import static edu.eci.arsw.bombermanx.model.game.Juego.DERECHA;
import static edu.eci.arsw.bombermanx.model.game.Juego.IZQUIERDA;
import edu.eci.arsw.bombermanx.model.game.entities.Bomba;
import edu.eci.arsw.bombermanx.model.game.entities.Caja;
import edu.eci.arsw.bombermanx.model.game.entities.Caja_Metalica;
import edu.eci.arsw.bombermanx.model.game.entities.Casilla;
import edu.eci.arsw.bombermanx.model.game.entities.DejaMover;
import edu.eci.arsw.bombermanx.model.game.entities.Destruible;
import edu.eci.arsw.bombermanx.model.game.entities.Elemento;
import edu.eci.arsw.bombermanx.model.game.entities.Espacio;
import edu.eci.arsw.bombermanx.model.game.entities.Jugador;
import edu.eci.arsw.bombermanx.model.game.entities.Man;
import edu.eci.arsw.bombermanx.model.game.entities.PAddBomba;
import edu.eci.arsw.bombermanx.model.game.entities.PLessBomba;
import edu.eci.arsw.bombermanx.model.game.entities.PRedbull;
import edu.eci.arsw.bombermanx.model.game.entities.PSuper;
import edu.eci.arsw.bombermanx.model.game.entities.PTinto;
import edu.eci.arsw.bombermanx.model.game.entities.PTortuga;
import edu.eci.arsw.bombermanx.model.game.entities.PTurbo;
import edu.eci.arsw.bombermanx.model.game.entities.Poder;
import edu.eci.arsw.bombermanx.persistencia.RedisException;
import edu.eci.arsw.bombermanx.recursos.MessengerTh;
import edu.eci.arsw.bombermanx.services.GameServicesException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 *
 * @author 
 */
public class BombarmanXRedisGame extends Juego{
    //@Autowired
    private StringRedisTemplate template;
    private int id_partida;

    public BombarmanXRedisGame(StringRedisTemplate template, int gameid) throws RedisException{
        super(new ArrayList<ArrayList<Jugador>>(), new String[1][1], false);
        this.template=template;
        this.id_partida= gameid;
        
        try{
            terminado=((boolean) template.opsForHash().get("game:"+Integer.toString(id_partida), "terminado"));
            esEquipos=((boolean) template.opsForHash().get("game:"+Integer.toString(id_partida), "terminado"));
        }catch(Exception e){
            throw new RedisException("id del juego no existe "+e.getMessage());
        }
    }

    public BombarmanXRedisGame(StringRedisTemplate template, int id, ArrayList<ArrayList<Jugador>> jugadores, String[][] tablero, boolean esEquipos) {
        super(new ArrayList<ArrayList<Jugador>>(), new String[1][1], false);
    }
    
    @Override
    public Bomba accionBomba(Jugador jugador) throws GameServicesException {
        
        int mposCol=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":man:"+jugador.getApodo(), "x"));
        int mposRow=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":man:"+jugador.getApodo(), "y"));

        //System.out.println("pos interno: "+mposCol+" "+mposRow);
        Bomba explotara = null;
        Man man=new Man("", jugador, "", mposRow, mposCol, esEquipos);
        boolean puede = hay_objeto(mposRow, mposCol, man);

        if (puede) {
            explotara = man.accionBomba();
            if (explotara != null) {
                tablero[mposRow][mposCol].add(explotara);
            }
        }

        return explotara;
    }

    
    @Override
    public String toString() {
        ArrayList<String> cajasS = new ArrayList<>();
        ArrayList<String> cajasM = new ArrayList<>();
        ArrayList<String> manesS = new ArrayList<>();
        int nman=((int) template.opsForHash().get("game:"+Integer.toString(id_partida), "nmanes"));
        int ncajasM=((int) template.opsForHash().get("game:"+Integer.toString(id_partida), "nmanes"));
        int ncajasS=((int) template.opsForHash().get("game:"+Integer.toString(id_partida), "nmanes"));
        for (int i = 0; i < ncajasS; i++) {
            int x=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":cajasS:"+i, "x"));
            int y=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":cajasS:"+i, "y"));
            cajasS.add("{x:" + x + ",y:" + y + "}");
        }
        for (int i = 0; i < ncajasM; i++) {
            int x=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":cajasM:"+i, "x"));
            int y=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":cajasM:"+i, "y"));
            cajasM.add("{x:" + x + ",y:" + y + "}");
        }
        for (int i = 0; i < nman; i++) {
            int x=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":man:"+i, "x"));
            int y=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":man:"+i, "y"));
            manesS.add("{x:" + x + ",y:" + y + "}");
        }
        return "{\"cajas\":" + cajasS.toString() + ",\"cajasFijas\":" + cajasM.toString() + ",\"manes\":" + manesS.toString() + ",\"ancho\":" + ANCHO + ",\"alto\":" + ALTO + "}";
    }

    @Override
    public int getIdJugador(Jugador j) {
        return ((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":jugador:"+j.getApodo(), "id"));
    }

    @Override
    public ArrayList<Elemento> moverPersonaje(Jugador j, int key) {
        ArrayList<Elemento> changes = new ArrayList<>();
        Elemento e1, e2;
        int mposRow=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":man:"+j.getApodo(), "y"));
        int mposCol=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":man:"+j.getApodo(), "x"));
        Man man = new Man("", j, "", mposRow, mposCol, esEquipos);
        if (man.isBloqueado()) {
            return changes;
        }
        man.bloquear(Math.max(5 - man.getVelocidad(), 0) * 50);
        int posCol = man.getPosCol();
        int posRow = man.getPosRow();

        int filFutura = 0;
        int colFutura = 0;

        // Flecha Abajo
        switch (key) {
            case 40:
                filFutura = posRow + 1;
                colFutura = posCol;
                break;
            case 37:
                filFutura = posRow;
                colFutura = posCol - 1;
                break;
            case 38:
                filFutura = posRow - 1;
                colFutura = posCol;
                break;
            case 39:
                filFutura = posRow;
                colFutura = posCol + 1;
                break;
            default:
                break;
        }

        if (filFutura >= 0 && colFutura >= 0 && colFutura < ANCHO && filFutura < ALTO && puedo_moverme(filFutura, colFutura)) {
            man.setPosRow(filFutura);
            man.setPosCol(colFutura);
            e1 = man;
            //revisamos si hay un poder
            ArrayList<Elemento> elem = this.tablero[filFutura][colFutura].getAll();
            for (int i = 0; i < elem.size(); i++) {
                if (elem.get(i) instanceof Poder) {
                    man.setPoder((Poder) elem.get(i));
                    break;
                }
            }
            //reemplazamos cualquier cosa por el man
            this.tablero[filFutura][colFutura].reemplazar(e1);

            changes.add(e1);
            // Validacion para caso de Espacio pero que colocan una bomba
            if (hay_objeto(posRow, posCol, man)) {
                e2 = new Espacio("O", posRow, posCol);
                this.tablero[posRow][posCol].reemplazar(e2);
                changes.add(e2);
            } else {
                //Casilla cas = tablero[posRow][posCol].get()
                //e2 = new Bomba
                e2 = tablero[posRow][posCol].getAll().get(tablero[posRow][posCol].getAll().size() - 1);
                tablero[posRow][posCol].reemplazar(e2);
                //System.out.println("*************** BOMBA: " + e2.toString());
                changes.add(e2);
            }
        }

        //System.out.println("+++++++ Numero de Cambios: " + changes.size());
        return changes;
    }

    public Elemento explotarElemento(Elemento ele) {
        Elemento p = null;
        if (ele instanceof Caja) {//borramos la caja a menos que sea un poder ahora
            Random rand = new Random();
            int y = ele.getPosRow();
            int x = ele.getPosCol();
            p = new Espacio("O", y, x);
            switch (rand.nextInt(NUMPODERES + 2)) {
                case 0:
                    p = new PRedbull(y, x);
                    break;
                case 1:
                    p = new PTortuga(y, x);
                    break;
                case 2:
                    p = new PTurbo(y, x);
                    break;
                case 3:
                    p = new PTinto(y, x);
                    break;
                case 4:
                    p = new PAddBomba(y, x);
                    break;
                case 5:
                    p = new PLessBomba(y, x);
                    break;
            }
            tablero[y][x].reemplazar(p);
        }

        ((Destruible) ele).explotaBomba();

        if (ele instanceof Man) {
            if (!((Man) ele).estaVivo()) {
                p = new PSuper(ele.getPosRow(), ele.getPosCol());
            } else {
                p = null;
            }
        }
        juegoTermina();
        //si nada cambia dejar null
        return p;
    }

    public String estadisticasJuego() {
        
        String estManes = "\"manes\":[";
        // {apodo, vidas, bombas, radio, velocidad, puntos }
        for (int i = 0; i < jugadores.size(); i++) {
            int mposRow=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":man:"+j.getApodo(), "y"));
            int mposCol=((int) template.opsForHash().get("game:"+Integer.toString(id_partida)+":man:"+j.getApodo(), "x"));
            Man man = new Man("", new Jugador(i, estManes, estManes, estManes, estManes, estManes), "", mposRow, mposCol, esEquipos);
            estManes += man.toString()+",";           
        }
        estManes+= "]";
        String estadisticas = "{\"esEquipo\":" + esEquipos+ ","+estManes+ "}";
        System.out.println(estadisticas);
        return estadisticas;
    }

    @Override
    public boolean terminado(){
        return ((boolean) template.opsForHash().get("game:"+Integer.toString(id_partida), "terminado"));
    }  
}