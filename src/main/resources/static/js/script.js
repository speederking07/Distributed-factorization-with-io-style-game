

let k = 0.2;


let view = undefined;
let XX = 1;
let YY = 1;
let board = Array(100);
for (let i = 0; i < 100; i++) {
    board[i] = Array(100);
    board[i].fill(BASE_PATTERN);
}
let players = [new Player(new Color(255, 0, 0), 320, 40, [[0, 4000], [0, 600], [120, 600], [120, 40]], 4, 0),
    new Player(new Color(0, 255, 0), 400, 360, [[5000, 400], [400, 400]], 0, -4),
    new Player(new Color(0, 0, 255), 320, 360, [[160, 4000], [160, 320], [320, 320]], 0, 4)];
r = 0;

$(document).ready(function () {
    view = new BoardView(document.getElementById('board'), board, players);
    view.killPlayer(new Player(new Color(255, 255, 0), 800, 80, [[80, 80]], 4, 0))
    /*setInterval(function () {
        window.requestAnimationFrame(function () {
            view.drawFromPerspective(players[1]);
        });
    }, 40);

    setInterval(function () {
        for (let p of players) {
            p.move();
        }
    }, 40);

    $(window).trigger('resize');

    setTimeout(() => {
        for (let x = 5; x < 8; x++) {
            for (let y = 9; y < 50; y++) {
                view.changeField(x, y, BASE_PATTERN, players[2].pattern);
                board[x][y] = players[2].pattern;
            }
        }
    }, 100);*/
});

$(window).resize(function () {
    $('#board').attr('width', $(window).width());
    $('#board').attr('height', $(window).height());
});
