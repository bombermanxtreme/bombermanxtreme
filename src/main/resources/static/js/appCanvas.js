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
            stompClient.subscribe("/topic/ponerBobmba." + idSala, function (eventbody) {
                callback_ponerBomba(eventbody);
            }); 
        });
    };

    
    var callback_ponerBomba = function (message) {        
        
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
        ponerBomba() {
            //reportamos que este usuario quiere poner una bomba			
            stompClient.send("/app/ponerBomba." + idSala, {}, idJugador);
        }

    };
})();