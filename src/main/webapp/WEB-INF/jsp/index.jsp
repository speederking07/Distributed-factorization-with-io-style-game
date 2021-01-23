<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/mainScreen.css" rel="stylesheet">
    <link href="css/icons.css" rel="stylesheet">
    <link href="css/config.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/@jaames/iro"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="js/wasm/factorization.js"></script>
    <script src="js/factorization.js"></script>
    <script src="js/auth.js"></script>
    <script src="js/consts.js"></script>
    <script src="js/animator.js"></script>
    <script src="js/boardView.js"></script>
    <script src="js/color.js"></script>
    <script src="js/pattern.js"></script>
    <script src="js/player.js"></script>
    <script src="js/script.js"></script>
    <script src="js/mainScreen.js"></script>
    <script src="js/config.js"></script>
    <script src="js/demo.js"></script>
    <script src="js/game.js"></script>
    <script src="js/connection.js"></script>
    <script src="js/stomp.js"></script>
    <script src="js/lib/swipe-listener.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <title>Document</title>
</head>
<body>
<canvas id="board" class="" height="400" width="500"></canvas>

<c:set value="False" var="logged"/>
<sec:authorize access="isAuthenticated()">
    <c:set value="True" var="logged"/>
</sec:authorize>

<div id="mainScreen" class="toggleVisibility fullScreen" visible="True" logged="${logged}">
    <div id="titleDiv">
        <span class="helper"></span><img src="img/title.png" alt="Logo">
    </div>
    <div id="loggedOutButtons" class="buttonsBox">
        <div id="signInFBtn" class="floatingBtn">
            <i class="icon-user-add"></i>
        </div>
        <div id="loginFBtn" class="floatingBtn">
            <i class="icon-login"></i>
        </div>
        <div class="leaderBoardFBtn floatingBtn">
            <i class="icon-trophy"></i>
        </div>
    </div>
    <div id="loggedButtons" class="buttonsBox">
        <div id="logoutFBtn" class="floatingBtn">
            <i class="icon-logout"></i>
        </div>
        <div id="configFBtn" class="floatingBtn">
            <i class="icon-palette"></i>
        </div>
        <div id="settingFBtn" class="floatingBtn">
            <i class="icon-tools"></i>
        </div>
        <div class="leaderBoardFBtn floatingBtn">
            <i class="icon-trophy"></i>
        </div>
    </div>
    <div id="loginDiv" class="popup floatingDiv" visible="False">
        <h1>Log In</h1>
        <form id="logInForm">
            <input id="logInLogin" name="username" type="text" placeholder="Login or e-mail">
            <input id="logInPassword" name="password" type="password" placeholder="Password">
        </form>
        <input id="logInBtn" type="button" value="Log In">
        <input id="showRecoverBtn" type="button" value="Recover Password">
    </div>
    <div id="passwordReminderDiv" class="popup floatingDiv" visible="False">
        <h1>Recover password</h1>
        <input id="recoverEmail" type="text" placeholder="Login">
        <input id="recoverBtn" type="button" value="Recover Password" onclick="recoverPassword()">
    </div>
    <div id="signInDiv" class="popup floatingDiv" visible="False">
        <h1>Create account</h1>
        <form id="registerForm">
            <input id="signInLogin" name="username" type="text" placeholder="Login">
            <input id="signInEmail" name="email" type="text" placeholder="email">
            <input id="signInPassword" name="password" type="password" placeholder="Password">
            <input id="signInPassword2" name="password2" type="password" placeholder="Repeat password">
        </form>
        <input id="signInBtn" type="button" value="Create account">
    </div>
    <div id="leaderBoardDiv" class="bigFloatingDiv popup blockPopup" visible="False">
        <h1>Highest score</h1>
        <table>
            <thead>
            <tr>
                <th>Rank</th>
                <th>Player</th>
                <th>Score</th>
            </tr>
            </thead>
            <tbody id="leaderboardBody">
            </tbody>
        </table>
        <input type="button" value="Close leader board" id="closeLeaderBoardBtn">
    </div>
    <div id="configDiv" class="bigFloatingDiv popup blockPopup" visible="False">
        <h1>Personalization</h1>
        <div class="configContentDiv">
            <h2>Choose your color</h2>
            <div id="chooseColorDiv">
                <label id="colorLabel" style="background: #ff0000;">
                    <input id="colorInput" type="color" value="#ff0000">
                </label>
                <input id="cloneColorBtn" type="button" value="Clone this color to your pattern">
            </div>
            <h2>Choose your pattern</h2>
            <div id="choosePatternDiv">
            </div>
            <input id="saveConfig" type="button" value="Save and quit">
            <input id="quitConfig" type="button" value="Quit without saving">
        </div>
    </div>
    <div id="settingDiv" class="bigFloatingDiv popup blockPopup" visible="False">
        <h1>Settings</h1>
        <div class="configContentDiv">
            <h2>Change password</h2>
            <input id="changePassword" type="password" placeholder="Password">
            <input id="changePassword2" type="password" placeholder="Repeat Password">
            <input id="changePasswordBtn" type="button" value="Change password" onclick="changePassword()">
            <h2>Change e-mail</h2>
            <input id="changeEmail" type="password" placeholder="E-mail">
            <input id="changeEmail2" type="password" placeholder="Repeat E-mail">
            <input id="changeEmailBtn" type="button" value="Change e-mail" onclick="changeEmail()">
            <h2>Graphical setting</h2>
            <label class="si si-checkbox">
                <input type="checkbox" id="boardAnimationsBox" checked/>
                <span class="si-label">Board animations</span>
            </label>
            <label class="si si-checkbox">
                <input type="checkbox" id="namesBox" checked/>
                <span class="si-label">Names above players</span>
            </label>
            <label class="si si-checkbox">
                <input type="checkbox" id="killingAnimationsBox" checked/>
                <span class="si-label">Players dying animations</span>
            </label>
            <input id="changeGraphicalSettings" type="button" value="Save and quit">
        </div>
    </div>

    <div id="startGameBar">
        <div id="startGameField" class="center">
            <c:choose>
                <c:when test="${pageContext.request.userPrincipal.name != null}">
                    <h1 id="enterNameH1">Start game</h1>
                    <div>
                        <input id="playerName" value="${pageContext.request.userPrincipal.name}" type="text" disabled>
                        <input id="startGameBtn" type="button" value="Start">
                    </div>
                </c:when>
                <c:otherwise>
                    <h1 id="enterNameH1">Enter player name</h1>
                    <div>
                        <input id="playerName" type="text">
                        <input id="startGameBtn" type="button" value="Start">
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
