/**
 * clase que gestiona los archivos de css que se quieren importar calculando la pantalla y trayendo sólo los estilos necesarios
 */
var pantalla=(function(){
	var TAM_PANTALLA="mobile";
	var dispositivo_="";

	return {
		/**
		 * retorna que dispositivo es m o pc si ya se ejecutó start
		 */
		getDispositivo:function(){
			return dispositivo_;
		},
		/**
		 * función que calcula la pantalla y usa eso para traer el css correcto para determinada pantalla de los css establecidos en el html que tengan clase procesarCSS
		 */
		start:function(){
			//sólo se debe ejecutar una vez
			if(dispositivo_!="")return false;
			//agregamos div para poder medir la resolución de pantalla
			$("body").append('<div id="medidaParaMovile" style="height:0px;width: 8cm;"></div>');
			//si no cabe el doble es un dispositivo muy pequeño (celular)
			dispositivo_=(window.innerWidth>2*$("#medidaParaMovile").width())?"pc":"m";
			//agregamos que no se pueda hacer zoom si es mobile
			document.getElementById("viewport").setAttribute("content", (dispositivo_!="m")?"initial-scale=1, minimum-scale=1":"user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1");

			//para el css de pc discriminamos cada pantalla 
			if(dispositivo_!="m"){
				var pantalla=2000;
				if(screen.width<1400)pantalla=1400;
				else if(screen.width<1200)pantalla=1200;
				else if(screen.width<800)pantalla=800;
				TAM_PANTALLA=pantalla;
			}
			$(".procesarCSS").each(function(){
				$(this).attr("href",$(this).attr("data-url")+"_"+pantalla+".css");
			});
			//eliminamos el div que se creó para calcular los cm de la pantalla
			$("#medidaParaMovile").remove();
		}
	}
})();

setTimeout(pantalla.start,100);