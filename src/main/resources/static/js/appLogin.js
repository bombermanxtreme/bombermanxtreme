//var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

var appLogin = (function () {



    return {

        login: function () {


            var correo = $("#nombreusuario").val();
            var clave = $("#contrasena").val();

            var datosInicio = {correo: correo, clave: clave};

            console.info("datos de inicio: " + correo);

            $.get("/users/" + correo + "/" + clave,
                    function (data) {
                        console.info("sesion: ");                      
                        document.cookie = "iduser="+data;
                        location.href="/jugar.html";
                    }
            ).fail(
                    function (data) {
                        alert("User: " + correo + " no existe " + data["responseText"]);
                    }

            );
        }
    };
})();