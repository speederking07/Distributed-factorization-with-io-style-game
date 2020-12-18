$(document).ready(function () {
    //$('#mainScreen').attr('visible', "True");

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

    refreshSettings();
});


function refreshSettings() {
    $.get({
        url: "/user/settings",
        dataType: "json",
        success: function (data) {
            alert("setting refreshed");
            //TODO
        }
    });
}

function updateSettings(settings) {
    // tak ma wygladac obiekt do tego requesta, odpowiednik UserSettingsDTO
    //settings = JSON.stringify({namesAbove:true, boardAnimation:true, dyingAnimation:true, colorsInCSV:"#010000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n#000000;#000000;#000000;#000000;#000000;#000000;\n"})
    settings = JSON.stringify(settings);
    $.post({
        url: "/user/settings",
        contentType: "application/json; charset=utf-8",
        data: settings,
        success: function () {
            //TODO
            alert("settings updated");
        },
        error: function () {
            //TODO
            alert("error")
        }
    });
}

function closeAllWindows() {
    $('.floatingBtn').each((k, v) => $(v).removeAttr('active'));
    $('.floatingDiv').each((k, v) => $(v).attr('visible', "False"));
}

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

function blockingPopups() {
    return $('.blockPopup[visible="True"]').length > 0
}

function popup(header, content, buttons) {
    let btn = "";
    for (let b of buttons) {
        console.log("()=>{(" + b[1] + ")(); closePopup();}");
        btn += "<input type='button' value='" + b[0] + "' onclick='(()=>{(" + b[1] + ")(); closePopup();})()'>"
    }
    $('body').append('<div class="dialogBox popup blockPopup">' +
        '<h1>' + header + '</h1>' +
        '<p>' + content + '</p>' +
        '<div>' + btn + '</div>' +
        '</div>');
}