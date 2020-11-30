function logout(){
    $.get({
        url: "/logout",
        success: function (data) {
            $('#mainScreen').attr('logged', 'False');
        }
    });
}

function register() {
    $('#signInLogin').removeAttr('invalid');
    $('#signInEmail').removeAttr('invalid');
    $('#signInPassword').removeAttr('invalid');
    $('#signInPassword2').removeAttr('invalid');
    let registerData = objectifyForm($('#registerForm').serializeArray());
    if (registerData['username'].length <= 5) {
        signInMessage("login%Username cannot be blank");
        return;
    }
    if (!emailVerify(registerData['email'])){
        signInMessage("email%Email is not correct");
        return;
    }
    if (registerData['password'] !== registerData['password2']) {
        signInMessage("password%Passwords are not identical");
        return;
    }
    if (registerData['password'].length <= 5) {
        signInMessage("password%Password must be length >= 5");
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
    $('#logInLogin').removeAttr('invalid');
    $('#logInPassword').removeAttr('invalid');
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

function objectifyForm(formArray) {
    let returnArray = {};
    for (let i = 0; i < formArray.length; i++) {
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}

function signInMessage(message) {
    const login = $('#signInLogin');
    const email = $('#signInEmail');
    const pass1 =  $('#signInPassword');
    const pass2 = $('#signInPassword2');
    let data = message.split('%');
    if(data.length < 1){
        pass1.val('');
        pass2.val('');
        popup('Error', message, [['ok', ()=>null]]);
    }
    else{
        if(data[0] === 'login'){
            login.attr('invalid', '');
            pass1.val('');
            pass2.val('');
            popup('Error', data[1], [['ok', ()=>null]]);
        } if(data[0] === 'email'){
            email.attr('invalid', '');
            pass1.val('');
            pass2.val('');
            popup('Error', data[1], [['ok', ()=>null]]);
        }else if (data[0] === 'password'){
            pass1.attr('invalid', '');
            pass2.attr('invalid', '');
            pass1.val('');
            pass2.val('');
            popup('Error', data[1], [['ok', ()=>null]]);
        }
    }
}

function logInMessage(message) {
    let data = message.split('%');
    if(data.length < 1){
        const login = $('#logInLogin');
        const password = $('#logInPassword');
        login.val('');
        password.val('');
        popup('Error', message, [['ok', ()=>null]]);
    }
    else{
        if(data[0] === 'password'){
            const password = $('#logInPassword');
            password.attr('invalid', '');
            password.val('');
            popup('Error', data[1], [['ok', ()=>null]]);
        } else if (data[0] === 'login'){
            const login = $('#logInLogin');
            const password = $('#logInPassword');
            login.attr('invalid', '');
            password.val('');
            popup('Error', data[1], [['ok', ()=>null]]);
        }
    }
}

function emailVerify(data){
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(data.toLowerCase());
}