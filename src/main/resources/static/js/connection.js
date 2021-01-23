let stompClient = null;

/**
 * Class responsible of handling connection via WebSocket
 *
 * @author Marek Bauer
 * @author Tomasz Pach
 */
class Connection {
    /**
     * Constructor of Connection
     *
     * @param gameID - Id of game to connect
     * @param username - name using in game
     */
    constructor(gameID, username) {
        this.gameID = gameID;
        this.username = username;
        console.log(username)
    }

    /**
     * Get get a promise of connection to server
     *
     * @param user - username to use
     * @returns {Promise<unknown>}
     */
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

    /**
     * Set listener of server connection
     *
     * @param update - function to be called whenever information is received from server
     */
    subscribe(update) {
        let id = this.gameID;
        return new Promise((function(resolve, reject) {
            $.get({
                url: '/game/state/' + this.gameID,
                success: function (data) {
                    stompClient.subscribe('/topic/stomp/' + id, function (state) {
                        update(JSON.parse(state.body));
                    }, {id: "gameSocket"});
                    resolve(data)
                },
                error: function (data) {
                    reject(data.responseText);
                }
            });
        }).bind(this));
    }

    unsubscribe() {
        if (stompClient != null) stompClient.unsubscribe("gameSocket");
    }

    /**
     * Send information about move to server
     *
     * @param turn - number of turn
     * @param move - string representing direction 'NORTH', 'WEST' etc.
     */
    sendMove(turn, move) {
        if (stompClient != null && this.gameID != null) {
            stompClient.send('/stomp/' + this.gameID, {}, JSON.stringify({
                'turn': turn,
                'move': move,
            }));
        }
    }
}

class FakeConnection {
    constructor() {
        this.turn = 0;
    }

    subscribe(update) {
        update({
            turn: 0, kills: [], moves: [], change: [], addPlayers: [
                {
                    name: "me",
                    x: 20,
                    y: 20,
                    colorsInCSV: "{\"size\":1, \"pattern\": [[\"#ff0000\"]], \"color\": \"#ff0000\"}"
                }
            ]
        });
    }

    sendMove(_t, _m) {
    }

}

/**
 * Creates connection via WebSocket
 */
window.onload = function () {
    refreshWebsocketConnection();
};

function refreshWebsocketConnection() {
    if (stompClient != null) stompClient.disconnect();
    let socket = new SockJS('/gameStompEndpoint');
    stompClient = Stomp.over(socket);
    stompClient.debug = () => {
    };
    stompClient.connect({}, function (frame) {
        connectSynchronizer();
    });
}

//  SYNC

let lastReqSendTime = null;
let times = [150]

function connectSynchronizer() {
    stompClient.subscribe('/user/queue/synchronize', function (state) {
        if (times.length >= 5) times.shift();
        let tempTime = Date.now() - lastReqSendTime;
        times.push(tempTime / 2);
    });
    setInterval(sendUpdateRequest, 1000);
}

function sendUpdateRequest() {
    lastReqSendTime = Date.now();
    stompClient.send('/synchronize', {}, "0".repeat(50));
}


function currentAvgDelay() {
    return times.reduce((a, b) => a + b, 0) / times.length;
}