//var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

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
            stompClient.subscribe("/topic/accionBomba." + idSala, function (eventbody) {
                callback_accionBomba(eventbody);
            }); 
            
            //Estamos atentos si se mueve algun jugador dentro de l
            stompClient.subscribe("/topic/moverPersonaje." + idSala, function (eventbody) {
                callback_moverPersonaje(eventbody);
            });

        });
    };

    
    var callback_accionBomba = function (message) {        
        
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
            stompClient.send("/app/accionBomba." + idSala, {},idJugador);
        }

    };
    
    var callback_moverPersonaje = function (message) {        
        
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
         * envia que ya está listo este usuario
         */
        moverPersonaje() {
            //reportamos que este usuario ha presionado una tecla para mover el personaje			
            stompClient.send("/app/moverPersonaje." + idSala, {},idJugador);
        }

    };
})();