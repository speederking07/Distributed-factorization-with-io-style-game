/**
 * Class representing patterns on board
 */
class Pattern{
    /**
     * Constrictor of class
     * @param pattern - 2D array representing pattern
     */
    constructor(pattern){
        this.array = pattern;
        this.size = pattern.length;
    }

    /**
     * Function returning color at specific position
     * @param x - position
     * @param y - position
     * @returns {Color} - color at this position
     */
    getColor(x, y){
        return this.array[mod(x, this.size)][mod(y, this.size)]
    }
}

/**
 * Generating single color pattern
 * @param color - color of pattern
 * @returns {Pattern}
 */
function getSingleColorPattern(color){
    return new Pattern([[color]]);
}

const BASE_PATTERN = new Pattern([[new Color(0,0,0), new Color(255,255,255)],
                                          [new Color(255,255,255), new Color(0,0,0)]]);