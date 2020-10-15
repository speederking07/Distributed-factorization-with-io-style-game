class Color {
    constructor(red, green, blue) {
        this.r = red;
        this.g = green;
        this.b = blue;
    }

    get() {
        return "rgb(" + this.r + "," + this.g + "," + this.b + ")";
    }

    darken() {
        return new Color(Math.round(this.r * 0.7), Math.round(this.g * 0.7), Math.round(this.b * 0.7));
    }
}