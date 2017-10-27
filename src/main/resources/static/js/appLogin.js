//var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

var appLogin = (function () {



    return {

        login: function () {


            var correo = $("#form-username").val();
            var clave = $("#form-password").val();

            var datosInicio = {correo: correo}

            console.info("datos de inicio: " + datosInicio);


            if (correo == "" || clave == "") {
                alert("Uno o varios campos estan sin llenar !!! , completa el para poder iniciar sesion");
            } else {
                $.get("/users/" + correo + "/" + clave,
                        function (data) {
                            console.info("sesion: " + datosInicio);
                            document.cookie = "iduser=" + data;
                            location.href = "/jugar.html";
                        }
                ).fail(
                        function (data) {
                            alert("User: " + correo + " no existe " + data["responseText"]);
                        }

                );
            }
        },

        registrer: function () {

            var nombre = $("#nnombre").val();
            var apodo = $("#napodo").val();
            var iurl = "url_imagen"; // aun no esta el campo en el html para cargar la imagen
            var correo = $("#ncorreo").val();
            var clave = $("nclave").val();
            var nclave = $("nrclave").val();

            var datosNuevos = {nomre: nombre, apodo: apodo, correo: correo};
            //var datosNuevos = {nomre: nombre, apodo: apodo, correo: correo, clave: clave, imagen:iurl};

            console.info("datos de registro: " + datosNuevos.nomre+ " " +datosNuevos.apodo+" "+datosNuevos.correo);


            if (nombre == "" || apodo == "" || correo == "" || clave == "" || nclave == "") {
                alert("Uno o varios campos estan sin llenar !!! , completalo formulario de registro");
            } else {

                $.get("/users/new/" + nombre + "/" + apodo + "/" + correo + "/" + clave + "/" + nclave + "/" + iurl,
                        function (data) {
                            console.info("registro: " + datosNuevos.correo + " " + datosNuevos.apodo + "  " + " * Response: ");
                            //document.cookie = "iduser=" + data;
                            //location.href = "/login.html";
                        }
                ).fail(
                        function (data) {
                            if (data == -2) {
                                console.info("No se puede crear el usuario " + datosNuevos.correo + " * Response: El usuario ya extiste. Codigo =" + data);
                                alert("El usario " + correo + " ya existe!, usa una dirección de correo diferente");
                            } else {
                                console.info("No se puede crear el usuario " + datosNuevos.correo + " * Response: ");
                                alert("El usario " + correo + " no se puede crear: Response: " + data["responseText"]);
                            }
                        }

                );

            }

        },

        personaje_config: function () {
            console.info("configurar personaje  \U/ \U/ \U/ \U/ ");
            location.href = "http://tycho.escuelaing.edu.co/Eciciencia/GrupoInscrito?grupo=null&idconcurso=55&carne=2095112&secuencia=0&participante=EE&tipoconcurso=0&documento=1016046080&tip_doc=C&nombre=manuel&apellido1=perez&apellido2=espitia&mail=dark07perez%40gmail.com&telefono=3134479638&celular=3134479638&direccion=calle+96B+No+16H-70&ciudad=11001&carrera=INGENIERIA+DE+SISTEMAS+&semestre=9&participo=si";

        }





    };


})();