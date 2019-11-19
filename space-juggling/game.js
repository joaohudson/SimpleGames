/**
 * Author: João Huson
 * Version: 1.0
 */

class GameObject
{
	constructor(x, y, width, height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//Chamado repetidamente a cada loop do jogo.
	update(objects)
	{
		objects.forEach((object) => {
			if(this.colision(object))
				this.colisioned(object);
		});
	}

	//Informa se colidiu com o dado objeto.
	colision(object)
	{
		if(object == this)
			return false;

		const mx = object.x + object.width;
		const my = object.y + object.height;
		const nx = this.x + this.width;
		const ny = this.y + this.height;

		return mx > this.x && nx > object.x && my > this.y && ny > object.y
	}

	//Chamado quando colidir com o dado objeto.
	colisioned(object){}
}

class Ball extends GameObject
{
	constructor(x, y, size, color)
	{
		super(x, y, size, size);
		this.dx = (Math.random() % 2) == 0 ? 2 : -2;
		this.dy = (Math.random() % 2) == 0 ? 2 : -2;
		this.color = color;
	}
	
	//Desenha a bola.
	draw(g)
	{
		g.save();
		g.beginPath();
			g.translate(this.x, this.y);
			g.fillStyle = this.color;
			g.arc(0, 0, this.width, 0, 360);
			g.fill();
		g.closePath();
		g.restore();
	}

	update(objects)
	{
		if(this.x > 300 || this.x < 0)
			this.dx = -this.dx;

		if(this.y > 150 || this.y < 0)
			this.dy = -this.dy;

		if(this.y > 150)
			showResult(false);

		this.x += this.dx;
		this.y += this.dy;

		super.update(objects);
	}

	colisioned(object){}
}

class Player extends GameObject
{
	constructor(x, y, size)
	{
		super(x, y, size, size);
		this.leftPressed = false;
		this.rightPressed = false;
		this.dx = 7;
		this.dy = 0;
		this.img = new Image();
		this.img.src = 'player.png';

		//Configura os controles:

		document.addEventListener('keydown', (e) => {
			if(e.key == 'ArrowLeft' || e.key == 'a')
				this.leftPressed = true;

			if(e.key == 'ArrowRight' || e.key == 'd')
				this.rightPressed = true;
		});

		document.addEventListener('keyup', (e) => {
			if(e.key == 'ArrowLeft' || e.key == 'a')
				this.leftPressed = false;

			if(e.key == 'ArrowRight' || e.key == 'd')
				this.rightPressed = false;
		});
	}

	//Desenha o jogador.
	draw(g)
	{
		g.save();
		g.beginPath();
			g.translate(this.x, this.y);
			g.scale(0.25, 0.25);
			g.fillStyle = 'white';
			g.drawImage(this.img, 0, 0);
			g.fill();
		g.closePath();
		g.restore();
	}

	update(objects)
	{
		if(this.rightPressed)
			this.x += this.dx;

		if(this.leftPressed)
			this.x += -this.dx;

		if(this.x > 250)
			this.x = 250;

		if(this.x < 0)
			this.x = 0;

		super.update(objects);
	}

	//Rebate as bolas quando colidir.
	colisioned(object)
	{
		object.dy = object.dy < 0 ? object.dy : -object.dy
	}
}

class Star
{
	constructor(x,y)
	{
		this.x = x;
		this.y = y;
		this.visible = Math.round(Math.random()) == 0;
		this.timer;

		this.timer = setInterval(() => this.visible = !this.visible, 200);
	}

	//Desenha a estrela.
	draw(g)
	{
		if(!this.visible)
			return;

		g.save();
		g.beginPath();
			g.translate(this.x, this.y);
			g.fillStyle = 'white';
			g.arc(0, 0, 1, 0, 360);
			g.fill();
		g.closePath();
		g.restore();
	}

	//Descarta a estrela.
	dispose()
	{
		clearInterval(this.timer);
	}
}

class Map
{
	constructor(width, height)
	{
		this.width = width;
		this.height = height;
		this.stars = [];

		for(let i = 0; i < 50; i++)
		{
			const x =  (Math.random() * 300) % 300;
			const y = (Math.random() * 150) % 150;

			this.stars[i] = new Star(x, y);
		}
	}

	//Desenha o mapa.
	draw(g)
	{
		this.stars.forEach((star) => star.draw(g));
	}

	//Descarta o mapa.
	dispose()
	{
		this.stars.forEach((star) => star.dispose());
	}
}

//Variáveis globais:
var canvas = document.querySelector('#canvas');
var display = document.querySelector('#display');
var context = canvas.getContext('2d');
var timerloop, timerdisplay;
var seconds = 60;
var drawables = [new Ball(50, 50, 5, 'orange'), new Ball(0, 0, 5, 'red'), new Ball(200, 0, 5, 'yellow'), new Player(100, 130, 50)];
var map = new Map();

//Exibe a tela de ínício:
function showInitScreen()
{
	const img = new Image();
	const title = new Image();
	img.src = 'player.png';
	title.src = 'title.png';

	title.onload = () => {
		//Desenha o título:
		context.save();
		context.beginPath();
			context.translate(35, 45);
			context.scale(0.25, 0.25);
			context.drawImage(title, 0, 0);
			context.fill();
		context.closePath();
		context.restore();
		//Desenha a nave:
		context.save();
		context.beginPath();
			context.translate(40, 52);
			context.rotate(-0.161799);
			context.scale(0.22, 0.22);
			context.fillStyle = 'white';
			context.drawImage(img, 0, 0);
			context.fill();
		context.closePath();
		context.restore();
	}
}

//Exibe o resultado da partida:
function showResult(result)
{
	clearInterval(timerloop);
	clearInterval(timerdisplay);
	map.dispose();

	let f = () => {
		context.clearRect(0, 0, 300, 150);
		context.resetTransform();
		
		context.save();
		context.beginPath();
			context.translate(130, 75);
			context.fillStyle = result ? 'green' : 'red';
			context.fillText(result ? 'WIN' : 'LOSE', 0, 0);
			context.save();
				context.translate(-30, 15);
				context.fillText('press key to continue', 0, 0);
			context.restore();
			context.fill();
		context.closePath();
		context.restore();

		document.onkeydown = () => window.location.reload();
	}

	setTimeout(f, 1000);
}

//Exibe o display do jogo:
function showDisplay()
{
	display.innerHTML = 'Time: ' + seconds;

	if(seconds == 0)
		showResult(true);

	seconds--;
}

//Desenha o jogo:
function draw()
{
	context.clearRect(0, 0, 300, 150);//canvas.clientHeight, canvas.clientWidth);
	context.resetTransform();
	map.draw(context);
	drawables.forEach((d) => d.draw(context));
}

//Atualia o jogo:
function update()
{
	drawables.forEach((o) => o.update(drawables));
}

//Loop do jogo:
function loop()
{
	const func = () =>{
		update();
		draw();
	}

	timerloop = setInterval(func, 30);
}

//Ao carregar, exibe a tela inicial:
window.onload = showInitScreen;

//Ao clicar no display, inicia o jogo:
display.onclick  = () => {
	
	loop();

	display.innerHTML = 'Time: ' + seconds;
	display.style.cursor = 'default';

	timerdisplay = setInterval(() => {
		showDisplay();
	}, 1000);

	display.onclick = null;
};