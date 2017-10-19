var app = (function () {

	var stompClient = null;

	var connectAndSubscribe = function () {
		console.info('Connecting to WS...');
		var socket = new SockJS('/stompendpoint');
		stompClient = Stomp.over(socket);
		
		//subscribe to /topic/TOPICXX when connections succeed
		stompClient.connect({}, function (frame) {
			console.log('Connected: ' + frame);
			var idCanvas= "";
			if(idCanvas==""){//por defecto
				idCanvas="1";
				//$("#id").val(idCanvas);
			}
			//var idCanvas = document.getElementById("id").value;
			stompClient.subscribe('/topic/estadisticas.'+idCanvas, function (eventbody) {
				callback_connectAndSubscribe(eventbody);
			});
		});

	};

	var callback_connectAndSubscribe=function(message) {
		var estadisticas=message.body;//JSON.parse(message.body);
		console.log("se recibe estadisticas:");
		console.log(estadisticas);
	};

	return {

		init: function () {
			//websocket connection
			connectAndSubscribe();
		},

		iniciarJuego: function(){
			stompClient.send("/app/estadisticas.1", {}, 1212);//JSON.stringify({hola:1})
			console.info("enviando... ");
		},

		disconnect: function () {
			if (stompClient !== null) {
				stompClient.disconnect();
			}
			//setConnected(false);
			console.log("Disconnected");
		}
	};
})();