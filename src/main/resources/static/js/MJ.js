/*
 --------------------------------------------
 FECHA DE MODIFICACION: 06 JUL 2015  - encargado de mostrar los mensajes en la pantalla todos empiezan por MJ_...
 --------------------------------------------
 
 este archivo necesita el siguiente código de html
 
 <div style="display:none;" id="div_aviso"><input type="hidden" value="" id="MJ_hidden"/></div>
 <div style="display:none;" id="div_aviso2"></div>
 */
var MJcolorBorde = "333333";
var MJcolorFondo = "EEEEEE";
var MJcolorSup = "444444";
var MJclassError = "input_error";
var MJclassCorrecto = "input_normal";
function MJ_input(q, id) {
    /*
     function MJ_input(bool q,str id) 
     se encarga de cambiar el color de los campos de pendiendo del estado que tenga q
     q: estado de error (false) o correcto (true) que tenga el campo id
     id: id del campo
     */
    document.getElementById(id).className = ((!q) ? MJclassError : MJclassCorrecto);
}

function MJ_iniciar(q) {
    document.getElementById('div_aviso').style.cursor = "";
    document.getElementById('div_aviso2').style.cursor = "";
    if (!q)
        setTimeout("Posicion(0);", 100);
    document.getElementById('MJ_hidden').value = "";
    document.getElementById("div_aviso").style.display = 'block';
    document.getElementById("div_aviso2").style.display = 'block';

}
function MJ_terminar(rta) {
    document.getElementById('MJ_hidden').value = rta;
    document.getElementById("div_aviso").style.display = 'none';
    document.getElementById("div_aviso2").style.display = 'none';
    document.getElementById('div_aviso2').innerHTML = '';
}
function MJ_simplex(titulo, mensaje, sinBotonX) {
    MJ_iniciar();
    var style = "";
    var t = '<input type="button" value="X" class="MJ_input_color" onclick="MJ_terminar();">';
    if (sinBotonX)
        t = '&nbsp;';
    document.getElementById('div_aviso2').innerHTML = '<table bgcolor="#' + MJcolorFondo + '" rules="none" bordercolor="#' + MJcolorBorde + '" style="' + style + '" border="2" align="center" cellpadding="0" cellspacing="0">  <tr bgcolor="#' + MJcolorSup + '"><td width="30">' + t + '</td><td align="center" class="MJ_text_mayor_white">' + titulo + '</td>  </tr>  <tr>    <td  colspan="2" align="center" class="MJ_text_normal"><br>' + mensaje + '</td>  </tr></table>';
}
function MJ_simple(titulo, mensaje) {
    MJ_iniciar();
    var style = "";
    document.getElementById('div_aviso2').innerHTML = '<table bgcolor="#' + MJcolorFondo + '" rules="none" bordercolor="#' + MJcolorBorde + '" style="' + style + '" border="2" align="center" cellpadding="0" cellspacing="0">  <tr>    <td align="center" bgcolor="#' + MJcolorSup + '" class="MJ_text_mayor_white">' + titulo + '</td>  </tr>  <tr>    <td align="center" class="MJ_text_normal"><br>' + mensaje + '<br><br><br></td>  </tr>  <tr>    <td align="left" class="MJ_text_mini_red">presione ENTER para continuar.</td>  </tr>  <tr>    <td align="center"><input type="button" onClick="MJ_terminar(\'aceptar\');" class="MJ_input_gris" id="aceptar_aviso"  value="Aceptar"/></td>  </tr></table>';
    if (val("dispositivo") !== "m")
        document.getElementById('aceptar_aviso').focus();
}
function MJ_sino(titulo, mensaje, funcionSI, funcionNO) {
    MJ_iniciar();
    var style = "";
    if (funcionNO === "") {
        funcionNO = "MJ_terminar(\'\')"
    }
    document.getElementById('div_aviso2').innerHTML = '<table bgcolor="#' + MJcolorFondo + '" rules="none" bordercolor="#' + MJcolorBorde + '" style="' + style + '" border="2" align="center" cellpadding="0" cellspacing="0">  <tr>    <td align="center" bgcolor="#' + MJcolorSup + '" class="MJ_text_mayor_white">' + titulo + '</td>  </tr>  <tr>    <td align="center" class="MJ_text_normal"><br>' + mensaje + '<br><br><br></td>  </tr>  <tr>    <td align="left" class="MJ_text_mini_red">presione ENTER para SI o TAB luego ENTER para NO</td>  </tr>  <tr>    <td align="center"><input type="button" onClick="' + funcionSI + 'MJ_terminar(\'\');" class="MJ_input_gris" id="aceptar_aviso"  value="SI"/> <input type="button" onClick="' + funcionNO + '" class="MJ_input_gris" value="NO"/></td>  </tr></table>';
    if (val("dispositivo") !== "m")
        document.getElementById('aceptar_aviso').focus();
}
function MJ_aceptar_cancelar(titulo, mensaje, funcionA, funcionC) {
    MJ_iniciar();

    var style = "";
    var funcionNO = funcionC;
    if (funcionC === "") {
        funcionNO = "MJ_terminar(\'\')"
    }
    document.getElementById('div_aviso2').innerHTML = '<table bgcolor="#' + MJcolorFondo + '" rules="none" bordercolor="#' + MJcolorBorde + '" style="' + style + '" border="2" align="center" cellpadding="0" cellspacing="0">  <tr>    <td align="center" bgcolor="#' + MJcolorSup + '" class="MJ_text_mayor_white">' + titulo + '</td>  </tr>  <tr>    <td align="center" class="MJ_text_normal"><br>' + mensaje + '<br><br><br></td>  </tr>  <tr>    <td align="left" class="MJ_text_mini_red">presione ENTER para Aceptar o TAB luego ENTER para Cancelar </td>  </tr>  <tr>    <td align="center"><input type="button" onClick="' + funcionA + '" class="MJ_input_gris" id="aceptar_aviso"  value="Aceptar"/> <input type="button" onClick="' + funcionNO + '" class="MJ_input_gris" value="Cancelar"/></td>  </tr></table>';
    if (val("dispositivo") !== "m")
        document.getElementById('aceptar_aviso').focus();
}
function MJ_load(func) {

    var style = "";
    if (func) {
        MJ_iniciar(1);
        document.getElementById('div_aviso').style.cursor = "wait";
        document.getElementById('div_aviso2').style.cursor = "wait";
        var tmpDomain = document.domain;
        document.getElementById('div_aviso2').innerHTML = '<table bgcolor="#' + MJcolorFondo + '" rules="none" bordercolor="#' + MJcolorBorde + '" style="' + style + '" border="2" align="center" cellpadding="0" cellspacing="0">  <tr>    <td align="center" bgcolor="#' + MJcolorSup + '" class="MJ_text_mayor_white">Espera por favor</td>  </tr>  <tr>    <td align="center" class="MJ_text_normal"><br><img src="/media/cargando.gif" class="MJ_loadImg"> Cargando ...</td>  </tr>  <tr>    <td align="left" class="MJ_text_mini_red">Espere por favor.</td>  </tr></table>';
    } else {
        MJ_terminar("cargado");
        document.getElementById('div_aviso').style.cursor = "";
        document.getElementById('div_aviso2').style.cursor = "";
    }
}
function MJ_frame(titulo, src, w, h) {
    var style = "";
    MJ_iniciar();
    document.getElementById('div_aviso2').innerHTML = '<table  bgcolor="#' + MJcolorFondo + '" rules="none" bordercolor="#' + MJcolorBorde + '"  style="' + style + '" border="1" cellpadding="0" cellspacing="0"><tr><td width="10"><input align="left" type="button" class="MJ_input_gris" value="X" title="Cerrar" onclick="MJ_terminar();" /></td><td align="center" bgcolor="#' + MJcolorSup + '" class="MJ_text_mayor_white">' + titulo + '</td></tr><tr><td colspan="2" bgcolor="#000000" ><iframe src="' + src + '" width="' + w + '" height="' + h + '" frameborder="0" framespacing="0" scrolling="auto" border="0" ></iframe></td></tr></table>';
}

