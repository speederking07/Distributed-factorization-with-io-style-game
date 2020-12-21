<%--
  Created by IntelliJ IDEA.
  User: Tomasz
  Date: 21.12.2020
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Password Recovery</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function recover() {
            alert("revocer")
            const pass1 = $("#password1").val()
            const pass2 = $("#password2").val()

            if (pass1.length <= 5) {
                alert("za krotkie");
                return;
            }
            if (pass1 !== pass2) {
                alert("różne");
                return;
            }

            $.post({
                url: window.location.href,
                contentType: "text/plain",
                data: pass1,
                success: function (data) {
                    alert("sukces");
                },
                error: function (data) {
                    alert(data.responseText);
                }
            });
        }
    </script>
</head>
<body>
<div id="passwordRecovery">
    <input id="password1" type="password" placeholder="Password">
    <input id="password2" type="password" placeholder="Password">
    <input type="button" value="Change" onclick="recover()">
</div>
</body>
</html>
