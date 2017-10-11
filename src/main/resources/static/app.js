var app = (function () {

    class Point{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }
    
    var stompClient = null;

    var addPointToCanvas = function (point) {        
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.arc(point.x, point.y, 3, 0, 2 * Math.PI);
        ctx.stroke();
    };
    
    
    var getMousePosition = function (evt) {
        canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    };

    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
			console.log('Connected: ' + frame);
			var idCanvas= $("#id").val();
			if(idCanvas==""){//por defecto
				idCanvas="1";
				$("#id").val(idCanvas);
			}
			//var idCanvas = document.getElementById("id").value;
            stompClient.subscribe('/topic/newpolygon.'+idCanvas, function (eventbody) {
				callback_connectAndSubscribe(eventbody);
            });
        });

    };

	var callback_connectAndSubscribe=function(message) {
		var poligono=JSON.parse(message.body);
		console.log("se recibe el poligono:");
		console.log(poligono);
		var canvas=document.getElementById("canvas");
		var c2 = canvas.getContext('2d');
		c2.fillStyle = '#f00';
		c2.beginPath();
		c2.moveTo(poligono[0].x, poligono[0].y);
		for(var i=1; i<poligono.length; i++)
			c2.lineTo(poligono[i].x,poligono[i].y);
		c2.closePath();
		c2.fill();
	};

    return {

        init: function () {
            var can = document.getElementById("canvas");
            
            //websocket connection
            connectAndSubscribe();
        },

        publishPoint: function(px,py){
			var pt=new Point(px,py);
			var idCanvas = $("#id").val();
			stompClient.send("/app/newpoint."+idCanvas, {}, JSON.stringify(pt));
            console.info("publishing point at "+pt);
            addPointToCanvas(pt);

            //publicar el evento
        },

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }
    };

})();