/*---------------------------------*/
function MJ_propio(titulo, contenido) {

    var style = "";
    MJ_iniciar();
    document.getElementById('div_aviso2').innerHTML = '<table rules="none" bordercolor="#' + MJcolorBorde + '" style="' + style + '" border="2" align="center" cellpadding="0" cellspacing="0">  <tr>  <td width="20"><input align="left" type="button" class="MJ_input_gris" value="X" title="Cerrar" onclick="MJ_terminar();" /></td>  <td align="center" bgcolor="#' + MJcolorSup + '" class="MJ_text_mayor_white">' + titulo + '</td>  </tr>  <tr>    <td colspan="2" align="center" bgcolor="#000000">' + contenido + '</td>  </tr></table>';
}

function MJ_reTry(f) {
    if (document.getElementById("div_aviso") && document.getElementById("div_aviso2") && document.getElementById("div_aviso").style.display === 'block' && document.getElementById("div_aviso2").style.display === 'block')
        MJ_sino("Error de conexi&oacute;n", "Parece que no tiene conexi&oacute;n a internet<br><br>&iquest;Desea intentar nuevamente?", "setTimeout(function(){" + f + ";},1000);", "MJ_terminar('');");
    else if (confirm("Parece que no tiene conexion a internet. Desea intentar nuevamente?"))
        eval(f + ";");
}
function Posicion(selector) {
    if (isNaN(selector)) {
        var destination = $(selector + ':first').offset().top;
        $("html:not(:animated),body:not(:animated)").animate({
            scrollTop: destination - 100
        }, 1000);
    } else {
        $("html:not(:animated),body:not(:animated)").animate({
            scrollTop: selector
        }, 1000);
    }
}

function val(id, v) {
    if (v === null || v === undefined)
        return $("#" + id).val();
    else
        $("#" + id).val(v);
}
