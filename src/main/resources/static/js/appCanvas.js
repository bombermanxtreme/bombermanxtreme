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
    var _manes;
    var _id_man;
    var myplayer = null;
    var myposx = null;
    var myposy = null;
    var keyPress = null;

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
            stompClient.subscribe("/topic/AccionBomba." + idSala, function (eventbody) {
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
            
            //Estamos atentos si se daña alguna caja
            stompClient.subscribe("/topic/actualizar." + idSala, function (eventbody) {
                callback_actualizar(eventbody);
            });

        });
    };
    
    function isNumber(n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    }

    // Funciones 
    
    /**
     * daña una caja específica
     * @param {*} message 
     */
    var callback_actualizar = function (data) {
        var tempotab = JSON.parse(data.body);
        console.log(data)
        var y, x, k;
        for (i = 0; i < tempotab.length; i++) {
            y = tempotab[i].y;
            x = tempotab[i].x;
            k = tempotab[i].key;
            tablero[y][x] = k;
            console.log("----- Tablero antes de Modificar: " + tablero);
            console.log("+++++ Esto es lo que voy a modificar: Y:" + y + ", X:" + x + ", K:" + k);
        }
        actualizar();
    };
    
    var callback_DaniarCaja = function (message) {
        var cajaADaniar = eval("("+message.body+")");
        console.log(cajaADaniar.y);
        console.log(cajaADaniar.x);
        tablero[cajaADaniar.y][cajaADaniar.x] = "c";
        actualizar();
    };
    
    function getCookie(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    };

    var callback_moverPersonaje = function (message) {
        var data = message;
    };

    var getJuego = function () {
        APIuseful.getJuego(idSala, function (data) {
            var nameCookie = getCookie("nombreuser");
            console.log("xdfcgvhbjnmk,lñ 111"+data);
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
            
            //cargamos las cajasfijas
            for (var i = 0; i < datosJuego.cajasFijas.length; i++) {
                var x = datosJuego.cajasFijas[i].x;
                var y = datosJuego.cajasFijas[i].y;
                tablero[y][x] = "X";
            }

            _manes = datosJuego.manes;
            //cargamos los manes
            for (var i = 0; i < datosJuego.manes.length; i++) {
                var x = datosJuego.manes[i].x;
                var y = datosJuego.manes[i].y;
                
                var color = datosJuego.manes[i].color;
                var apodo = datosJuego.manes[i].apodo_jugador;
                tablero[y][x] = datosJuego.manes[i].key;
                
                //Verificar KSSP
                //console.log("/// Este es el nombre de la sesion = " + appCookie.getNombre());
                if(nameCookie === apodo){
                    _id_man = parseInt(getCookie("iduser"));
//                    switch (_id_man){
//                        case 0://Jugador1
//                            myposx = 0;
//                            myposy = 0;
//                            break;
//                        case 1://Jugador2
//                            myposx = 19;
//                            myposy = 9;
//                            break;
//                        case 2://Jugador3
//                            myposx = 19;
//                            myposy = 0;
//                            break;
//                        case 3://Jugador4
//                            myposx = 0;
//                            myposy = 9;
//                            break;
//                    }
                }
            }
            //actualizamos el canvas
            actualizar();
        });
    };

    var loadBasicControls = function () {
        //console.info('Cargando script!');
        canvas = document.getElementById('lienzo');
        ctx = canvas.getContext('2d');

        window.addEventListener('keydown', function (e) {
            key = e.keyCode;
            if(key==32)colocarBomba();
            else moverPersonaje(key);
            console.log(key);
        });
        window.addEventListener('keyup', function (e) {
            key = false;
        });
    };

    function moverPersonaje(key) {
        if (36 < key && key < 41) {
            //console.log("/// Me estoy moviendo :D");
            keyPress = key;
            stompClient.send("/app/mover." + idSala + "." + key, {}, idJugador);
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
        //console.log(tablero);
        for (i = 0; i < tablero.length; i++) {
            for (j = 0; j < tablero[i].length; j++) {
                if (isNumber(tablero[i][j])){
                    var myPlayer = new Player(tablero[i][j], j * anchoCasilla, i * anchoCasilla, anchoCasilla, anchoCasilla, "image");
                    myPlayer.update();
                    
                }else{
                    switch (tablero[i][j]){
                        case "C"://caja
                            var myObstacle = new Objeto("wood", j * anchoCasilla, i * anchoCasilla, anchoCasilla, anchoCasilla, "image");
                            myObstacle.update();
                            break;
                        case "X"://Pared
                            var myObstacle = new Objeto("wall", j * anchoCasilla, i * anchoCasilla, anchoCasilla, anchoCasilla, "image");
                            myObstacle.update();
                            break;
                        case "c"://caja dañada
                            //console.log("** Entre a dibujar CajaDañada");
                            anim_cajaDañada(j, i);
                            break;
                        case "O"://nada
                            var myObstacle = new Objeto("grass", j * anchoCasilla, i * anchoCasilla, anchoCasilla, anchoCasilla, "image");
                            myObstacle.update();
                            break;
                        case "B"://nada
                            var myObstacle = new Objeto("bomba", j * anchoCasilla, i * anchoCasilla, anchoCasilla, anchoCasilla, "image");
                            myObstacle.update();
                            break;
                    }
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
        console.log(J);
        //bomba
        var bomba=J.bomba;
        //fuego
        if(bomba.estallo==true){
            var coords=J.coords;
            for (var i = 0; i < coords.length; i++) {
                tablero[coords[i].y][coords[i].x]="b";
            }
        }else{
            tablero[bomba.y][bomba.x]="B";
        }
        
        actualizar();
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
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
        
         
        this.update = function () {
            if (type === "image") {
                var img = document.getElementById(color);
                ctx.drawImage(img,
                        this.x,
                        this.y,
                        this.ancho, this.alto);
            } else {
                //console.log(ctx);
                ctx.fillStyle = color;
                ctx.fillRect(this.x, this.y, this.ancho, this.alto);
            }
        };
    }
    
    function Player(color, x, y, ancho, alto, type) {
        this.type = type;
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
        
         
        this.update = function () {
            if (type === "image") {
                //console.log("++ COLOCANDO IMAGEN de Jugador");
                var img;
                var sx, sy, swidth, sheight;
//                if (tablero[i][j] == _id_man){
//                    console.log("KEYPRESS: " + keyPress);
//                    switch (keyPress){
//                        // Abajo
//                        case 40:
//                            sx = 50;
//                            sy = 0;
//                            break;
//                        // Izquierda
//                        case 37:
//                            sx = 0;
//                            sy = 100;
//                            break;
//                        // Arriba
//                        case 38:
//                            sx = 0;
//                            sy = 150;
//                            break;
//                        // Derecha
//                        case 39:
//                            sx = 0;
//                            sy = 200;
//                            break;
//                    }
//                }else{
//                    sx = 0;
//                    sy = 0;
//                }
//                
                sx = 0;
                sy = 0;
                swidth = 50;
                sheight = 50;
                
                switch (tablero[i][j]){
                    case "0"://caja
                        img = document.getElementById("george");
                        break;
                    case "1"://caja
                        img = document.getElementById("alfredo");
                        break;
                    case "2"://Pared
                        img = document.getElementById("pirata");
                        break;
                    case "3"://caja dañada
                        img = document.getElementById("sergio");
                        break;
                    default :
                        img = document.getElementById("betty2");
                        break;
                }
                
                ctx.drawImage(img,
                        sx,
                        sy,
                        swidth,
                        sheight,
                        this.x,
                        this.y,
                        this.ancho, this.alto);
            } else {
                //console.log(ctx);
                ctx.fillStyle = color;
                ctx.fillRect(this.x, this.y, this.ancho, this.alto);
            }
        };
    }

    /**
     * coloca una bomba luego de presionar espacio
     */
    var colocarBomba =function () {
        stompClient.send("/app/AccionBomba." + idSala, {}, idJugador);
    };

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
            //console.log("***** Iniciando Script!!");
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
        }
    };

})();