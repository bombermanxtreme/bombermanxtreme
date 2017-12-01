//var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

var appSala=(function(){
	var stompClient = null;
	var idSala = 1;//por ahora una sola sala
	var imgCargando = "<img src='/media/cargando.gif' class='imgCargando'>";

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

	var getSalas=function(){
		$.get("/sala",
			function (data) {
				console.info("data: "+data);
			}
		).fail(
			
		);
	};

	return {
		/**
		 * encargado de realizar la conexión con STOMP
		 */
		init() {
			//verificamos que el usuario haya iniciado
			idJugador=appCookie.getIdJugador(false);
			if(idJugador==-1)
				return false;
			$("#antesDeEmpezar").html("Cargando Salas... " + imgCargando);
			//INICIAMOS CONEXIÓN
			connectAndSubscribe();
			getSalas();
		},
		/**
		 * desconecta del STOMP
		 */
		disconnect() {
			if (stompClient!==null) {
				stompClient.disconnect();
			}
			//setConnected(false);
			console.log("Desconectado");
		}
	};
})();