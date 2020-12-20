/**
 * Class representing player in game
 *
 * @author Marek Bauer
 */

class Player {
    /**
     * Constructor of player
     * @param color : Color - color of player
     * @param name : String - name of player
     * @param posX - current position in pixels
     * @param posY - current position in pixels
     * @param path : [[int]] - array of positions of turns in pixels. eg [[0,0], [30, 0]] means path started in (0, 0),
     *                         goes to (30, 0) and to current player's position
     * @param movX - current speed of player
     * @param movY - current speed of player
     * @param pattern : Pattern - pattern of this player
     * @param futurePos
     * @param currentPos
     * @param prevPos
     */
    constructor(color, name, posX, posY, path, movX, movY, pattern = getSingleColorPattern(color),
                futurePos = null, currentPos = {x: posX / BLOCK_SIZE, y: posY / BLOCK_SIZE}, prevPos = null) {
        this.color = color;
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.path = path;
        this.movX = movX;
        this.movY = movY;
        this.pattern = pattern;
        this.futurePos = futurePos;
        this.currentPos = currentPos;
        this.prevPos = prevPos;
        this.drawingPath = false;
    }

    static fromServer(data) {
        let posX = (data.x - 1) * BLOCK_SIZE - 3 * SPEED;
        let posY = data.y * BLOCK_SIZE;
        let color = Color.formJson(data.colorsInCSV);
        let pattern = Pattern.FromJSON(data.colorsInCSV);
        return new Player(color, data.name, posX, posY, [], 2, 0, pattern,
            {x: data.x, y: data.y}, {x: data.x - 1, y: data.y}, {x: data.x - 2, y: data.y});
    }

    /**
     * Function checking if is rectangle ((x1, y1), (x2, y2)) has common points with ((vX, vY), (vX + vW, vY + vH))
     * @param x1 - coordinate of first rectangle
     * @param y1 - coordinate of first rectangle
     * @param x2 - coordinate of first rectangle
     * @param y2 - coordinate of first rectangle
     * @param vX - coordinate of second rectangle
     * @param vY - coordinate of second rectangle
     * @param vW - width of second rectangle
     * @param vH - height of second rectangle
     * @returns {boolean} - result
     */
    static visible(x1, y1, x2, y2, vX, vY, vW, vH) {
        if (x1 > x2) x2 = [x1, x1 = x2][0];
        if (y1 > y2) y2 = [y1, y1 = y2][0];
        return !(x1 > vX + vW || x2 < vX || y1 > vY + vH || y2 < vY);
    }

    /**
     * Function displaying player on canvas
     * @param ctx - canvas context
     * @param viewX - x coordinate of camera
     * @param viewY - y coordinate of camera
     * @param viewW - width of camera view
     * @param viewH - height of camera view
     */
    draw(ctx, viewX, viewY, viewW, viewH) {
        const C = PLAYER_RADIUS * 2;
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

    /**
     * Function displaying names above players
     * @param ctx - canvas context
     * @param viewX - x coordinate of camera
     * @param viewY - y coordinate of camera
     * @param viewW - width of camera view
     * @param viewH - height of camera view
     */
    displayName(ctx, viewX, viewY, viewW, viewH) {
        const center = this.name.length * -10 + PLAYER_RADIUS;
        const x = this.posX - viewX + center;
        const y = this.posY - viewY - 10;
        if (Player.visible(x, y - 30, x + this.name.length * 30, y + 30, viewX, viewY, viewW, viewH)) {
            ctx.font = '34px Consolas';
            ctx.strokeStyle = 'black';
            ctx.lineWidth = 4;
            ctx.strokeText(this.name, x, y);
            ctx.fillStyle = 'white';
            ctx.fillText(this.name, x, y);
        }
    }

    /**
     * Function displaying dying player animation on canvas
     * @param ctx - canvas context
     * @param viewX - x coordinate of camera
     * @param viewY - y coordinate of camera
     * @param viewW - width of camera view
     * @param viewH - height of camera view
     * @param frame - frame of dying animation
     */
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

    /**
     * Computing how many steps form previous position is player
     * @returns {number}
     */
    computeStep() {
        if (this.movX >= 0) {
            return (this.posX - Math.floor(this.posX / BLOCK_SIZE) * BLOCK_SIZE) / SPEED
        } else if (this.movX < 0) {
            return BLOCK_SIZE / SPEED - (this.posX - Math.floor(this.posX / BLOCK_SIZE) * BLOCK_SIZE) / SPEED
        } else if (this.movY >= 0) {
            return (this.posY - Math.floor(this.posY / BLOCK_SIZE) * BLOCK_SIZE) / SPEED
        } else {
            return BLOCK_SIZE / SPEED - (this.posY - Math.floor(this.posY / BLOCK_SIZE) * BLOCK_SIZE) / SPEED
        }
    }

    /**
     * Change current position target in case of wrong predictions
     * @param currX
     * @param currY
     */
    makePositionAdjustments(currX, currY) {
        const step = this.computeStep();
        if (this.path !== []) {
            this.path.push([currX * BLOCK_SIZE, currY * BLOCK_SIZE])
        }
        this.movX = (this.currentPos.x - this.prevPos.x) * SPEED;
        this.movY = (this.currentPos.y - this.prevPos.y) * SPEED;
        this.posX = this.prevPos.x * BLOCK_SIZE + movX * step;
        this.posY = this.prevPos.y * BLOCK_SIZE + movY * step;
    }

    /**
     * Set position from witch player is going
     * @param x
     * @param y
     */
    setPrevPos(x, y) {
        this.prevPos = {x: x, y: y}
    }

    /**
     * Set position to witch player is going to go in next turn
     * @param x
     * @param y
     */
    setFuturePos(x, y) {
        this.futurePos = {x: x, y: y}
    }

    /**
     * Players started path
     */
    drawPath() {
        this.drawingPath = true;
    }

    /**
     * Player stoped his path
     */
    closePath() {
        this.drawingPath = false;
    }

    /**
     * Call this function to add current speed to position
     */
    move() {
        this.posX += this.movX;
        this.posY += this.movY;
        if (mod(this.posX, BLOCK_SIZE) === 0 && mod(this.posY, BLOCK_SIZE) === 0) {
            if (this.futurePos === null) {
                this.futurePos = {
                    x: (this.posX + (this.movX / SPEED) * BLOCK_SIZE) / BLOCK_SIZE,
                    y: (this.posY + (this.movY / SPEED) * BLOCK_SIZE) / BLOCK_SIZE
                }
            } else {
                const pX = this.movX;
                const pY = this.movY;
                this.movX = (this.futurePos.x - this.currentPos.x) * SPEED;
                this.movY = (this.futurePos.y - this.currentPos.y) * SPEED;
                if ((pX !== this.movX || pY !== this.movY) && this.path !== []) {
                    this.path.push([this.posX, this.posY])
                }
            }
            if (this.drawingPath === true && this.path === []) {
                this.path.push([this.posX, this.posY])
            } else if (this.drawingPath === false) {
                this.path = []
            }
            this.prevPos = this.currentPos;
            this.currentPos = this.futurePos;
            this.futurePos = null;
        }
    }

    /**
     * Function drawing path behind player
     * @param ctx - canvas context
     * @param viewX - x coordinate of camera
     * @param viewY - y coordinate of camera
     * @param viewW - width of camera view
     * @param viewH - height of camera view
     * @param shift - number of pixels to shift path position to right and dawn
     * @param C - extension of field of view for drawing optimization
     */
    #drawPath(ctx, viewX, viewY, viewW, viewH, shift = 0, C = PLAYER_RADIUS) {
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
}