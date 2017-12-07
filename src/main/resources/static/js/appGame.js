//var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

var appGame = (function () {
    var stompClient = null;
    var idSala = appCookie.getSala();
    var idJugador = appCookie.getIdJugador();
    /**
     * función que realiza la conexión STOMP
     */
    var connectAndSubscribe = function () {
        console.info("Connecting to WS...");
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);

        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log("Conectado: " + frame);
            /*
             //especificamos que estamos atentos de nuevos jugadores que entren
             stompClient.subscribe("/topic/JugadoresQuierenJugar." + idSala, function (eventbody) {
             callback_JugadoresQuierenJugar(eventbody);
             });
             
             //especificamos que estamos atentos de que cumpla el mínimo de jugadores
             stompClient.subscribe("/topic/ListoMinimoJugadores." + idSala, function (eventbody) {
             callback_ListoMinimoJugadores(eventbody);
             });
             
             //reportamos que este usuario quiere entrar al juego
             stompClient.send("/app/EntrarAJuego." + idSala, {}, idJugador);
             */
        });
    };

    /**
     * cuando los jugadores están listos empieza el reloj
     * @param {*} message 
     *
     var callback_ListoMinimoJugadores = function (message) {
     segundosRestantes = message.body;
     var mjTiempo = jugadorListo ? "Esperando algunos jugadores" : "Mínimos jugadores requeridos listos apúrate te quedan";
     $("#tiempo").html(mjTiempo + ": <span id='segundos'>" + segundosRestantes + "</span> segundos.");
     
     //disminuir tiempo cada segundo
     var restarSegundos = function () {
     if (segundosRestantes === 0) {
     if (jugadorListo){
     appCookie.setSala(idSala);
     location.href = "/indexPlay.html";
     }else
     MJ_simple("Jugar","no entraste al juego intenta en otra sala");
     return false;
     }
     $("#segundos").text(segundosRestantes--);
     setTimeout(restarSegundos, 1000);
     };
     setTimeout(restarSegundos, 1000);
     
     }*/
    return {
        /**
         * encargado de realizar la conexión con STOMP
         */
        init() {
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