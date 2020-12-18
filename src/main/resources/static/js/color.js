/**
 * Class representing color
 * @author Marek Bauer
 */
class Color {
    /**
     * Class constructor
     * @param red
     * @param green
     * @param blue
     */
    constructor(red, green, blue) {
        this.r = red;
        this.g = green;
        this.b = blue;
    }

    /**
     * Returns string representing color in RGB format
     * @returns {string}
     */
    get() {
        return "rgb(" + this.r + "," + this.g + "," + this.b + ")";
    }

    /**
     * Returns darker color
     * @returns {Color}
     */
    darken() {
        let hsl = Color.#rgbToHsl(this.r, this.g, this.b);
        return Color.#hslToColor(hsl[0], hsl[1], hsl[2]/2)
    }

    /**
     * Returns lighten color
     * @returns {Color}
     */
    lighten() {
        let hsl = Color.#rgbToHsl(this.r, this.g, this.b);
        return Color.#hslToColor(hsl[0], hsl[1], hsl[2]/2+0.5)
    }

    /**
     * Constructs color from hex string
     * @param str - string representing color in hex format #000000
     * @returns {Color}
     */
    static fromHex(str){
        let num = parseInt(str.replace('#', '0x'));
        return new Color(Math.floor(num/65536)%256, Math.floor(num/256)%256, num%256)
    }

    /**
     * Returns string representing color in hex format
     * @returns {string}
     */
    toHex(){
        return '#'+("0"+(this.r.toString(16))).slice(-2)+("0"+(this.g.toString(16))).slice(-2)+
            ("0"+(this.b.toString(16))).slice(-2)
    }

    /**
     * Converts RGB to HSL
     * @param r - red
     * @param g - green
     * @param b = blue
     * @returns {[number, number, number]}
     */
    static #rgbToHsl(r, g, b) {
        r /= 255;
        g /= 255;
        b /= 255;
        let max = Math.max(r, g, b);
        let min = Math.min(r, g, b);
        let h, s, l = (max + min) / 2;

        if (max === min) {
            h = s = 0;
        } else {
            let d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
            switch (max) {
                case r: h = (g - b) / d + (g < b ? 6 : 0); break;
                case g: h = (b - r) / d + 2; break;
                case b: h = (r - g) / d + 4; break;
            }
            h /= 6;
        }
        return [ h, s, l ];
    }

    /**
     * Converts hue to RGB param
     * @param p
     * @param q
     * @param t
     * @returns {*}
     */
    static #hue(p, q, t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (t < 1/6) return p + (q - p) * 6 * t;
        if (t < 1/2) return q;
        if (t < 2/3) return p + (q - p) * (2/3 - t) * 6;
        return p;
    }

    /**
     * Converts HSL to Color
     * @param h - hue
     * @param s - saturation
     * @param l - light
     * @returns Color
     */
     static #hslToColor(h, s, l) {
        let r, g, b;
        if (s === 0) {
            r = g = b = l;
        } else {
            let q = l < 0.5 ? l * (1 + s) : l + s - l * s;
            let p = 2 * l - q;
            r = Color.#hue(p, q, h + 1/3);
            g = Color.#hue(p, q, h);
            b = Color.#hue(p, q, h - 1/3);
        }
        return new Color(Math.floor(r * 255), Math.floor(g * 255), Math.floor(b * 255));
    }

    static formJson(data){
        const color = JSON.parse(data).color;
        return Color.fromHex(color);
    }
}