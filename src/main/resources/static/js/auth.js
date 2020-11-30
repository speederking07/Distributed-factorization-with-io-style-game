function logout(){
    $.get({
        url: "/logout",
        success: function (data) {
            $('#mainScreen').attr('logged', 'False');
        }
    });
}

function register() {
    let registerData = objectifyForm($('#registerForm').serializeArray());
    if (registerData['password'] !== registerData['password2']) {
        signInMessage("Passwords are not identical");
        return;
    }
    delete registerData.password2;

    $.post({
        url: "/register",
        contentType: "application/json; charset=utf-8",
        dataType: "text",
        data: JSON.stringify(registerData),
        success: function (data) {
            $('#signInFBtn').trigger('click');
            let login = $('#logInLogin');
            let pass = $('#logInPassword');
            login.val(registerData['username']);
            pass.val(registerData['password']);
            logIn();
            login.val("");
            pass.val("");
        },
        error: function (data) {
            signInMessage(data.responseText);
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