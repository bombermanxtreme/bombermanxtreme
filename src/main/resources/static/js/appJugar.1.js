var appCookie = (function () {
    var stompClient = null;
    var jugadorEnSala = null;// null=> usuario va a ingresar, false=>sala cerrada no pudo entrar, true=> usuario en sala
    var idSala = 1;//por ahora una sola sala
    var jugadorListo = false;
    var segundosRestantes = null;//cuando el tiempo empieza a correr
    var imgCargando = "<img src='/media/cargando.gif' class='imgCargando'>";

	var _getIdJugador=function(fueraDeJuego){
		var idJugador=-1;
		document.cookie.split("; ").map(function(e) {
			var _cookie=e.split("=");
			if(_cookie[0]=="iduser")
				idJugador=_cookie[1];
		});

		if (!fueraDeJuego && idJugador<0) {
			setTimeout(function(params) {//demoramos un poco
				MJ_simplex("Jugar","Inicia sesión por favor, te vamos a redirigir en 3 segundos...<br>",true);
			},100);
			setTimeout(function(){
				location.href="login.html";
			},3000);
		}

		if(fueraDeJuego && idJugador>=0)
			location.href="jugar.html";
		return idJugador;
	}
    var idJugador = -1;

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
        });
    };

    /**
     * cada vez que hay un jugador nuevo esta función recibe TODOS los que quieren jugar (incluido el nuevo y los que ya están listos!)
     * y los coloca en la tabla de jugadores
     * @param {*} message - message.body listado de jugadores, si es false=> la sala está cerrada
     */
    var callback_JugadoresQuierenJugar = function (message) {
        //verificamos si el usuario acaba de entrar y si la sala ya está cerrada
        if (jugadorEnSala === null && message.body === "false") {
            $("#antesDeEmpezar").html("Sala cerrada");
            jugadorEnSala = false;
            return false;
        }
        //omitir si es un jugador que está en sala pero alguien intentó entrar y ya la sala está cerrada
        if (message.body === "false")
            return false;

        var jugadores = JSON.parse(message.body);

        //si el tiempo no ha empezado
        if (segundosRestantes === null)
            $("#tiempo").html(jugadorListo ? "Esperando mínimo de jugadores " + imgCargando : "");

        //definimos que el jugador si pudo entrar a la sala
        if (jugadorEnSala === null) {
            jugadorEnSala = true;
            //armamos tabla con jugadores actuales y listos
            $("#antesDeEmpezar").html("<input type='button' value='Ya estoy listo!' onclick='appJugar.estoyListo();'><div id='tiempo'></div><br><br><table id='listaJugadores'><thead><th>#</th><th>Nombre</th><th>Record</th><th>&nbsp;</th></thead><tbody></tbody></table>");
        }

        //borramos todos los jugadores
        $("#listaJugadores > tbody > tr").remove();

        //agregamos TODOS los jugadores a la tabla
        var numJugadores = 0;
        jugadores.map(function (jugador) {
            numJugadores++;
            var listo = jugador.listo === true ? "LISTO" : "";
            var filasHTML = "<tr><td>" + numJugadores + "</td><td>" + jugador.nombre + "</td><td>" + jugador.record + "</td><td>" + listo + "</td></tr>";
            $("#listaJugadores > tbody").append(filasHTML);
        });
	};
	
    /**
     * cuando los jugadores están listos empieza el reloj
     * @param {*} message 
     */
    var callback_ListoMinimoJugadores = function (message) {
        segundosRestantes = message.body;
        var mjTiempo = jugadorListo ? "Esperando algunos jugadores" : "Mínimos jugadores requeridos listos apúrate te quedan";
        $("#tiempo").html(mjTiempo + ": <span id='segundos'>" + segundosRestantes + "</span> segundos.");

        //disminuir tiempo cada segundo
        var restarSegundos = function () {
            if (segundosRestantes === 0) {
                if (jugadorListo)
                    location.href = "/indexCanvas.html";
                else
                    alert("no entraste al juego intenta en otra sala");
                return false;
            }
            $("#segundos").text(segundosRestantes--);
            setTimeout(restarSegundos, 1000);
        };
        setTimeout(restarSegundos, 1000);
    
    }
    return {
        /**
         * encargado de realizar la conexión con STOMP
         */
        init() {
			//verificamos que el usuario haya iniciado
			idJugador=_getIdJugador(false);
			if(idJugador==-1)
				return false;
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
        estoyListo() {
            jugadorListo = true;
            $("#antesDeEmpezar > input[type=button]").remove();
            //reportamos que este usuario quiere entrar al juego
            stompClient.send("/app/JugadorListo." + idSala, {}, idJugador);
		},
		getIdJugador:_getIdJugador
    };
})();