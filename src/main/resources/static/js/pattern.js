class Pattern{
    constructor(pattern){
        this.array = pattern;
        this.size = pattern.length;
    }

    getColor(x, y){
        return this.array[mod(x, this.size)][mod(y, this.size)]
    }
}

function getSingleColorPattern(color){
    return new Pattern([[color]]);
}

const BASE_PATTERN = new Pattern([[new Color(0,0,0), new Color(255,255,255)],
                                          [new Color(255,255,255), new Color(0,0,0)]]);