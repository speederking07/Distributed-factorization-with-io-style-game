$( document ).ready(function () {
    //$('#mainScreen').attr('visible', "True");

    $('#loginFBtn').click(() => btnHandler($('#loginFBtn'), $('#loginDiv')));

    $('#signInFBtn').click(() => btnHandler($('#signInFBtn'), $('#signInDiv')));

    $('#configFBtn').click(() => btnHandler($('#configFBtn'), $('#configDiv')));

    $('#settingFBtn').click(() => btnHandler($('#settingFBtn'), $('#settingDiv')));

    $('#logInBtn').click(function () {
        $('#mainScreen').attr('logged', 'True');
        $('#loginDiv').attr('visible', "False");
        $('#loginFBtn').removeAttr('active');
    });

    $('#logoutFBtn').click(function () {
        $('#mainScreen').attr('logged', 'False');
    });

    $('#signInBtn').click(function () {
        loginVerify();
        emailVerify();
    });
});

function closeAllWindows() {
    $('.floatingBtn').each((k, v) => $(v).removeAttr('active'));
    $('#signInDiv').attr('visible', "False");
    $('#loginDiv').attr('visible', "False");
}

function loginVerify(){
    return true;
}

function emailVerify(){
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test($('#signInEmail').val().toLowerCase());
}

function btnHandler(btn, div){
    if (div.attr('visible') === 'False'){
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