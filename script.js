const BLOCK_SIZE = 40;
const PLAYER_RADIUS = 30;

class BoardView{
    constructor(canvas, borad, players){
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.boradSize = 100;
        this.borad = borad;
        this.players = players;
    }

    draw(x, y){
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.ctx.clearRect(0, 0, this.width, this.height)
        this.drawBorad(x, y);
        for(let p of this.players){
            p.draw(this.ctx, x, y, this.width, this.height);
        }
    }

    drawBorad(viewX, viewY){
        var row = Math.ceil(this.height/BLOCK_SIZE) + 1;
        var col = Math.ceil(this.width/BLOCK_SIZE) + 1;
        var arrayX = Math.floor(viewX/BLOCK_SIZE);
        var arrayY = Math.floor(viewY/BLOCK_SIZE);
        for(let x = Math.max(0, -arrayX); x <= Math.min(col, this.boradSize-arrayX-1); x++){
            for(let y = Math.max(0, -arrayY); y <= Math.min(row, this.boradSize-arrayY-1); y++){
                if(this.borad[x+arrayX][y+arrayY] === undefined){

                } else {
                    this.ctx.fillStyle = 'rgb(0, 0, 0)';
                    this.ctx.fillRect(-mod(viewX,BLOCK_SIZE) + x*BLOCK_SIZE + 1, -mod(viewY,BLOCK_SIZE) + y*BLOCK_SIZE +1, BLOCK_SIZE-2, BLOCK_SIZE-2);
                }
            }
        }
    }
}

class Player{
    constructor(color, posX, posY, path, movX, movY){
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.path = path;
        this.movX = movX;
        this.movY = movY;
    }

    draw(ctx, viewX, viewY, viewW, viewH){
        const C = PLAYER_RADIUS
        if(this.posX >= viewX-C && this.posX <= viewX+viewW+C && this.posY >= viewY-C && this.posY <= viewY+viewH+C)
        {
            ctx.beginPath();
            ctx.fillStyle = this.color;
            ctx.arc(this.posX+ BLOCK_SIZE/2 - viewX,this.posY+ BLOCK_SIZE/2 - viewY,30,0,2*Math.PI);
            ctx.fill();
        }
        let drawing = false;
        let prev = [this.posX, this.posY]
        ctx.lineWidth = 20;
        ctx.lineCap = "round";
        ctx.lineJoin = "round";
        ctx.strokeStyle = this.color;
        //ctx.beginPath();        
        //ctx.moveTo(this.posX + BLOCK_SIZE/2 - viewX, this.posY + BLOCK_SIZE/2 - viewY);
        for(let i = this.path.length - 1; i >= 0; i--){
            if (drawing){
                if(this.visable(this.path[i][0], this.path[i][1], prev[1], prev[1], viewX-C, viewY-C, viewW+2*C, viewH+2*C)){
                    ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX, this.path[i][1] + BLOCK_SIZE/2 - viewY);
                } else{
                    ctx.stroke();
                    drawing = false;
                }
            } else {
                if(this.visable(this.path[i][0], this.path[i][1], prev[1], prev[1], viewX-C, viewY-C, viewW+2*C, viewH+2*C)){
                    ctx.beginPath();        
                    ctx.moveTo(prev[0] + BLOCK_SIZE/2 - viewX, prev[1] + BLOCK_SIZE/2 - viewY);
                    ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX, this.path[i][1] + BLOCK_SIZE/2 - viewY);
                    drawing = true;
                }
            }
            //ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX, this.path[i][1] + BLOCK_SIZE/2 - viewY);
            prev = this.path[i];
        }
        if (drawing) ctx.stroke();      
    }

    visable(x1,y1,x2,y2,vX,vY,vW,vH){
        if(x1 > x2) x2 = [x1, x1 = x2][0];
        if(y1 > y2) y2 = [y1, y1 = y2][0];
        if(x1 > vX + vW || x2 < vX|| y1 > vY + vH || y2 < vY) return false
        else return true
    }

    move(){
        this.posX += this.movX;
        this.posY += this.movY;
    }
}

function rect(ctx, x1, y1, x2, y2){
    let x = Math.min(x1, x2);
    let w = Math.abs(x1 - x2);
    let y = Math.min(y1, y2);
    let h = Math.abs(y1 - y2);
    ctx.fillRect(x, y, w, h);
}

function mod(x, n) {
    return ((x%n)+n)%n
}

let view = undefined;
let XX = -200;
let YY = -200;
let borad = Array(100).fill(Array(100).fill(1));
let players = [ new Player("rgb(255,0,0)", 320, 40, [[0, 4000], [0, 600],[120, 600], [120, 40]], 4, 0),
                new Player("rgb(0,0,255)", 400, 360, [[5000, 400], [400, 400]], 0, -4),
                new Player("rgb(0,255,0)", 320, 360, [[160, 4000], [160, 320], [320, 320]], 0, 4)];

$(document).ready(function(){
    view = new BoardView(document.getElementById('board'), borad, players);    
    setInterval(function(){
        window.requestAnimationFrame(function(){
            view.draw(XX, YY);
            XX++;
            YY++;
        });
    }, 40);

    setInterval(function(){
        for(let p of players){
            p.move();
        }
    }, 40);
    $(window).trigger('resize');
});

$(window).resize(function(e) {
    $('#board').attr('width', $(window).width());
    $('#board').attr('height', $(window).height());
})
