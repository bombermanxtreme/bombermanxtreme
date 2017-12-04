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
				$("#lista_salas").html("no se han encontrado salas disponibles");
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
		 * muestra formulario para crear sala nueva
		 */
		crearSala(){
			disconnect();
			$("#antesDeEmpezar").html("<div id='titulo_salas'>Crear Sala Nueva</div><div id='formulario'>Nombre:<input type='text' placeholder='Nombre de Sala' id='nombre'><br>Equipos <input type='checkbox' id='equipos'><br>Fuego Amigo <input type='checkbox' id='fuegoamigo'><br><input type='button' onclick='appSala.crearSalaForm();' value='Crear'>");
		},
		/**
		 * envia crear sala nueva
		 */
		crearSalaForm(){
			//verificamos los datos primero
			var nombre=$("#nombre").val();
			var equipos=$("#equipos").is(':checked');
			var fuegoamigo=$("#fuegoamigo").is(':checked');
			if (nombre=="") {
				MJ_simple("Crear Sala","El nombre no puede ser vacío");
				return false;
			}
			//enviamos los datos
			APIuseful.createSala(nombre,equipos,fuegoamigo);
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