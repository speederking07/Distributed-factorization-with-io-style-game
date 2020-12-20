/**
 * Logging out user
 */
function logout() {
    $.get({
        url: "/logout",
        success: function (data) {
            $('#mainScreen').attr('logged', 'False');
            const name_bar = $('#playerName');
            const header = $('#enterNameH1');
            header.html('Enter player name');
            name_bar.val('');
            name_bar.removeAttr('disabled');
            refreshWebsocketConnection();
        }
    });
}

/**
 * Creating new account using filled input
 */
function register() {
    const login = $('#signInLogin');
    const email = $('#signInEmail');
    const pass1 = $('#signInPassword');
    const pass2 = $('#signInPassword2');
    login.removeAttr('invalid');
    email.removeAttr('invalid');
    pass1.removeAttr('invalid');
    pass2.removeAttr('invalid');
    let registerData = objectifyForm($('#registerForm').serializeArray());
    if (registerData['username'].length <= 5) {
        signInMessage("username%Username is too short");
        return;
    }
    if (!emailVerify(registerData['email'])) {
        signInMessage("email%Email is not correct");
        return;
    }
    if (registerData['password'] !== registerData['password2']) {
        signInMessage("password%Passwords are not identical");
        return;
    }
    if (registerData['password'].length <= 5) {
        signInMessage("password%Password must be length > 5");
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
            $('#logInLogin').val(registerData['username']);
            $('#logInPassword').val(registerData['password']);
            logIn();
            login.val('');
            email.val('');
            pass1.val('');
            pass2.val('');
        },
        error: function (data) {
            signInMessage(data.responseText);
        }
    })
}

/**
 * Logging user to server using filled input
 */
function logIn() {
    const login = $('#logInLogin');
    const name = login.val();
    const password = $('#logInPassword');
    login.removeAttr('invalid');
    password.removeAttr('invalid');
    $.post({
        url: "/login",
        data: $('#logInForm').serialize(),
        dataType: "text",
        success: function (data) {
            const name_bar = $('#playerName');
            const header = $('#enterNameH1');
            header.html('Start game');
            name_bar.val(name);
            name_bar.attr('disabled', '');
            login.val('');
            password.val('');
            $('#mainScreen').attr('logged', 'True');
            $('#loginDiv').attr('visible', "False");
            $('#loginFBtn').removeAttr('active');
            refreshSettings();
            refreshWebsocketConnection();
        },
        error: function (data) {
            logInMessage(data.responseText);
        }
    })
}

/**
 * Change user password using filled input
 */
function changePassword() {
    let var1 = $("#changePassword").val();
    let var2 = $("#changePassword2").val();
    if (var1 !== var2) {
        settingsMessage("password%Passwords are not identical");
        return;
    }
    $.post({
        url: "/user/password",
        contentType: 'text/plain',
        data: var1,
        success: function (data) {
            alert(data);
        },
        error: function (data) {
            settingsMessage(data.responseText);
        }
    });
}

/**
 * Changed user email
 */
function changeEmail() {
    let var1 = $("#changeEmail").val();
    let var2 = $("#changeEmail2").val();
    if (var1 !== var2) {
        settingsMessage("email%Emails are not identical");
        return;
    }
    $.post({
        url: "/user/email",
        contentType: 'text/plain',
        data: var1,
        success: function (data) {
            alert(data);
        },
        error: function (data) {
            settingsMessage(data.responseText);
        }
    });
}


/**
 * Function changing array to abject
 *
 * @param formArray - array to objectify
 */
function objectifyForm(formArray) {
    let returnArray = {};
    for (let i = 0; i < formArray.length; i++) {
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}

/**
 * Communicate user problem with singing in
 * @param message - field%message
 */
function signInMessage(message) {
    const login = $('#signInLogin');
    const email = $('#signInEmail');
    const pass1 = $('#signInPassword');
    const pass2 = $('#signInPassword2');
    let data = message.split('%');
    if (data.length < 1) {
        pass1.val('');
        pass2.val('');
        popup('Error', message, [['ok', () => null]]);
    } else {
        if (data[0] === 'username') {
            login.attr('invalid', '');
            pass1.val('');
            pass2.val('');
            popup('Error', data[1], [['ok', () => null]]);
        }
        if (data[0] === 'email') {
            email.attr('invalid', '');
            pass1.val('');
            pass2.val('');
            popup('Error', data[1], [['ok', () => null]]);
        } else if (data[0] === 'password') {
            pass1.attr('invalid', '');
            pass2.attr('invalid', '');
            pass1.val('');
            pass2.val('');
            popup('Error', data[1], [['ok', () => null]]);
        }
    }
}

/**
 * Communicate user problem with logging in
 * @param message - field%message
 */
function logInMessage(message) {
    let data = message.split('%');
    if (data.length < 1) {
        const login = $('#logInLogin');
        const password = $('#logInPassword');
        login.val('');
        password.val('');
        popup('Error', message, [['ok', () => null]]);
    } else {
        if (data[0] === 'password') {
            const password = $('#logInPassword');
            password.attr('invalid', '');
            password.val('');
            popup('Error', data[1], [['ok', () => null]]);
        } else if (data[0] === 'username') {
            const login = $('#logInLogin');
            const password = $('#logInPassword');
            login.attr('invalid', '');
            password.val('');
            popup('Error', data[1], [['ok', () => null]]);
        }
    }
}

/**
 * Communicate user problem with changing account problem
 * @param message - field%message
 */
function settingsMessage(message) {
    let data = message.split('%');
    if (data[0] === "password") {
        $("#changePassword").val('');
        $("#changePassword2").val('');
        popup('Error', data[1], [['ok', () => null]]);
    } else if (data[0] === "email") {
        popup('Error', data[1], [['ok', () => null]]);
    }
}

/**
 * Checks is email is valid email
 * @param data - email
 * @returns {boolean}
 */
function emailVerify(data) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(data.toLowerCase());
}