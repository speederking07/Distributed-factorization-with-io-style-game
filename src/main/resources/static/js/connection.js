let stompClient = null;

class Connection {
    gameID;
    username;

    #constructor(gameID, username){
        this.gameID = gameID
        this.username = username
    }

    async static getGameConnection(user) {
        return new Promise(function (resolve, reject) {
            $.get({
                url: '/game/play?username=' + user,
                dataType: "text/plain",
                success: function (data) {
                    resolve(new #Connection(data, user));
                },
                error: function (data) {
                    reject(data);
                }
            })
        });
    }

    subscribe() {
        stompClient.subscribe('/topic/stomp/' + this.gameID, function (move) {
            if (move.body !== "ERROR") decodeMove(JSON.parse(move.body));
        });
    }

    sendMove(turn, move) {
        if (stompClient != null && this.gameID != null) {
            stompClient.send('/stomp/' + this.gameID, {}, JSON.stringify({
                'turn': turn,
                'move': move,
            }));
        }
    }
}

window.onload = function () {
    let socket = new SockJS('/gameStompEndpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //TODO: co robic przy polaczeniu
    });
};