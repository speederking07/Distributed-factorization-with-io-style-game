var stompClient = null;
var gameID = null;
var username = null;

$(document).ready(function () {
    var socket = new SockJS('/gameStompEndpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //TODO: co robic przy polaczeniu
    });
});



function play() {
    var user = $("#playerName").val();
    alert(user);
    $.get({
        url: '/game/play?username=' + user,
        dataType: "text/plain",
        success: function (data) {
            alert(data);
            gameID = data;
            username = user;
        },
        error: function (data) {
            alert(data.responseText);
        }
    })
}


function subscribe(ID) {
    stompClient.subscribe('/topic/stomp/' + gameID, function (move) {
        if (move.body !== "ERROR") decodeMove(JSON.parse(move.body));
    });
}

//TODO: format ruchu
function sendMove(x, y, type) {
    if (stompClient != null && gameID != null) {
        stompClient.send('/stomp/' + gameID, {}, JSON.stringify({
            'x': x,
            'y': y,
            'commandType': type,
        }));
    }
}