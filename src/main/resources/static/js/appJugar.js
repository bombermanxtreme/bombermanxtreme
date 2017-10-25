var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

var appJugar = (function () {

	var stompClient = null;
	var idSala=1;//por ahora una sola sala
	var numJugadores=0;

	var connectAndSubscribe = function () {
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		
		//subscribe to /topic/TOPICXX when connections succeed
		stompClient.connect({}, function (frame) {
			console.log('Connected: ' + frame);	
			//reportamos que este usuario quiere entrar al juego
			stompClient.send("/app/EntrarAJuego."+idSala, {}, $("#id_jugador").val());		
			//especificamos que estamos atentos de nuevos jugadores que entren
			stompClient.subscribe('/topic/EntraAJuego.'+idSala, function (eventbody) {
				callback_EntraAJuego(eventbody);
			});
		});
	};

	var callback_EntraAJuego=function(message) {
		var jugador=JSON.parse(message.body);
		console.log("Entra a juego:");
		console.log(jugador);
		//lo agregamos a la tabla de jugadores
		agregarJugadoresATabla(jugador);
	};

	var callbackActualizarJugadores=function(jugadores) {
		console.log("jugadores recibidos");
		console.log(jugadores);
		//armamos tabla con jugadores actuales y listos
		$("#antesDeEmpezar").html("<table id='listaJugadores'><thead><th>#</th><th>Nombre</th><th>Record</th><th>Listo</th></thead><tbody></tbody></table>");
		jugadores.map(agregarJugadoresATabla);
		//INICIAMOS CONEXIÓN
		connectAndSubscribe();
	};
	
	var agregarJugadoresATabla=function(jugador){
		numJugadores++;
		var listo=idSala==jugador.idSalaJugando?"LISTO":"NO";
		var filasHTML="<tr><td>"+numJugadores+"</td><td>"+jugador.nombre+"</td><td>"+jugador.record+"</td><td>"+listo+"</td></tr>";
		$("#listaJugadores > tbody").append(filasHTML);
	}

	return {
		/*
		COMENTADOS PORQUE SE GESTIONAN LUEGO DE CARGAR LA LISTA DE JUGADORES

		init: function () {
			//websocket connection
			connectAndSubscribe();
		},

		EntrarAJuego: function(){
			stompClient.send("/app/EntrarAJuego."+idSala, {}, $("#id_jugador").val());
			console.info("enviando...EntrarAJuego ");
		},
		*/

		disconnect: function () {
			if (stompClient !== null) {
				stompClient.disconnect();
			}
			//setConnected(false);
			console.log("Disconnected");
		},
		/**
		 * PARA EMPEZAR EJECUTAR ESTA FUNCION DESDE CONSOLA: appJugar.actualizarJugadores(); 
		 * esta función sólo se ejecuta cuando el jugador está ingresando porque de resto lo hace por medio de STOMP
		 */
        actualizarJugadores:function(){
            APIuseful.getJugadoresDeSala(idSala,callbackActualizarJugadores);
		}
    };
})();