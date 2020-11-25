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
            dataType:"json",
            success: function (data) {
                let table = $('#leaderboardBody');
                table.empty();
                let i = 1;
                for (const name in data) {
                    table.append('<tr>')
                    table.append('<td>' + i + '</td>')
                    table.append('<td>' + name + '</td>')
                    table.append('<td>' + data[name] + '</td>')
                    table.append('</tr>')
                    i++;
                }
            }
        });
    });

    $('#logoutFBtn').click(function () {
        $.get({
            url: "/logout",
            success: function (data) {
                $('#mainScreen').attr('logged', 'False');
            }
        });
    });
    $('#logInBtn').click(function () {
        logIn()
    });
    $('#signInBtn').click(function () {
        register()
    });

});

function objectifyForm(formArray) {
    var returnArray = {};
    for (var i = 0; i < formArray.length; i++) {
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}

function register() {
    let registerData = objectifyForm($('#registerForm').serializeArray());
    if (!(registerData['password'].localeCompare(registerData['password2']))) {
        signInMessage("Passwords are not identical");
        //return;
    }
    delete registerData.password2;

    $.post({
        url: "/register",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify(registerData),
        success: function (data) {
            signInMessage(JSON.parse(data));
            $('#logInLogin').val(registerData['username']);
            $('#logInPassword').val(registerData['password']);
            logIn();
        },
        error: function (data) {
            signInMessage(JSON.parse(data));
        }
    })
}

function logIn() {
    $.post({
        url: "/login",
        data: $('#logInForm').serialize(),
        dataType: "text",
        success: function (data) {
            $('#mainScreen').attr('logged', 'True');
            $('#loginDiv').attr('visible', "False");
            $('#loginFBtn').removeAttr('active');
        },
        error: function (data) {
            logInMessage(data.responseText);
        }
    })
}

function signInMessage(message) {
    alert(message);
}

function logInMessage(message) {
    alert(message);
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