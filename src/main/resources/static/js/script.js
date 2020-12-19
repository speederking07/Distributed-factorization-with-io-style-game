r = 0;

class Demo{
    constructor(canvas){
        this.board = Array(100);
        for (let i = 0; i < 100; i++) {
            this.board[i] = Array(100);
            this.board[i].fill(BASE_PATTERN);
        }
        this.players = [];
        this.view = new BoardView(canvas, this.board, this.players);
        this.animator = new Animator(() => this.view.draw(0,0), FRAMES_PER_SECONDS);
        this.maxFrames = 60;
        this.frame = 0;
        this.key_frames = [];
        this.key_frames[0] = (() => {
            this.players.push(new Player(Color.fromHex('#ff0000'), "P1__T__P1", 8*40, -40, [[8*40, -40]], 0, 4));
            this.players.push(new Player(Color.fromHex('#00ff00'), "P2" ,13*40, 8*-40, [[13*40, 8*-40]], 0, 4));
            this.players.push(new Player(Color.fromHex('#0000ff'), "P3",  20*40, 16*-40, [[20*40, 16*-40]], 0, 4));
            this.players.push(new Player(Color.fromHex('#ffff00'), "P4",  40*40, 8*40, [[40*40, 8*40]], -4, 0));
            for (let p of this.players){
                p.drawPath();
            }
        }).bind(this);
        this.key_frames[11] = (() => {
            this.players[0].path.push([8*40, 10*40]);
            this.players[0].posX = 8*40;
            this.players[0].posY = 10*40;
            this.players[0].movX = -4;
            this.players[0].movY = 0;
        }).bind(this);
        this.key_frames[22] = (() => {
            let pattern = this.players[0].pattern;
            this.players.reverse();
            this.players.pop();
            this.players.reverse();
            for (let x = 0; x <= 9; x++) {
                for (let y = 0; y <= 11; y++) {
                    this.board[x][y] = pattern;
                    this.view.changeField(x, y, BASE_PATTERN, pattern);
                }
            }
        }).bind(this);
        this.key_frames[23] = (() => {
            this.view.killPlayer(this.players[2]);
            this.players.pop();
        }).bind(this);
        this.key_frames[25] = (() => {
            this.players[0].path.push([13*40, 17*40]);
            this.players[0].posX = 13*40;
            this.players[0].posY = 17*40;
            this.players[0].movX = -4;
            this.players[0].movY = 0;
        }).bind(this);
        this.key_frames[39] = (() => {
            this.view.killPlayer(this.players[1]);
            this.players.pop();
        }).bind(this);
        this.key_frames[44] = (() => {
            let pattern = this.players[0].pattern;
            this.players.reverse();
            this.players.pop();
            this.players.reverse();
            for (let x = 0; x <= 13; x++) {
                for (let y = 0; y <= 17; y++) {
                    this.board[x][y] = pattern;
                    this.view.changeField(x, y, BASE_PATTERN, pattern);
                }
            }
        }).bind(this);
        this.key_frames[52] = (() => {
            for (let x = 0; x <= 13; x++) {
                for (let y = 0; y <= 17; y++) {
                    this.view.changeField(x, y, this.board[x][y], BASE_PATTERN);
                    this.board[x][y] = BASE_PATTERN;
                }
            }
        }).bind(this);
    }

    start(){
        this.animator.start();
        this.moveInterval = setInterval((() => {
            if(document.hasFocus()){
                for (let p of this.players) {
                    p.move();
                }
            }
        }).bind(this), 40);
        this.framesInterval = setInterval((() => {
            if(document.hasFocus()) {
                if (this.key_frames[this.frame] !== undefined) {
                    this.key_frames[this.frame]();
                }
                this.frame += 1;
                if (this.frame > this.maxFrames) {
                    this.frame = 0;
                }
            }
        }).bind(this), 400);
    }

    kill(){
        clearInterval(this.moveInterval);
        clearInterval(this.framesInterval);
        this.animator.kill()
    }
}

let demo;

$(document).ready(function () {
    $(window).trigger('resize');
    demo = new Demo(document.getElementById('board'));
    demo.start();
    //demo = new Game(new FakeConnection(), document.getElementById('board'), "me");
});

$(window).resize(function () {
    $('#board').attr('width', $(window).width());
    $('#board').attr('height', $(window).height());
});

function closePopup() {
    $('.dialogBox').remove();
}