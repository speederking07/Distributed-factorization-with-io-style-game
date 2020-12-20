const BLOCK_SIZE = 40;
const PLAYER_RADIUS = 26;
const DYING_FRAMES = 30;
const LINE_WIDTH = 20;
const ROTATION_FRAMES = 16;
const MAX_ROTATION_DELAY = 20;
const BACKGROUND_SHIFT = 0.25;
const FRAMES_PER_SECONDS = 30;
const SPEED = 2;
const TURNS_PER_SECONDS = 4;
const NUMBER_OF_STEPS = BLOCK_SIZE/SPEED;
const MOVE_WINDOW = Math.floor(2*NUMBER_OF_STEPS/3);

function mod(a, n) {
    return ((a % n) + n) % n
}

function arrayRemove(arr, value) {
    return arr.filter(ele => ele !== value );
}