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
            url: "/leaders",
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
                        '</tr>')
                    i++;
                }
            }
        });
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
});

function objectifyForm(formArray) {
    var returnArray = {};
    for (var i = 0; i < formArray.length; i++) {
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
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