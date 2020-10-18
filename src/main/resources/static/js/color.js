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

    lighten() {
        return new Color(Math.min(255, Math.floor(this.r * 1.5)), Math.min(255, Math.floor(this.g * 1.5)),
            Math.min(255, Math.floor(this.b * 1.5)));
    }

    static fromHex(str){
        let num = parseInt(str.replace('#', '0x'));
        return new Color(Math.floor(num/65536)%256, Math.floor(num/256)%256, num%256)
    }

    toHex(){
        return '#'+("0"+(this.r.toString(16))).slice(-2)+("0"+(this.g.toString(16))).slice(-2)+
            ("0"+(this.b.toString(16))).slice(-2)
    }
}