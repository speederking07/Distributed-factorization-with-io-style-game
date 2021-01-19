<%--
  Created by IntelliJ IDEA.
  User: Tomasz
  Date: 21.12.2020
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Password Recovery</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="../css/style.css" rel="stylesheet">
    <link href="../css/mainScreen.css" rel="stylesheet">
    <link href="../css/icons.css" rel="stylesheet">
    <link href="../css/config.css" rel="stylesheet">
    <link href="../css/recover.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/@jaames/iro"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="../js/consts.js"></script>
    <script src="../js/animator.js"></script>
    <script src="../js/boardView.js"></script>
    <script src="../js/color.js"></script>
    <script src="../js/pattern.js"></script>
    <script src="../js/player.js"></script>
    <script src="../js/demo.js"></script>
    <script src="../js/mainScreen.js"></script>
    <script>
        function recover() {
            const pass1 = $("#password1").val();
            const pass2 = $("#password2").val();

            if (pass1.length <= 5) {
                $("#password1").val('');
                $("#password2").val('');
                popup('Error', "Passwords must have at least 6 characters", [['ok', () => {
                }]]);
                return;
            }
            if (pass1 !== pass2) {
                $("#password1").val('');
                $("#password2").val('');
                popup('Error', "Passwords are different", [['ok', () => {
                }]]);
                return;
            }

            $.post({
                url: window.location.href,
                contentType: "text/plain",
                data: pass1,
                success: function (data) {
                    popup('Success', "You have successfully changed your password", [['Go to game', () => {
                        window.location.replace("/");
                    }]]);
                },
                error: function (data) {
                    $("#password1").val('');
                    $("#password2").val('');
                    popup('Error', data.responseText, [['ok', () => {
                    }]]);
                }
            });
        }

        $(document).ready(function () {
            $(window).trigger('resize');
            let demo = new Demo(document.getElementById('board'));
            demo.start();
        });

        $(window).resize(function () {
            $('#board').attr('width', $(window).width());
            $('#board').attr('height', $(window).height());
        });
    </script>
</head>
<body>
<div id="passwordRecovery" class="bigFloatingDiv">
    <h1> Change password </h1>
    <input id="password1" type="password" placeholder="New password">
    <input id="password2" type="password" placeholder="Repeat password">
    <input type="button" value="Change" onclick="recover()">
</div>
<canvas id="board" class="" height="400" width="500"></canvas>
</body>
</html>
