//var APIuseful = apimockJugar;
var APIuseful=apiclientSala;

var appSala=(function(){
	var stompClient = null;
	var imgCargando = "<img src='/media/cargando.gif' class='imgCargando'>";
        var idJugador = -1;
        
	/**
	 * desconecta del STOMP
	 */
	var disconnect=function() {
		if (stompClient!==null) {
			stompClient.disconnect();
		}
		//setConnected(false);
		console.log("Desconectado");
	};
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
			stompClient.subscribe("/topic/Salas", function (eventbody) {
				callback_getSalas(eventbody.body);
			});
			/*
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
	 * alista todo para iniciar a pedir las salas cuando recién entra
	 */
	var getSalas=function(){
		$("#antesDeEmpezar").html("<div id='titulo_salas'>Salas De Juego</div><input type='button' onclick='appSala.crearSala();' value='Crear Nueva Sala'><div id='lista_salas'>Cargando Salas..." + imgCargando+"</div>");
		APIuseful.getSalas(callback_getSalas);
	};
	
	/**
	 * responde ante el listado de salas obtenido mostrando el listado
	 * @param {*} data 
	 */
	var callback_getSalas=function(data){
		var J=eval("("+data+")");
		if(J.length>0){
			$("#lista_salas").html(" ");
			for (let i=0; i<J.length;i++){
				if(J[i].casiLista=="true")
					continue;
				var strEquipos=J[i].equipos=="true"?"- Equipos"+(J[i].friendFire=="true"?" - Friend Fire":""):"";
				$("#lista_salas").append("<div onclick='appSala.entrarASala("+J[i].id+");'><span class='nombre_sala'>"+J[i].nombre+"</span><br><br> "+J[i].numJugadores+" jugadores "+strEquipos+"<br><div class='creador_sala'>Creador: "+J[i].creador+"</div></div>");
			}
		}else{
			$("#lista_salas").html("no se han encontrado salas disponibles");
		}
	};

	return {
		/**
		 * encargado de realizar la conexión con STOMP
		 */
		init() {
			//verificamos que el usuario haya iniciado
			idJugador=appCookie.getIdJugador(false);
                        $("header .nombre_jugador").text(appCookie.getNombreJugador());
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
			$("#antesDeEmpezar").html("<div id='titulo_salas'><input type='button' onclick='location.href=\"\";' value='Regresar a las Salas'> - Crear Sala Nueva</div><div id='formulario'>Nombre:<input type='text' placeholder='Nombre de Sala' id='nombre'><br><br><b>Características</b><br><label for='equipos'>Equipos</label> <input type='checkbox' id='equipos'><br><label for='fuegoamigo'>Fuego Amigo</label> <input type='checkbox' id='fuegoamigo'><br><br><input type='button' onclick='appSala.crearSalaForm();' value='Crear'>");
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
			MJ_load(true);
			//enviamos los datos
			APIuseful.createSala(nombre,equipos,fuegoamigo,function(data) {
				MJ_load(false);
				console.log(data);
				if(isNaN(data)){
					MJ_simple("Crear Sala","error:<br>"+data);
					return false;
				}
				appSala.entrarASala(data);
			});
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