$( document ).ready(function () {
    $('#mainScreen').attr('visible', "True");

    $('#loginFBtn').click(function () {
        let div = $('#loginDiv');
        if (div.attr('visible') === 'False'){
            closeAllWindows();
            $(this).attr('active', 'True');
            div.attr('visible', 'True');
        } else {
            $(this).removeAttr('active');
            div.attr('visible', "False");
        }
    });

    $('#signInFBtn').click(function () {
        let div = $('#signInDiv');
        if (div.attr('visible') === 'False'){
            closeAllWindows();
            $(this).attr('active', 'True');
            div.attr('visible', 'True');
        } else {
            $(this).removeAttr('active');
            div.attr('visible', "False");
        }
    });

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
    $('#signInFBtn').removeAttr('active');
    $('#loginFBtn').removeAttr('active');
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