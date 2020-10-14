const BLOCK_SIZE = 40;
const PLAYER_RADIUS = 26;
const DYING_FRAMES = 30;
const LINE_WIDTH = 20;
const ROTATION_FRAMES = 16;
const MAX_ROTATION_DELAY = 20;

class BoardView{
    constructor(canvas, borad, players){
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.prevX = 0;
        this.prevY = 0;
        this.boradSize = 100;
        this.borad = borad;
        this.players = players;
        this.dying = [];
        this.animations = [];
    }

    draw(x, y){
        this.prevX = x;
        this.prevY = y;
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.ctx.clearRect(0, 0, this.width, this.height)
        this.drawBorad(x, y);
        this.drawBoardAnimations(x, y);
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
                    this.ctx.fillStyle = 'rgb(0, 0, 0)';
                    this.ctx.fillRect(-mod(viewX,BLOCK_SIZE) + x*BLOCK_SIZE + 1, -mod(viewY,BLOCK_SIZE) + y*BLOCK_SIZE +1, BLOCK_SIZE-2, BLOCK_SIZE-2);
                } if (this.borad[x+arrayX][y+arrayY] instanceof Player) {
                    this.ctx.fillStyle = this.borad[x+arrayX][y+arrayY].color.get();
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

    drawBoardAnimations(viewX, viewY){
        for(let i = 0; i < this.animations.length; i++){
            this.animations[i][0](viewX, viewY, this.animations[i][1])
            this.animations[i][1] -= 1;
            if (this.animations[i][1] < 0){
                this.animations.splice(i, 1);
                i -= 1;
            }
        }
    }

    drawRotation(x, y, viewX, viewY, colorFrom, colorTo, frame){
        if((x+2)*BLOCK_SIZE > viewX && (x-2)*BLOCK_SIZE < viewX + this.width && (y+2)*BLOCK_SIZE > viewY && (y-2)*BLOCK_SIZE < viewY + this.height){
            if(frame <= ROTATION_FRAMES){
                if(frame*2 < ROTATION_FRAMES){
                    this.ctx.fillStyle = colorTo;
                } else {
                    this.ctx.fillStyle = colorFrom;
                }
                this.ctx.clearRect(x*BLOCK_SIZE - viewX, y*BLOCK_SIZE - viewY, BLOCK_SIZE, BLOCK_SIZE)
                drawRotationRect(this.ctx, x*BLOCK_SIZE - viewX, y*BLOCK_SIZE - viewY, BLOCK_SIZE, BLOCK_SIZE, (Math.PI/ROTATION_FRAMES) * frame);
            } else {
                this.ctx.fillStyle = colorFrom;
                this.ctx.fillRect(x*BLOCK_SIZE - viewX + 1, y*BLOCK_SIZE - viewY + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2)
            }
        }
    }

    changeField(x,y,colorFrom, colorTo){
        if((x+2)*BLOCK_SIZE > this.prevX && (x-2)*BLOCK_SIZE < this.prevX + this.width
            && (y+2)*BLOCK_SIZE > this.prevY && (y-2)*BLOCK_SIZE < this.prevY + this.height)
        {   
            let t = this;
            this.animations.push([function(vX, vY, frame){
                t.drawRotation(x, y, vX, vY, colorFrom, colorTo, frame);
            }, ROTATION_FRAMES + Math.floor(Math.random() * (MAX_ROTATION_DELAY))]);
        };
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

let k = 0.2

function drawRotationRect(ctx, x, y, w, h, rotation, shape=0.2){
    ctx.beginPath();
    let t1 = w*Math.cos(rotation)/2;
    let t2 = w*shape*Math.sin(rotation)/2;
    ctx.moveTo(t1+x+w/2, t2+y);
    ctx.lineTo(-t1+x+w/2, -t2+y);
    ctx.lineTo(-t1+x+w/2, -t2+y+h);
    ctx.lineTo(t1+x+w/2, t2+y+h);
    ctx.closePath();
    ctx.fill();
}

let view = undefined;
let XX = 1;
let YY = 1;
let borad = Array(100)
for(let i = 0; i< 100; i++){
    borad[i] = Array(100);
    borad[i].fill(undefined);
}
let players = [ new Player(new Color(255,0,0), 320, 40, [[0, 4000], [0, 600],[120, 600], [120, 40]], 4, 0),
                new Player(new Color(0,255,0), 400, 360, [[5000, 400], [400, 400]], 0, -4),
                new Player(new Color(0,0,255), 320, 360, [[160, 4000], [160, 320], [320, 320]], 0, 4)];
r = 0;

$(document).ready(function(){
    view = new BoardView(document.getElementById('board'), borad, players);
    view.killPlayer(new Player(new Color(255,255,0), 800, 80, [[80, 80]], 4, 0))    
    setInterval(function(){
        window.requestAnimationFrame(function(){
            view.drawFromPerspective(players[2]);
        });
    }, 40);    

    setInterval(function(){
        for(let p of players){
            p.move();
        }
    }, 40);

    $(window).trigger('resize');

    setTimeout(() => {
        for(let x = 5; x < 8; x++){
            for(let y = 9; y < 50; y++){
                view.changeField(x,y, '#000000', players[2].color.get());
                borad[x][y] = players[2];
            }
        }
    }, 100);
});

$(window).resize(function(e) {
    $('#board').attr('width', $(window).width());
    $('#board').attr('height', $(window).height());
})
