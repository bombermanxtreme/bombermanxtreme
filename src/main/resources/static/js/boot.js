// Nombre: Kevin S. Sanchez Prieto
// Boot: Inicio del juego (Prueba)[BETA]

// Objeto BomberCanvas
var Boot = Boot || {};

// Clase Boot (Atributos y funciones)
Boot = {
    preload: function (){
        musicaBtn = '', 
        BomberGame.load.image('fondoIni', 'media/ejemplo/fondoInicio.png');
        BomberGame.load.spritesheet('boton', 'media/ejemplo/botones.png', 180, 80);
        BomberGame.load.audio('musicaIni', 'media/audio/title.mp3');
        BomberGame.load.audio('clicBtn', 'media/audio/ping.mp3');
    },

    create: function (){
        BomberGame.add.sprite(0, 0, 'fondoIni');
        var musica =  BomberGame.add.audio('musicaIni');
        musica.play('', 0, 0.3, true); 
        this.musicaBtn = BomberGame.add.audio('clicBtn');
        
        BomberGame.add.button(400, 300, 'boton', this.iniciar , this, 2, 1, 0);
    },

    iniciar: function (){
        this.musicaBtn.play();
        console.log('Inicio Juego');
    }
}