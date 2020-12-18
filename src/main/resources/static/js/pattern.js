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

    /**
     * Converts pattern to JSON string
     * @returns {string}
     */
    toJSON(color){
        let pattern = this.array.map(x => x.map(y => y.toHex()));
        return JSON.stringify({size: this.size, pattern: pattern, color:color.toHex()});
    }

    /**
     * Creates pattern from JSON string
     * @param data - JSON string
     * @returns {Pattern} - pattern represented by this string
     */
    static FromJSON(data){
        let obj = JSON.parse(data);
        let patten = obj.pattern.map(x => x.map(y => Color.fromHex(y)));
        return new Pattern(patten)
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

const BASE_PATTERN = new Pattern([[Color.fromHex('#769887'), Color.fromHex('#b4ab8a')],
                                          [Color.fromHex('#b4ab8a'), Color.fromHex('#769887')]]);