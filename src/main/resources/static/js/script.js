r = 0;

let demo;

$(document).ready(function () {
    SwipeListener(document);
    $(window).trigger('resize');
    demo = new Demo(document.getElementById('board'));
    demo.start();
    //demo = new Game(new FakeConnection(), document.getElementById('board'), "me");
});

$(window).resize(function () {
    $('#board').attr('width', $(window).width());
    $('#board').attr('height', $(window).height());
});

