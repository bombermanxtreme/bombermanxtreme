//var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

var appLogin = (function () {
    
    
    return {

        avatarLoad: function () {
            var correo_login = $("#correo").val();
            if (correo_login !== "") {
                $.get("/users/avatar/" + correo_login+"/view",
                        function (data) {
                            console.info("carangando imagen url: " + data);
                            $("#avatar-load").attr("src", data);
                            $("#avatar-load").attr("alt", correo_login);
                        }
                ).fail(
                        function (data) {
                            $("#avatar-load").attr("src", "https://www.drupal.org/files/issues/default-avatar.png");
                            $("#avatar-load").attr("alt", "Avatar");
                            console.info("Error: No es posible cargar la imagen: " + data.responseText);
                        }
                );
            }
        },
        
        avatarLoad_temp: function () {
            var new_iurl = $("#niurl").val();
            
            console.info("carangando imagen url: " + new_iurl);
            $("#avatar-load").attr("src", new_iurl);
            $("#avatar-load").attr("alt", new_iurl);
           
        },

        login: function () {
            var correo = $("#correo").val();
            var clave = $("#clave").val();

            var datosInicio = {correo: correo};

            console.info("datos de inicio: " + datosInicio.correo);


            if (correo === "" || clave === "") {
                alert("Uno o varios campos estan sin llenar. Completa los campos para poder continuar.");
            } else {

                $.get("/users/" + correo + "/" + clave,
                        function (data) {
                            console.info("sesion: " + datosInicio.correo + " id user: " + data);
                            document.cookie = "iduser=" + data;
                            location.href = "/jugar.html";

                        }
                ).fail(
                        function (data) {
                            console.info("Response text: " + data.responseText);
                            if (data.responseText === "-1") {
                                console.info("User: " + correo + " no existe " + data.responseText);
                            } else if (data.responseText === "-2") {
                                console.log("User: " + correo + " contraseña incorrecta, cod: " + data.responseText);
                                alert("Contraseña Incorrecta :(");
                            } else {
                                console.log("error desconocido: " + data.responseText);
                            }

                        }

                );


            }
        },

        registrer: function () {

            var nombre = $("#nnombre").val();
            var apodo = $("#napodo").val();
            var iurl = "https://www.drupal.org/files/issues/default-avatar.png"; 
            var correo = $("#ncorreo").val();
            var clave = $("#nclave").val();
            var nclave = $("#nrclave").val();

            var datosNuevos = {nomre: nombre, apodo: apodo, correo: correo, clave: clave, imagen:iurl};

            console.info("datos de registro para empezar el registro: " + datosNuevos.nomre + " " + datosNuevos.apodo + " " + datosNuevos.correo);


            if (nombre === "" || apodo === "" || correo === "" || clave === "" || nclave === "") {
                alert("Uno o varios campos estan sin llenar !!! , completa el formulario de registro");

            } else if (clave !== nclave) {
                alert("La contraseña no coincide.");

            } else if (!correo.includes("@")) {
                alert("El correo no es una dirección de correo valida.");

            } else if (nombre.length < 5) {
                alert("Nombre muy corto, minimo 5 caracteres.");

            } else if (clave.length < 3) {
                alert("Contraseña muy corta, minimo 3 caracteres.");

            } else if (apodo.length < 3) {
                alert("Nombre muy corto, minimo 3 caracteres.");

            } else {
                $.get("/users/new/" + datosNuevos.nombre + "/" + datosNuevos.correo + "/" + datosNuevos.apodo + "/" + datosNuevos.clave + "/" + datosNuevos.iurl+"/",
                        function (data) {
                            console.info("registro: " + datosNuevos.correo + " " + datosNuevos.apodo + "  " + " id user: " + data);
                            document.cookie = "iduser=" + data;
                            alert("Bienvenido " + datosNuevos.nomre);
                            location.href = "/login.html";
                        }
                ).fail(
                        function (data) {
                            console.info("Response text: " + data.responseText);
                            if (data.responseText === "-2") {
                                console.log("No se puede crear el usuario " + datosNuevos.correo + "El usuario ya extiste. Codigo =" + data.responseText);
                                alert("El usario " + datosNuevos.correo + " ya existe!, usa una dirección de correo diferente.");
                            } else if (data.responseText === "-3") {
                                console.log("No se puede crear el usuario " + datosNuevos.apodo + "El apodo ya extiste. Codigo =" + data.responseText);
                                alert("El apodo " + datosNuevos.apodo + " ya existe!, usa un apodo diferente.");
                            } else {
                                console.log("Error desconodido:" + data.responseText);
                            }
                        }

                );

            }

        }
        
    };

})();