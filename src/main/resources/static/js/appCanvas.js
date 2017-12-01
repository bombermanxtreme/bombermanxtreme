//var APIuseful = apimockJugar;
var APIuseful=apiclientCanvas;

var appCanvas = (function () {

    var stompClient = null;
    var idJugador = document.cookie.replace("iduser=", "");    
    var idSala = 1;//por ahora una sola sala    

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
            stompClient.subscribe("/topic/ponerBomba." + idSala, function (eventbody) {
                callback_ponerBomba(eventbody);
            }); 
            
            //Estamos atentos si se mueve algun jugador dentro de l
            stompClient.subscribe("/topic/moverPersonaje." + idSala, function (eventbody) {
                callback_moverPersonaje(eventbody);
            });

        });
    };

    // Funciones 
    var callback_ponerBomba = function (message) {        
        
    }

    var getJuego=function() {
        APIuseful.getJuego(idSala,function(data){
            console.log(data);
            //hacemos el tablero str
            //actualizamos el canvas
            actualizar();
        });
    }

    var actualizar=function(){
        //dibuja el camvas COMPLETO!
    }

    return {
		/**
         * encargado de realizar la conexión con STOMP
         */
        init(){
            //verificamos que el usuario haya iniciado
            if (idJugador=="" || isNaN(idJugador) || idJugador < 0) {
				MJ_simplex("Jugar","Inicia sesión por favor, te vamos a redirigir en 3 segundos...<br>",true);
				setTimeout(function(){
					location.href="login.html";
				},3000);
                return false;
            }
            
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
        ponerBomba() {
            //reportamos que este usuario quiere poner una bomba			
            stompClient.send("/app/ponerBomba." + idSala, {},idJugador);
        }

    };
    
    var callback_moverPersonaje = function (message) {        
        var data = message;
    }
    
    return {
	/**
         * encargado de realizar la conexión con STOMP
         */
        init() {
            //verificamos que el usuario haya iniciado
            if (isNaN(idJugador) || idJugador < 0) {
                alert("Inicia sesión por favor");
                return false;
            }
            $("#antesDeEmpezar").html("Cargando Jugadores... " + imgCargando);
            
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
         * Mover Personaje
         */
        moverPersonaje() {
            console.log(data);
            var cambios = JSON.parse(data.body);
            //reportamos que este usuario ha presionado una tecla para mover el personaje			
            stompClient.send("/app/moverPersonaje." + idSala, {}, idJugador);
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