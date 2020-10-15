class Player {
    constructor(color, posX, posY, path, movX, movY, pattern=getSingleColorPattern(color)) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.path = path;
        this.movX = movX;
        this.movY = movY;
        this.pattern = pattern;
    }

    draw(ctx, viewX, viewY, viewW, viewH) {
        const C = PLAYER_RADIUS;
        ctx.lineWidth = LINE_WIDTH;
        ctx.lineCap = "round";
        ctx.lineJoin = "round";
        ctx.strokeStyle = this.color.darken().get();
        this.#drawPath(ctx, viewX, viewY, viewW, viewH, 2);
        ctx.strokeStyle = this.color.get();
        this.#drawPath(ctx, viewX, viewY, viewW, viewH);
        if (this.posX >= viewX - C && this.posX <= viewX + viewW + C && this.posY >= viewY - C && this.posY <= viewY + viewH + C) {
            ctx.beginPath();
            ctx.fillStyle = this.color.darken().get();
            ctx.arc(this.posX + BLOCK_SIZE / 2 - viewX + 2, this.posY + BLOCK_SIZE / 2 - viewY + 2, PLAYER_RADIUS + 1, 0, 2 * Math.PI);
            ctx.fill();
            ctx.beginPath();
            ctx.fillStyle = this.color.get();
            ctx.arc(this.posX + BLOCK_SIZE / 2 - viewX, this.posY + BLOCK_SIZE / 2 - viewY, PLAYER_RADIUS, 0, 2 * Math.PI);
            ctx.fill();
        }
    }

    drawDying(ctx, viewX, viewY, viewW, viewH, frame) {
        const C = PLAYER_RADIUS;
        if (LINE_WIDTH > frame) {
            ctx.lineWidth = LINE_WIDTH - frame;
            ctx.lineCap = "round";
            ctx.lineJoin = "round";
            ctx.strokeStyle = this.color.get();
            this.#drawPath(ctx, viewX, viewY, viewW, viewH);
        }
        if (this.posX >= viewX - C && this.posX <= viewX + viewW + C && this.posY >= viewY - C && this.posY <= viewY + viewH + C) {
            ctx.beginPath();
            ctx.fillStyle = this.color.get();
            ctx.arc(this.posX + BLOCK_SIZE / 2 - viewX, this.posY + BLOCK_SIZE / 2 - viewY, PLAYER_RADIUS - (PLAYER_RADIUS / DYING_FRAMES) * frame, 0, 2 * Math.PI);
            ctx.fill();
        }
    }

    #drawPath(ctx, viewX, viewY, viewW, viewH, shift = 0, C=PLAYER_RADIUS) {
        let drawing = false;
        let prev = [this.posX, this.posY];
        for (let i = this.path.length - 1; i >= 0; i--) {
            if (drawing) {
                if (Player.visible(this.path[i][0], this.path[i][1], prev[0], prev[1], viewX - C, viewY - C, viewW + 2 * C, viewH + 2 * C)) {
                    ctx.lineTo(this.path[i][0] + BLOCK_SIZE / 2 - viewX + shift, this.path[i][1] + BLOCK_SIZE / 2 - viewY + shift);
                } else {
                    ctx.stroke();
                    drawing = false;
                }
            } else {
                if (Player.visible(this.path[i][0], this.path[i][1], prev[0], prev[1], viewX - C, viewY - C, viewW + 2 * C, viewH + 2 * C)) {
                    ctx.beginPath();
                    ctx.moveTo(prev[0] + BLOCK_SIZE / 2 - viewX + shift, prev[1] + BLOCK_SIZE / 2 - viewY + shift);
                    ctx.lineTo(this.path[i][0] + BLOCK_SIZE / 2 - viewX + shift, this.path[i][1] + BLOCK_SIZE / 2 - viewY + shift);
                    drawing = true;
                }
            }
            prev = this.path[i];
        }
        if (drawing) ctx.stroke();
    }

    static visible(x1, y1, x2, y2, vX, vY, vW, vH) {
        if (x1 > x2) x2 = [x1, x1 = x2][0];
        if (y1 > y2) y2 = [y1, y1 = y2][0];
        return !(x1 > vX + vW || x2 < vX || y1 > vY + vH || y2 < vY);
    }

    move() {
        this.posX += this.movX;
        this.posY += this.movY;
    }
}