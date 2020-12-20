let stompClient = null;

class Connection {
    gameID;
    username;

    constructor(gameID, username){
        this.gameID = gameID;
        this.username = username;
    }

    static getGameConnection(user) {
        return new Promise(function (resolve, reject) {
            $.get({
                url: '/game/play?username=' + user,
                success: function (data) {
                    resolve(new Connection(data, user));
                },
                error: function (data) {
                    reject(data.responseText);
                }
            })
        });
    }

    subscribe(update) {
        stompClient.subscribe('/topic/stomp/' + this.gameID, function (state) {
            update(JSON.parse(state.body));
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

class FakeConnection{
    constructor(){
        this.turn = 0;
    }

    subscribe(update){
        update({turn: 0, kills: [], moves:[], change:[], addPlayers: [
            {name:"me", x:20, y:20, colorsInCSV:"{\"size\":1, \"pattern\": [[\"#ff0000\"]], \"color\": \"#ff0000\"}"}
            ]});
    }

    sendMove(_t, _m){}

}

window.onload = function () {
    let socket = new SockJS('/gameStompEndpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //TODO: co robic przy polaczeniu
    });
};