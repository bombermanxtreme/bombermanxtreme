apiclientSala = (function () {
    return {
        getSalas: function (callback) {
            $.ajax({
                url: "/sala",
                type: "GET",
            }).done(function (data) {
                callback(data);
            }).fail(function (jqXHR, textStatus) {
                callback(undefined);
                if (jqXHR.status != 404)
                    alert("Error " + jqXHR.status + " peticion GET!");
            });
        },
        createSala: function (_nombre,_equipos,_fuegoamigo,callback) {
            $.ajax({
                url: "/sala",
				type: "POST",
                data: JSON.stringify({nombre:_nombre,equipos:_equipos,fuegoamigo:_fuegoamigo,id_jugador:appCookie.getIdJugador(false)}),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
            }).done(function (res) {
                callback(res);
            }).fail(function (jqXHR, textStatus) {
                callback(undefined);
                if (jqXHR.status != 404)
                    alert("Error " + jqXHR.status + " peticion GET!");
            });
        }
    }
})();