const BLOCK_SIZE = 40; //Size of single tile on board
const PLAYER_RADIUS = 26; //Radius of player's checker
const DYING_FRAMES = 30; //Length of dying animations in frames
const LINE_WIDTH = 20; //Width of line of path behind players
const ROTATION_FRAMES = 16; //Length of tile animation in frames
const MAX_ROTATION_DELAY = 20; //Maximal number of frames to wait to begin change tile animation
const BACKGROUND_SHIFT = 0.25; //Number of pixes to move background per one pixel of moved foreground
const FRAMES_PER_SECONDS = 30; //Caped FPS in game
const SPEED = 2; //Number of pixels to move on one step
const TURNS_PER_SECONDS = 4; //Number of tiles to move to in single seconds
const NUMBER_OF_STEPS = BLOCK_SIZE / SPEED; //Number of steps to move from one tile to another
const MOVE_WINDOW = Math.floor(2 * NUMBER_OF_STEPS / 3); //Number of step after witch player can not change direction
const BOARD_SIZE = 200; //Size of board on witch game takes place

function mod(a, n) {
    return ((a % n) + n) % n
}

function arrayRemove(arr, value) {
    return arr.filter(ele => ele !== value);
}