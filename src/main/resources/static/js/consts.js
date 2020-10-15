const BLOCK_SIZE = 40;
const PLAYER_RADIUS = 26;
const DYING_FRAMES = 30;
const LINE_WIDTH = 20;
const ROTATION_FRAMES = 16;
const MAX_ROTATION_DELAY = 20;
const BACKGROUND_SHIFT = 0.25

function mod(a, n) {
    return ((a % n) + n) % n
}