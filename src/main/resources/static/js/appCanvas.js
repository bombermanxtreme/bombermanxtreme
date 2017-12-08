//var APIuseful = apimockJugar;
var APIuseful = apiclientCanvas;

var appCanvas = (function () {

    var stompClient = null;
    var idJugador = document.cookie.replace("iduser=", "");
    var idSala = 1;//por ahora una sola sala
    var canvas;
    var anchoCasilla = 50;
    var ctx;
    var tablero;
    var _manes;
    var _id_man;

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

            //Estamos atentos si se daña alguna caja
            stompClient.subscribe("/topic/DaniarCaja." + idSala, function (eventbody) {
                callback_DaniarCaja(eventbody);
            });

        });
    };

    // Funciones 
    var callback_ponerBomba = function (message) {

    };
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
                console.log("x:"+x);
                console.log("y:"+y);
                tablero[x][y] = "C";
            }
            
            //cargamos las cajasfijas
            for (var i = 0; i < datosJuego.cajasFijas.length; i++) {
                var x = datosJuego.cajasFijas[i].x;
                var y = datosJuego.cajasFijas[i].y;
                console.log("x:"+x);
                console.log("y:"+y);
                tablero[x][y] = "X";
            }

            _manes=datosJuego.manes;
            //cargamos los manes
            for (var i = 0; i < datosJuego.manes.length; i++) {
                var x=datosJuego.manes[i].x;
                var y=datosJuego.manes[i].y;
                var color=datosJuego.manes[i].color;
                var apodo=datosJuego.manes[i].apodo_jugador;
                // Verificar
//                if(appCookie.getNombre()==apodo)
//                    _id_man=i;
//                    tablero[x][y]=i;
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

    /**
     * encargado de redibujar el canvas
     */
    var actualizar = function () {
        //dibuja el canvas COMPLETO!
        console.log(tablero);
        for (i = 0; i < tablero.length; i++) {
            for (j = 0; j < tablero[i].length; j++) {
                switch (tablero[i][j]){
                    case "C"://caja
                        //var myObstacle = new Caja("#a27250", j, i);
                        console.log("** Entre a dibujar Caja");
                        var myObstacle = new Objeto("wood", j * 50, i * 50, 50, 50, "image");
                        //var myObstacle = new Objeto("red", j * 50, i * 50, 50, 50, "");
                        myObstacle.update();
                        break;
                    case "X"://Pared
                        //var myObstacle = new Objeto("green", j * 50, i * 50, 50, 50, "");
                        console.log("** Entre a dibujar Caja Metalica");
                        var myObstacle = new Objeto("wall", j * 50, i * 50, 50, 50, "image");
                        myObstacle.update();
                        break;
                    case "c"://caja dañada
                        console.log("** Entre a dibujar CajaDañada");
                        anim_cajaDañada(j, i);
                        break;
                    case "O"://nada
                        //ctx.clearRect(j * anchoCasilla, i * anchoCasilla, (j + 1) * anchoCasilla, (i + 1) * anchoCasilla);
                        console.log("** Entre a dibujar Espacio");
                        //var myObstacle = new Objeto("https://secure.gravatar.com/avatar/107d8490cb488836a968eb085b870621?s=50&d=retro&r=g", i * 50, j * 50, 50, 50, "image");
                        //var myObstacle = new Objeto("yellow", j * 50, i * 50, 50, 50, "");
                        var myObstacle = new Objeto("grass", j * 50, i * 50, 50, 50, "image");
                        myObstacle.update();
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
    }

    var callback_accionBomba = function (message) {

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
    
    function Objeto(color, x, y, ancho, alto, type) {
        this.type = type;
        if (type === "image") {
            //this.imagen = new Image();
            //this.imagen.src = color;
            //var img = document.getElementById("scream");
            console.log("**** Es una imagen :D");
        }
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
        
         
        this.update = function () {
            if (type === "image") {
                console.log("** COLOCANDO IMAGEN");
                var img = document.getElementById(color);
                ctx.drawImage(img,
                        this.x,
                        this.y,
                        this.ancho, this.alto);
            } else {
                console.log(ctx);
                ctx.fillStyle = color;
                ctx.fillRect(this.x, this.y, this.ancho, this.alto);
            }
        };
    }

    return {
        //estas funciones publicas son sólo para pruebas
        _actualizar: actualizar,
        _ctx:function(){
            return ctx;
        },
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