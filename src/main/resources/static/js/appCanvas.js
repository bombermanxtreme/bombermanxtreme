//var APIuseful = apimockJugar;
var APIuseful = apiclientCanvas;

var appCanvas = (function () {

    var stompClient = null;
    var idJugador = appCookie.getIdJugador(false);
    var idSala = appCookie.getSala();
    var canvas;
    var anchoCasilla = 50;
    var ctx;
    var tablero;

    /**
     * función que realiza la conexión STOMP
     */
    var connectAndSubscribe = function () {
        console.info("Connecting to WS...");
        var socket = new SockJS("/stompendpoint");
        stompClient = Stomp.over(socket);

        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log("Conectado: " + frame);

            //especificamos que estamos atentos a poner bombas de jugadores
            stompClient.subscribe("/topic/accionBomba." + idSala, function (eventbody) {
                callback_accionBomba(eventbody.body);
            });

            //Estamos atentos si se mueve algun jugador dentro de l
            stompClient.subscribe("/topic/moverPersonaje." + idSala, function (eventbody) {
                callback_moverPersonaje(eventbody);
            });

            //Estamos atentos si se daña alguna caja
            stompClient.subscribe("/topic/DaniarCaja." + idSala, function (eventbody) {
                callback_DaniarCaja(eventbody);
            });

        });
    };

    // Funciones 
    
    /**
     * daña una caja específica
     * @param {*} message 
     */
    var callback_DaniarCaja = function (message) {
        var cajaADaniar = message.body;
        tablero[cajaADaniar.y][cajaADaniar.x] = "c";
        actualizar();
    };

    var callback_moverPersonaje = function (message) {
        var data = message;
    };

    var getJuego = function () {
        APIuseful.getJuego(idSala, function (data) {
            var datosJuego = eval("(" + data + ")");
            tablero = Array();
            //llenamos todo de vacíos
            for (var i = 0; i < datosJuego.alto; i++) {
                tablero[i] = Array();
                for (var k = 0; k < datosJuego.ancho; k++) {
                    tablero[i][k] = "O";
                }
            }
            //cargamos las cajas
            for (var i = 0; i < datosJuego.cajas.length; i++) {
                var x = datosJuego.cajas[i].x;
                var y = datosJuego.cajas[i].y;
                tablero[y][x] = "C";
            }
            //
            //hacemos el tablero str
            //actualizamos el canvas
            actualizar();
        });
    };

    var loadBasicControls = function () {
        console.info('Cargando script!');
        canvas = document.getElementById('lienzo');
        ctx = canvas.getContext('2d');

        window.addEventListener('keydown', function (e) {
            key = e.keyCode;
            moverPersonaje(key);
            console.log(key);
        });
        window.addEventListener('keyup', function (e) {
            key = false;
        });
    };

    function moverPersonaje(key) {
        if (36 < key && key < 41) {
            console.log("/// Me estoy moviendo :D");
            //stompClient.send("/app/mover",{}, JSON.stringify( {x: myposx, y: myposy, k: key}));
        }

    }

    function getParameterByName(name, url) {
        if (!url)
            url = window.location.href;
        name = name.replace(/[\[\]]/g, "\\$&");
        var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                results = regex.exec(url);
        if (!results)
            return null;
        if (!results[2])
            return '';
        return decodeURIComponent(results[2].replace(/\+/g, " "));
    }

    /**
     * encargado de redibujar el canvas
     */
    var actualizar = function () {
        //dibuja el canvas COMPLETO!
        console.log(tablero);
        for (i = 0; i < tablero.length; i++) {
            
            for (j = 0; j < tablero[i].length; j++) {
                switch (tablero[i][j]) {
                    case "C"://caja
                        var myObstacle = new Caja("#a27250", j, i);
                        myObstacle.update();
                        break;
                    case "c"://caja dañada
                        anim_cajaDañada(j, i);
                        break;
                    case "O"://nada
                        ctx.clearRect(j * anchoCasilla, i * anchoCasilla, (j + 1) * anchoCasilla, (i + 1) * anchoCasilla);
                        break;
                }
            }
        }
        console.log("LLENANDO CANVAS");
    };

    /**
     * método encargado de animar una caja dañandose 
     * @param {*} j 
     * @param {*} i 
     */
    var anim_cajaDañada = function (j, i) {
        var myObstacle = new Caja("#222222", j, i);
        myObstacle.update();
        tablero[i][j] = "O";
        setTimeout(function () {
            actualizar();
        }, 100);
    };

    var callback_accionBomba = function (data) {
        var J = eval("(" + data + ")");
        if(J.length>0){
            console.log(J);
        }
        

    };

    function Caja(color, x, y) {
        //this.type = type;
        /*if (type === "image") {
         this.image = new Image();
         this.image.src = color;
         }*/
        this.update = function () {
            /*if (type === "image") {
             ctx.drawImage(this.image,
             this.x,
             this.y,
             this.width, this.height);
             } else {*/
            x *= anchoCasilla;
            y *= anchoCasilla;
            ctx.fillStyle = color;
            ctx.fillRect(x, y, anchoCasilla, anchoCasilla);
            ctx.beginPath();
            ctx.moveTo(x, y);
            ctx.lineTo(anchoCasilla + x, y);
            ctx.lineTo(anchoCasilla + x, anchoCasilla + y);
            ctx.lineTo(x, anchoCasilla + y);
            ctx.lineTo(x, y);
            ctx.stroke();
            ctx.beginPath();
            ctx.moveTo(x, y);
            ctx.lineTo(anchoCasilla + x, anchoCasilla + y);
            ctx.stroke();
            ctx.beginPath();
            ctx.moveTo(anchoCasilla + x, y);
            ctx.lineTo(x, anchoCasilla + y);
            ctx.stroke();
            //}
        };
    }

    return {
        //estas funciones publicas son sólo para pruebas
        _actualizar: actualizar,
        setTablero(i, k, val) {
            tablero[i][k] = val;
        },
        /**
         * encargado de realizar la conexión con STOMP
         */
        init() {
            console.log("***** Iniciando Script!!");
            console.log("Jugador: " + idJugador);
            idJugador = appCookie.getIdJugador(false);

            // Cargamos elementos clave para dibujar en Tablero
            loadBasicControls();
            // Traer Numero de sala
            idSala = appCookie.getSala();
            console.log("Este es el numero de Sala en el JS: " + idSala);
            //pedir estado inicial del juego
            getJuego();
            //INICIAMOS CONEXIÓN
            connectAndSubscribe();
        },
        /**
         * desconecta del STOMP
         */
        disconnect() {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            //setConnected(false);
            console.log("Desconectado");
        },
        /**
         * envia que ya está listo este usuario
         */
        accionBomba() {
            //reportamos que este usuario quiere poner una bomba	
            console.log(idJugador);
            stompClient.send("/app/AccionBomba." + idSala, {}, idJugador);
        }

    };

})();

//$(document).ready(
//    function () {
//        console.info('Cargando script!');
//        connect();
//        canvas = document.getElementById('lienzo');
//        ctx = canvas.getContext('2d');
//
//        window.addEventListener('keydown', function (e) {
//            key = e.keyCode;
//            moverPersonaje(key);
//            console.log(key);
//        })
//        window.addEventListener('keyup', function (e) {
//            key = false;
//        })
//
//    }
//);