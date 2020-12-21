let conn; //TODO: Delete after testing

$(document).ready(function () {
    $('#loginFBtn').click(() => btnHandler($('#loginFBtn'), $('#loginDiv')));

    $('#signInFBtn').click(() => btnHandler($('#signInFBtn'), $('#signInDiv')));

    $('#configFBtn').click(() => btnHandler($('#configFBtn'), $('#configDiv')));

    $('#settingFBtn').click(() => btnHandler($('#settingFBtn'), $('#settingDiv')));

    $('#showRecoverBtn').click(() => btnHandler($('#showRecoverBtn'), $('#passwordReminderDiv')));

    $('.leaderBoardFBtn').click(() => btnHandler($('.leaderBoardFBtn'), $('#leaderBoardDiv')));

    $('.leaderBoardFBtn').click(function () {
        $.get({
            url: "/game/leaders",
            dataType: "json",
            success: function (data) {
                let table = $('#leaderboardBody');
                table.empty();
                let i = 1;
                for (const name in data) {
                    table.append('<tr>' +
                        '<td>' + i + '</td>' +
                        '<td>' + name + '</td>' +
                        '<td>' + data[name] + '</td>' +
                        '</tr>');
                    i++;
                }
            }
        });
    });

    $('#closeLeaderBoardBtn').click(function () {
        $('.leaderBoardFBtn').removeAttr('active');
        $('#leaderBoardDiv').attr('visible', 'False');
    });

    $('#logoutFBtn').click(function () {
        logout();
    });

    $('#logInBtn').click(function () {
        logIn();
    });

    $('#signInBtn').click(function () {
        register();
    });

    $('#startGameBtn').click(() => {
        $('#mainScreen').attr("visible", "False");
        Connection.getGameConnection($('#playerName').val()).then(connection => {
            conn = connection;
            if (typeof demo !== 'undefined') {
                demo.kill();
            }
            demo = new Game(connection, document.getElementById('board'), $('#playerName').val(), closeGame)
        }).catch(error => {
            $('#mainScreen').attr("visible", "True");
            popup('Error', error, [['ok', () => {
            }]]);
        })
    });
});

/**
 * Close all visible windows
 */
function closeAllWindows() {
    $('.floatingBtn').each((k, v) => $(v).removeAttr('active'));
    $('.floatingDiv').each((k, v) => $(v).attr('visible', "False"));
}

/**
 * Handle clicking on specific floating button
 * @param btn - handler of button
 * @param div - div to appear after clicking
 */
function btnHandler(btn, div) {
    if (div.attr('visible') === 'False') {
        if (!blockingPopups()) {
            closeAllWindows();
            btn.attr('active', 'True');
            div.attr('visible', 'True');
        }
    } else {
        btn.removeAttr('active');
        div.attr('visible', "False");
    }
}

/**
 * Checks if there is visible window with class blockPopup
 * @returns {boolean}
 */
function blockingPopups() {
    return $('.blockPopup[visible="True"]').length > 0
}

/**
 * Display popup window
 *
 * @param header{String} - title of popup
 * @param content{String}  - message of popup
 * @param buttons - list of buttons under message in format [['text_on_btn_1', function_to_call], ['text2', function_2]]
 */
function popup(header, content, buttons) {
    let btn = "";
    for (let b of buttons) {
        btn += "<input type='button' value='" + b[0] + "' onclick='(()=>{(" + b[1] + ")(); closePopup();})()'>"
    }
    $('body').append('<div class="dialogBox popup blockPopup">' +
        '<h1>' + header + '</h1>' +
        '<p>' + content + '</p>' +
        '<div>' + btn + '</div>' +
        '</div>');
}

/**
 * Closes popup
 */
function closePopup() {
    $('.dialogBox').remove();
}

/**
 * Close game and show menu after dying
 */
function closeGame() {
    $('#mainScreen').attr("visible", "True");
    setTimeout(() => {
        if (typeof demo !== 'undefined') {
            demo.kill()
        }
        demo = new Demo(document.getElementById('board'));
        demo.start();
    }, 2000);
}