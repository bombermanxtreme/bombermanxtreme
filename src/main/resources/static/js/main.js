var BomberGame = new Phaser.Game(1000, 500, Phaser.AUTO, 'contenedor');

BomberGame.state.add('Boot', Boot);	
BomberGame.state.start('Boot');