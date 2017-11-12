//var APIuseful = apimockJugar;
//var APIuseful=apiclientJugar;

var appLogin = (function () {



    return {

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
                            console.info("sesion: " + datosInicio.correo +" id user: "+data);
                            document.cookie = "iduser=" + data;
                            location.href = "/jugar.html";
                            
                        }
                ).fail(
                        function (data) {
                            console.info("Response text: "+data.responseText);
                            if(data.responseText==="-1") {
                                console.info("User: " + correo + " no existe " + data.responseText);}
                            else if(data.responseText==="-2") {
                                console.log("User: " + correo + " contraseña incorrecta, cod: " + data.responseText); 
                                alert("Contraseña Incorrecta :(");
                            }
                            else {
                                console.log("error desconocido: "+data.responseText);                                
                            }

                        }

                );
               
               
            }
        },

        registrer: function () {

            var nombre = $("#nnombre").val();
            var apodo = $("#napodo").val();
            var iurl = "url_imagen"; // aun no esta el campo en el html para cargar la imagen
            var correo = $("#ncorreo").val();
            var clave = $("#nclave").val();            
            var nclave = $("#nrclave").val();

            var datosNuevos = {nomre: nombre, apodo: apodo, correo: correo};
            //var datosNuevos = {nomre: nombre, apodo: apodo, correo: correo, clave: clave, imagen:iurl};

            console.info("datos de registro para empezar el registro: " + datosNuevos.nomre+ " " +datosNuevos.apodo+" "+datosNuevos.correo);


            if (nombre === "" || apodo === "" || correo === "" || clave === "" || nclave === "") {
                alert("Uno o varios campos estan sin llenar !!! , completa el formulario de registro");
            
            } else if(clave !== nclave) {
                alert("La contraseña no coincide.");
                
            }else if(!correo.includes("@")){
                alert("El correo no es una dirección de correo valida.");
                
            }else if(nombre.length <5 ){
                alert("Nombre muy corto, minimo 5 caracteres.");
                
            }else if(clave.length < 3){
                alert("Contraseña muy corta, minimo 3 caracteres.");
            
            }else if(apodo.length < 3){
                alert("Nombre muy corto, minimo 3 caracteres.");
                
            }else {
                $.get("/users/new/" + nombre + "/" + correo + "/" + apodo + "/" + clave + "/" + iurl,
                        function (data) {
                            console.info("registro: " + datosNuevos.correo + " " + datosNuevos.apodo + "  " + " id user: " + data);
                            document.cookie = "iduser=" + data;
                            alert("Bienvenido "+datosNuevos.nomre);
                            location.href = "/login.html";
                        }
                ).fail(
                        function (data) {
                            console.info("Response text: "+data.responseText);
                            if (data.responseText === "-2") {
                                console.log("No se puede crear el usuario " + datosNuevos.correo + "El usuario ya extiste. Codigo =" + data.responseText);
                                alert("El usario " + datosNuevos.correo + " ya existe!, usa una dirección de correo diferente.");
                            }else if(data.responseText === "-3"){
                                console.log("No se puede crear el usuario " + datosNuevos.apodo + "El apodo ya extiste. Codigo =" + data.responseText);
                                alert("El apodo " + datosNuevos.apodo + " ya existe!, usa un apodo diferente.");                                
                            }else {
                                console.log("Error desconodido:" + data.responseText);                                
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