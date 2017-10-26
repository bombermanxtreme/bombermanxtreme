/*---------------------------------------------
@Kevin  S. Sanchez
 Descripcion: Movimiento con teclado (Up, Down, Rght, Left) y deteccion de colisiones con obstaculos u otros objetos dentro de tablero.
-------------------------------------------- */

/*+++++++++++++++++++++++++++++++
ANIMACION REQUEST ANIMATION FRAME: Deteccion por navegador
+++++++++++++++++++++++++++++++++*/

var frame = window.requestAnimationFrame || 
window.mozRequestAnimationFrame || 
window.webkitRequestAnimationFrame || 
window.msRequestAnimationFrame;

var canvas = document.querySelector("#lienzo");
var ctx = canvas.getContext("2d");

/*++++++++++++++++++++++++++++
Jugadores
++++++++++++++++++++++++++++++*/

var jugador = {
        x: 280,
        y: 70,
        ancho: 10,
        alto: 10,
        color: "blue",
        movimiento_x: 0,
        movimiento_y: 0,
        velocidad: 2
}

/*++++++++++++++++
Obstaculos
++++++++++++++++++*/

var bloques = [{x:300, y:50, ancho: 400, alto: 10, color: "darkgreen"},
            {x:300, y:90, ancho: 10, alto: 360},
            {x:300, y:440, ancho: 400, alto: 10},
            {x:690, y:90, ancho: 10, alto: 360},
            {x:365, y:50, ancho:10, alto:350},
            {x:430, y:100, ancho:10, alto:350},
            {x:495, y:50, ancho:10, alto:350},
            {x:560, y:100, ancho:10, alto:350},
            {x:625, y:50, ancho:10, alto:350}
];

/*++++++++++++++++++++++++++++++
Propiedades de datos (Teclas)
++++++++++++++++++++++++++++++++*/	
var datos = {
    izquierda: false,
    derecha: false,
    arriba: false,
    abajo: false
}

/*+++++++++++++++++++++++
Funcionalidades del Juego
+++++++++++++++++++++++++*/			   

var juego = {

    /*++++++++++++++++++++++++++++++++
    Evento al presionar o soltar tecla
    ++++++++++++++++++++++++++++++++++*/
    teclado: function(){
        document.addEventListener("keydown", juego.oprimir)
        document.addEventListener("keyup", juego.soltar)
    },
    
    /*+++++++++++++++
    Oprimir tecla
    +++++++++++++++++*/
    oprimir: function(tecla){
        tecla.preventDefault();

        switch(tecla.keyCode){
            // Flecha Izquierda
            case 37: 
                datos.izquierda = true; 
                break;
            // Flecha Arriba
            case 38: 
                datos.arriba = true; 
                break;
            // Flecha Derecha
            case 39: 
                datos.derecha = true;
                break;
            // Flecha Abajo
            case 40: 
                datos.abajo = true; 
                break;
        }
    },

    /*
        Soltar Tecla
    */
    soltar: function(tecla){
        tecla.preventDefault();
        switch(tecla.keyCode){
            case 37: 
                datos.izquierda = false; 
                break;
            case 38: 
                datos.arriba = false; 
                break;
            case 39: 
                datos.derecha = false; 
                break;
            case 40: 
                datos.abajo = false; 
                break;
        }
    },

    tiempo: function(){
        /* Movimiento Horizontal del Jugador */
        jugador.x += jugador.movimiento_x;

        // Movimiento hacia la izquierda (Tecla oprimida)
        if(datos.izquierda){
            jugador.movimiento_x = -jugador.velocidad; 
            jugador.movimiento_y = 0
        }
        // Movimiento a la Derecha (Tecla oprimida)
        if(datos.derecha){
            jugador.movimiento_x = jugador.velocidad; 
            jugador.movimiento_y = 0
        }
        
        // Cuando la tecla no este oprimida no se reliza movimiento
        if(!datos.izquierda && !datos.derecha){
            jugador.movimiento_x = 0
        }

        /* Movimiento Vertical del Jugador */
        jugador.y += jugador.movimiento_y;

        // Movimiento arriba
        if(datos.arriba){
            jugador.movimiento_y = -jugador.velocidad; 
            jugador.movimiento_x = 0
        }
        // Movimiento abajo
        if(datos.abajo){
            jugador.movimiento_y = jugador.velocidad; 
            jugador.movimiento_x = 0
        }
        // No realizar movimiento cuando no esta presionada la tecla
        if(!datos.arriba && !datos.abajo){
            jugador.movimiento_y = 0
        }

        /*++++++++++++++
        Ejecucion y se dibujan elementos en el canvas
        ++++++++++++++++*/
        juego.canvas();

        /*+++
        Se activa Linea de Tiempo
        ++*/
        frame(juego.tiempo)
    },

    /* Limpiar canvas y redibujar jugador y obstaculos */
    canvas: function(){
        // Limpiar Lienzo
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Dibujar al jugador que por el momento es un cuadro
        ctx.fillStyle = jugador.color;
        ctx.fillRect(jugador.x, jugador.y, jugador.ancho, jugador.alto);

        //Dibujar los Obstaculos
        ctx.fillStyle = bloques[0].color;

        for(var i = 0; i < bloques.length; i++){
            ctx.fillRect(bloques[i].x, bloques[i].y, bloques[i].ancho, bloques[i].alto);	
        }

    }

}

juego.teclado();
juego.tiempo();

