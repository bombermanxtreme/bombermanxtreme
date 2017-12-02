//var APIuseful = apimockJugar;
var APIuseful=apiclientSala;

var appSala=(function(){
	var stompClient = null;
	var imgCargando = "<img src='/media/cargando.gif' class='imgCargando'>";

	/**
	 * desconecta del STOMP
	 */
	var disconnect=function() {
		if (stompClient!==null) {
			stompClient.disconnect();
		}
		//setConnected(false);
		console.log("Desconectado");
	}
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
		$("#antesDeEmpezar").html("<div id='titulo_salas'>Salas De Juego</div><input type='button' onclick='appSala.crearSala();' value='Crear Nueva Sala'><div id='lista_salas'>Cargando Salas..." + imgCargando+"</div>");
		APIuseful.getSalas(function (data) {
			var J=eval("("+data+")");
			console.info("data: ");
			console.info(J);
			if(J.length>0){
				$("#lista_salas").html(" ");
				for (let i=0; i<J.length;i++){
					$("#lista_salas").append("<div onclick='appSala.entrarASala("+J[i].id+");'>"+J[i].nombre+" - "+J[i].numJugadores+" jugadores</div>");
				}
			}else{
				console.log("no se han encontrado salas disponibles");
			}
		});
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
			//INICIAMOS CONEXIÓN
			connectAndSubscribe();
			getSalas();
		},
		/**
		 * permite crear sala nueva
		 */
		crearSala(){
			disconnect();
		},
		/**
		 * entra a la sala y empezamos a ejecutar appJugar
		 */
		entrarASala(id){
			disconnect();
			appCookie.setSala(id);
			appJugar.init();
		}
	};
})();