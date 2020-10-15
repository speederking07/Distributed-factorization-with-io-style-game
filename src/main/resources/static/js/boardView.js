class BoardView {
    constructor(canvas, board, players) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.prevX = 0;
        this.prevY = 0;
        this.boradSize = 100;
        this.borad = board;
        this.players = players;
        this.dying = [];
        this.animations = [];
    }

    draw(x, y) {
        this.prevX = x;
        this.prevY = y;
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        $('body').css('background-position', Math.floor(x/10)+"px "+Math.floor(y/10)+"px");
        this.ctx.clearRect(0, 0, this.width, this.height);
        this.#drawBoard(x, y);
        this.#drawBoardAnimations(x, y);
        this.#drawDying(x, y);
        for (let p of this.players) {
            p.draw(this.ctx, x, y, this.width, this.height);
        }
    }

    drawFromPerspective(player) {
        this.draw(player.posX - this.canvas.width / 2 + PLAYER_RADIUS, player.posY - this.canvas.height / 2 + PLAYER_RADIUS);
    }

    #drawBoard(viewX, viewY) {
        const row = Math.ceil(this.height / BLOCK_SIZE) + 1;
        const col = Math.ceil(this.width / BLOCK_SIZE) + 1;
        const arrayX = Math.floor(viewX / BLOCK_SIZE);
        const arrayY = Math.floor(viewY / BLOCK_SIZE);
        for (let x = Math.max(0, -arrayX); x <= Math.min(col, this.boradSize - arrayX - 1); x++) {
            for (let y = Math.max(0, -arrayY); y <= Math.min(row, this.boradSize - arrayY - 1); y++) {
                this.ctx.fillStyle = this.borad[x + arrayX][y + arrayY].getColor(x + arrayX, y + arrayY).get();
                this.ctx.fillRect(-mod(viewX, BLOCK_SIZE) + x * BLOCK_SIZE + 1,
                    -mod(viewY, BLOCK_SIZE) + y * BLOCK_SIZE + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2);
            }
        }
    }

    #drawDying(viewX, viewY) {
        for (let i = 0; i < this.dying.length; i++) {
            this.dying[i][0].drawDying(this.ctx, viewX, viewY, this.width, this.height, this.dying[i][1]);
            this.dying[i][1] += 1;
            if (this.dying[i][1] > DYING_FRAMES) {
                this.dying.splice(i, 1);
                i -= 1;
            }
        }
    }

    #drawBoardAnimations(viewX, viewY) {
        for (let i = 0; i < this.animations.length; i++) {
            this.animations[i][0](viewX, viewY, this.animations[i][1]);
            this.animations[i][1] -= 1;
            if (this.animations[i][1] < 0) {
                this.animations.splice(i, 1);
                i -= 1;
            }
        }
    }

    #drawRotation(x, y, viewX, viewY, colorFrom, colorTo, frame) {
        if ((x + 2) * BLOCK_SIZE > viewX && (x - 2) * BLOCK_SIZE < viewX + this.width && (y + 2) * BLOCK_SIZE > viewY && (y - 2) * BLOCK_SIZE < viewY + this.height) {
            if (frame <= ROTATION_FRAMES) {
                if (frame * 2 < ROTATION_FRAMES) {
                    this.ctx.fillStyle = colorTo;
                } else {
                    this.ctx.fillStyle = colorFrom;
                }
                this.ctx.clearRect(x * BLOCK_SIZE - viewX, y * BLOCK_SIZE - viewY, BLOCK_SIZE, BLOCK_SIZE);
                this.#drawRotationRect(x * BLOCK_SIZE - viewX, y * BLOCK_SIZE - viewY, BLOCK_SIZE, BLOCK_SIZE, (Math.PI / ROTATION_FRAMES) * frame);
            } else {
                this.ctx.fillStyle = colorFrom;
                this.ctx.fillRect(x * BLOCK_SIZE - viewX + 1, y * BLOCK_SIZE - viewY + 1, BLOCK_SIZE - 2, BLOCK_SIZE - 2)
            }
        }
    }

    changeField(x, y, patternFrom, patternTo) {
        if ((x + 2) * BLOCK_SIZE > this.prevX && (x - 2) * BLOCK_SIZE < this.prevX + this.width
            && (y + 2) * BLOCK_SIZE > this.prevY && (y - 2) * BLOCK_SIZE < this.prevY + this.height) {
            let t = this;
            this.animations.push([function (vX, vY, frame) {
                t.#drawRotation(x, y, vX, vY, patternFrom.getColor(x, y).get(), patternTo.getColor(x, y).get(), frame);
            }, ROTATION_FRAMES + Math.floor(Math.random() * (MAX_ROTATION_DELAY))]);
        }
    }

    killPlayer(player) {
        this.dying.push([player, 0])
    }

    #drawRotationRect(x, y, w, h, rotation, shape = 0.2) {
        this.ctx.beginPath();
        let t1 = w * Math.cos(rotation) / 2;
        let t2 = w * shape * Math.sin(rotation) / 2;
        this.ctx.moveTo(t1 + x + w / 2, t2 + y);
        this.ctx.lineTo(-t1 + x + w / 2, -t2 + y);
        this.ctx.lineTo(-t1 + x + w / 2, -t2 + y + h);
        this.ctx.lineTo(t1 + x + w / 2, t2 + y + h);
        this.ctx.closePath();
        this.ctx.fill();
    }
}