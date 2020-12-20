/**
 * Class responsible of calling animating at specified frame rate
 *
 * @author Marek Bauer
 */
class Animator {
    /**
     * Class constructor
     * @param drawFunction - the function which supposed to be called
     * @param FPS - frame rate
     */
    constructor(drawFunction, FPS) {
        this.then = Date.now();
        this.print = drawFunction;
        this.fpsInterval = 1000 / FPS;
        this.alive = false;
        this.live = this.live.bind(this)
    }

    /**
     * Animation function
     */
    live() {
        if (!this.alive) return;
        requestAnimationFrame(this.live);
        let now = Date.now();
        let elapsed = now - this.then;
        if (elapsed > this.fpsInterval) {
            this.then = now - (elapsed % this.fpsInterval);
            this.print();
        }
    }

    /**
     * Start animation
     */
    start() {
        this.then = Date.now();
        this.alive = true;
        requestAnimationFrame(this.live);
    }

    /**
     * Stop animation
     */
    kill() {
        this.alive = false;
    }
}