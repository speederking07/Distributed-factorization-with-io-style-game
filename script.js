const BLOCK_SIZE = 40;
const PLAYER_RADIUS = 26;
const DYING_FRAMES = 30;
const LINE_WIDTH = 20;

class BoardView{
    constructor(canvas, borad, players){
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.boradSize = 100;
        this.borad = borad;
        this.players = players;
        this.dying = [];
    }

    draw(x, y){
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.ctx.clearRect(0, 0, this.width, this.height)
        this.drawBorad(x, y);
        this.drawDying(x, y);
        for(let p of this.players){
            p.draw(this.ctx, x, y, this.width, this.height);
        }
    }

    drawFromPerspective(player){
        this.draw(player.posX - this.canvas.width/2 + PLAYER_RADIUS, player.posY - this.canvas.height/2 + PLAYER_RADIUS);
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

    drawDying(viewX, viewY){
        for(let i = 0; i < this.dying.length; i++){
            this.dying[i][0].drawDying(this.ctx, viewX, viewY, this.width, this.height, this.dying[i][1]);
            this.dying[i][1] += 1;
            if (this.dying[i][1] > DYING_FRAMES){
                this.dying.splice(i, 1);
                i -= 1;
            }
        }
    }

    killPlayer(player){
        this.dying.push([player, 0])
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
        let drawing = false;
        let prev = [this.posX, this.posY]
        ctx.lineWidth = LINE_WIDTH;
        ctx.lineCap = "round";
        ctx.lineJoin = "round";
        ctx.strokeStyle = this.color.darken().get();
        for(let i = this.path.length - 1; i >= 0; i--){
            if (drawing){
                if(this.visable(this.path[i][0], this.path[i][1], prev[0], prev[1], viewX-C, viewY-C, viewW+2*C, viewH+2*C)){
                    ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX + 2, this.path[i][1] + BLOCK_SIZE/2 - viewY + 2);
                } else{
                    ctx.stroke();
                    drawing = false;
                }
            } else {
                if(this.visable(this.path[i][0], this.path[i][1], prev[0], prev[1], viewX-C, viewY-C, viewW+2*C, viewH+2*C)){
                    ctx.beginPath();        
                    ctx.moveTo(prev[0] + BLOCK_SIZE/2 - viewX + 2, prev[1] + BLOCK_SIZE/2 - viewY + 2);
                    ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX + 2, this.path[i][1] + BLOCK_SIZE/2 - viewY + 2);
                    drawing = true;
                }
            }
            //ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX, this.path[i][1] + BLOCK_SIZE/2 - viewY);
            prev = this.path[i];
        }
        if (drawing){
            ctx.stroke(); 
            drawing = false;
        }
        prev = [this.posX, this.posY]
        ctx.strokeStyle = this.color.get();
        for(let i = this.path.length - 1; i >= 0; i--){
            if (drawing){
                if(this.visable(this.path[i][0], this.path[i][1], prev[0], prev[1], viewX-C, viewY-C, viewW+2*C, viewH+2*C)){
                    ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX, this.path[i][1] + BLOCK_SIZE/2 - viewY);
                } else{
                    ctx.stroke();
                    drawing = false;
                }
            } else {
                if(this.visable(this.path[i][0], this.path[i][1], prev[0], prev[1], viewX-C, viewY-C, viewW+2*C, viewH+2*C)){
                    ctx.beginPath();        
                    ctx.moveTo(prev[0] + BLOCK_SIZE/2 - viewX, prev[1] + BLOCK_SIZE/2 - viewY);
                    ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX, this.path[i][1] + BLOCK_SIZE/2 - viewY);
                    drawing = true;
                }
            }
            prev = this.path[i];
        }
        if (drawing) ctx.stroke();      
        if(this.posX >= viewX-C && this.posX <= viewX+viewW+C && this.posY >= viewY-C && this.posY <= viewY+viewH+C)
        {
            ctx.beginPath();
            ctx.fillStyle = this.color.darken().get();
            ctx.arc(this.posX + BLOCK_SIZE/2 - viewX + 2,this.posY+ BLOCK_SIZE/2 - viewY + 2,PLAYER_RADIUS+1,0,2*Math.PI);
            ctx.fill();
            ctx.beginPath();
            ctx.fillStyle = this.color.get();
            ctx.arc(this.posX+ BLOCK_SIZE/2 - viewX,this.posY+ BLOCK_SIZE/2 - viewY,PLAYER_RADIUS,0,2*Math.PI);
            ctx.fill();
        }
    }

    drawDying(ctx, viewX, viewY, viewW, viewH, frame){
        const C = PLAYER_RADIUS
        if(LINE_WIDTH>frame){
            let drawing = false;
            let prev = [this.posX, this.posY]
            ctx.lineWidth = LINE_WIDTH-frame;
            ctx.lineCap = "round";
            ctx.lineJoin = "round";
            ctx.strokeStyle = this.color.get();
            for(let i = this.path.length - 1; i >= 0; i--){
                if (drawing){
                    if(this.visable(this.path[i][0], this.path[i][1], prev[0], prev[1], viewX-C, viewY-C, viewW+2*C, viewH+2*C)){
                        ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX, this.path[i][1] + BLOCK_SIZE/2 - viewY);
                    } else{
                        ctx.stroke();
                        drawing = false;
                    }
                } else {
                    if(this.visable(this.path[i][0], this.path[i][1], prev[0], prev[1], viewX-C, viewY-C, viewW+2*C, viewH+2*C)){
                        ctx.beginPath();        
                        ctx.moveTo(prev[0] + BLOCK_SIZE/2 - viewX, prev[1] + BLOCK_SIZE/2 - viewY);
                        ctx.lineTo(this.path[i][0] + BLOCK_SIZE/2 - viewX, this.path[i][1] + BLOCK_SIZE/2 - viewY);
                        drawing = true;
                    }
                }
                prev = this.path[i];
            }
            if (drawing) ctx.stroke();  
        }
        if(this.posX >= viewX-C && this.posX <= viewX+viewW+C && this.posY >= viewY-C && this.posY <= viewY+viewH+C)
        {
            ctx.beginPath();
            ctx.fillStyle = this.color.get();
            ctx.arc(this.posX+ BLOCK_SIZE/2 - viewX,this.posY+ BLOCK_SIZE/2 - viewY,PLAYER_RADIUS-(PLAYER_RADIUS/DYING_FRAMES)*frame,0,2*Math.PI);
            ctx.fill();
        }
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

class Color{
    constructor(red, green, blue){
        this.r = red;
        this.g = green;
        this.b = blue;
    }

    get(){
        return "rgb("+this.r+","+this.g+","+this.b+")";
    }

    darken(){
        return new Color(Math.round(this.r*0.7), Math.round(this.g*0.7), Math.round(this.b*0.7));
    }
}

let view = undefined;
let XX = 1;
let borad = Array(100).fill(Array(100).fill(1));
let players = [ new Player(new Color(255,0,0), 320, 40, [[0, 4000], [0, 600],[120, 600], [120, 40]], 4, 0),
                new Player(new Color(0,255,0), 400, 360, [[5000, 400], [400, 400]], 0, -4),
                new Player(new Color(0,0,255), 320, 360, [[160, 4000], [160, 320], [320, 320]], 0, 4)];

$(document).ready(function(){
    view = new BoardView(document.getElementById('board'), borad, players);
    view.killPlayer(new Player(new Color(255,255,0), 800, 80, [[80, 80]], 4, 0))    
    setInterval(function(){
        window.requestAnimationFrame(function(){
            view.drawFromPerspective(players[XX]);
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
