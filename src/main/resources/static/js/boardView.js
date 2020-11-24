/**
 * Class responsible of displaying current state of game on canvas
 *
 * @author Marek Bauer
 */
class BoardView {
    /**
     * Constructor of boardView
     * @param canvas - handle to canvas
     * @param board : [[Pattern]] - reference to 2d array of patterns representing current state of board
     * @param players : [Player] - reference to list of players
     */
    constructor(canvas, board, players){
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        this.prevX = 0;
        this.prevY = 0;
        this.boradSize = board.length;
        this.borad = board;
        this.players = players;
        this.dying = [];
        this.animations = [];
        let config = this.getGraphicalSettings();
        this.draw = this.generateDrawFunction(config[0], config[1], config[2]);
    }

    /**
     * Function returning display configuration
     */
    getGraphicalSettings(){
        return [true, true, true]
    }

    /**
     * Function displaying game on canvas at point
     * @param x - x coordinate
     * @param y - y coordinate
     */
    draw(x, y) {
        this.prevX = x;
        this.prevY = y;
        this.width = this.canvas.width;
        this.height = this.canvas.height;
        $('body').css('background-position', Math.floor(-x * BACKGROUND_SHIFT) + "px " + Math.floor(-y * BACKGROUND_SHIFT) + "px");
        this.ctx.clearRect(0, 0, this.width, this.height);
        this.#drawBoard(x, y);
        this.#drawBoardAnimations(x, y);
        this.#drawDying(x, y);
        for (let p of this.players) {
            p.draw(this.ctx, x, y, this.width, this.height);
        }
        for (let p of this.players) {
            p.displayName(this.ctx, x, y, this.width, this.height);
        }
    }

    /**
     * Function displaying game form same player perspective     *
     * @param player : Player - player to center perspective on
     */
    drawFromPerspective(player) {
        this.draw(player.posX - this.canvas.width / 2 + PLAYER_RADIUS, player.posY - this.canvas.height / 2 + PLAYER_RADIUS);
    }

    /**
     * Call this function to generate color transition animation on board
     * @param x - coordinate of board tile
     * @param y - coordinate of board tile
     * @param patternFrom : Pattern - previous pattern
     * @param patternTo : Pattern - new pattern
     */
    changeField(x, y, patternFrom, patternTo) {
        if ((x + 2) * BLOCK_SIZE > this.prevX && (x - 2) * BLOCK_SIZE < this.prevX + this.width
            && (y + 2) * BLOCK_SIZE > this.prevY && (y - 2) * BLOCK_SIZE < this.prevY + this.height) {
            let t = this;
            this.animations.push([function (vX, vY, frame) {
                t.#drawRotation(x, y, vX, vY, patternFrom.getColor(x, y).get(), patternTo.getColor(x, y).get(), frame);
            }, ROTATION_FRAMES + Math.floor(Math.random() * (MAX_ROTATION_DELAY))]);
        }
    }

    /**
     * Call this function to generate animation of dying player
     * @param player - reference to dying player
     */
    killPlayer(player) {
        this.dying.push([player, 0])
    }

    /**
     * Function displaying board at point
     *
     * @param viewX - x coordinate
     * @param viewY - y coordinate
     */
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

    /**
     * Function displaying dying players animations
     * @param viewX - x coordinate
     * @param viewY - y coordinate
     */
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

    /**
     * Function displaying animations on board
     * @param viewX - x coordinate
     * @param viewY - y coordinate
     */
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

    /**
     * Function displaying rotation of one tile
     * @param x - coordinate of tile to rotate
     * @param y - coordinate of tile to rotate
     * @param viewX - position of camera
     * @param viewY - position of camera
     * @param colorFrom - previous color
     * @param colorTo - new color
     * @param frame - frame of animation
     */
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

    /**
     * Function to draw rectangle in rotation
     * @param x - x position of rectangle
     * @param y - y position of rectangle
     * @param w - width of rectangle
     * @param h - height of rectangle
     * @param rotation - angle of rotation in radians
     * @param shape - perspective of viewer (at 0 only width is changed)
     */
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

    /**
     * Function generating display function
     * @param displayNames : boolean - true if names will be displayed
     * @param displayAnimations : boolean - true if animations will be displayed
     * @param displayDying : boolean - true if dying players will be displayed
     * @returns new display function
     */
    generateDrawFunction(displayNames, displayAnimations, displayDying){
        let names, animations, dying;
        if(displayNames){
            names = ((x, y) => {
                for (let p of this.players) {
                    p.displayName(this.ctx, x, y, this.width, this.height);
                }
            }).bind(this)
        } else{
            names = (_x, _y) => void 0;
        }
        if(displayAnimations){
            animations = ((x, y) => this.#drawBoardAnimations(x, y)).bind(this)
        } else{
            animations = ((_x, _y) => this.animations = []).bind(this);
        }
        if(displayDying){
            dying = ((x, y) => this.#drawDying(x, y)).bind(this)
        } else{
            dying = ((_x, _y) => this.dying = []).bind(this);
        }
        return((x, y) => {
            this.prevX = x;
            this.prevY = y;
            this.width = this.canvas.width;
            this.height = this.canvas.height;
            $('body').css('background-position', Math.floor(-x * BACKGROUND_SHIFT) + "px " + Math.floor(-y * BACKGROUND_SHIFT) + "px");
            this.ctx.clearRect(0, 0, this.width, this.height);
            this.#drawBoard(x, y);
            animations(x, y);
            dying(x, y);
            for (let p of this.players) {
                p.draw(this.ctx, x, y, this.width, this.height);
            }
            names(x, y);
        }).bind(this)
    }
}