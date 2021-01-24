r = 0;

let demo, worker;

$(document).ready(function () {
    SwipeListener(document);
    worker = new Worker('./js/wasm/factorization.js');
    $(window).trigger('resize');
    demo = new Demo(document.getElementById('board'));
    demo.start();
    //demo = new Game(new FakeConnection(), document.getElementById('board'), "me");
});

$(window).resize(function () {
    $('#board').attr('width', $(window).width());
    $('#board').attr('height', $(window).height());
});

