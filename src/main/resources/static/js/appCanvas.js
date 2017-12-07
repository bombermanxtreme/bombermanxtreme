//var APIuseful = apimockJugar;
var APIuseful = apiclientCanvas;

var appCanvas = (function () {

    var stompClient = null;
    var idJugador = document.cookie.replace("iduser=", "");
    var idSala = 1;//por ahora una sola sala
    var canvas;
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
                callback_accionBomba(eventbody);
            });

            //Estamos atentos si se mueve algun jugador dentro de l
            stompClient.subscribe("/topic/moverPersonaje." + idSala, function (eventbody) {
                callback_moverPersonaje(eventbody);
            });

        });
    };

    // Funciones 
    var callback_ponerBomba = function (message) {

    };

    var callback_moverPersonaje = function (message) {
        var data = message;
    };

    var getJuego = function () {
        APIuseful.getJuego(idSala, function (data) {
            var datosJuego=eval("("+data+")");
            tablero=Array();
            for (var i = 0; i < datosJuego.alto; i++) {
                tablero[i]=Array();
                for (var k = 0; k < datosJuego.ancho; k++) {
                    tablero[i][k]="O";
                }
            }
            for (var i = 0; i < datosJuego.cajas.length; i++) {
                var x=datosJuego.cajas[i].x;
                var y=datosJuego.cajas[i].y;
                tablero[y][x]="X";
            }
            //
            //hacemos el tablero str
            //actualizamos el canvas
            actualizar();
        });
    };

    var loadBasicControls = function () {
        console.info('Cargando script!');
        connectAndSubscribe();
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

    var actualizar = function () {
        //dibuja el canvas COMPLETO!
        console.log(tablero);
        for (i = 0; i < tablero.length; i++) {
            for (j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] === "X") {
                    var myObstacle = new Caja(50, 50, "green", j * 50, i * 50);
                    myObstacle.update();

                } else {
                    var myObstacle = new Caja(50, 50, "blue", j * 50, i * 50);
                    myObstacle.update();
                }
            }
        }
        console.log("LLENANDO CANVAS");
    };


    var callback_accionBomba = function (message) {

    };

    function Caja(width, height, color, x, y, type) {
        this.type = type;
        if (type === "image") {
            this.image = new Image();
            this.image.src = color;
        }
        this.width = width;
        this.height = height;
        this.speedX = 0;
        this.speedY = 0;
        this.x = x;
        this.y = y;

        this.update = function () {
            //var canvas = document.getElementById('cnv');
            //var ctx = canvas.getContext('2d');
            if (type === "image") {
                ctx.drawImage(this.image,
                        this.x,
                        this.y,
                        this.width, this.height);
            } else {
                ctx.fillStyle = color;
                ctx.fillRect(this.x, this.y, this.width, this.height);
            }
        };
    }

    return {
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
            stompClient.send("/app/accionBomba." + idSala, {}, idJugador);
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