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
        createSala: function (nombre,equipos,fuegoamigo,callback) {
            $.ajax({
                url: "/sala",
				type: "POST",
				data: "nombre="+nombre +"&equipos="+equipos+"&fuegoamigo="+fuegoamigo
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