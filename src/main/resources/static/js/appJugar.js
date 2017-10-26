var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

var appJugar = (function () {

	var stompClient = null;
	var idSala=1;//por ahora una sola sala
	var numJugadores=0;

	/**
	 * función que realiza la conexión STOMP
	 */
	var connectAndSubscribe = function () {
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		
		//subscribe to /topic/TOPICXX when connections succeed
		stompClient.connect({}, function (frame) {
			console.log('Conectado: ' + frame);	

			//especificamos que estamos atentos de nuevos jugadores que entren
			stompClient.subscribe('/topic/EntraAJuego.'+idSala, function (eventbody) {
				callback_EntraAJuego(eventbody);
			});
			
			//reportamos que este usuario quiere entrar al juego
			stompClient.send("/app/EntrarAJuego."+idSala, {}, $("#id_jugador").val());		
		});
	};

	/**
	 * cada vez que hay un jugador nuevo esta función recibe TODOS los que quieren jugar (incluido el nuevo y los que ya están listos!)
	 * y los coloca en la tabla de jugadores
	 * @param {*} message 
	 */
	var callback_EntraAJuego=function(message) {
		var jugadores=JSON.parse(message.body);
		//borramos y armamos tabla con jugadores actuales y listos
		$("#antesDeEmpezar").html("<table id='listaJugadores'><thead><th>#</th><th>Nombre</th><th>Record</th><th>Listo</th></thead><tbody></tbody></table>");
		//agregamos TODOS los jugadores a la tabla
		jugadores.map(function(jugador){
			numJugadores++;
			var listo=idSala==jugador.idSalaJugando?"LISTO":"NO";
			var filasHTML="<tr><td>"+numJugadores+"</td><td>"+jugador.nombre+"</td><td>"+jugador.record+"</td><td>"+listo+"</td></tr>";
			$("#listaJugadores > tbody").append(filasHTML);
		});
	};

	return {
		/**
		 * encargado de realizar la conexión con STOMP
		 */
		init: function(){
			//INICIAMOS CONEXIÓN
			connectAndSubscribe();
		},
		/**
		 * desconecta del STOMP
		 */
		disconnect: function () {
			if (stompClient !== null) {
				stompClient.disconnect();
			}
			//setConnected(false);
			console.log("Desconectado");
		},
		estoyListo:function(id) {
			
			//reportamos que este usuario quiere entrar al juego
			stompClient.send("/app/listoJugar."+idSala, {}, id);		
		}

    };
})();

//PARA EMPEZAR EJECUTAR ESTA FUNCION DESDE CONSOLA: appJugar.init(); 