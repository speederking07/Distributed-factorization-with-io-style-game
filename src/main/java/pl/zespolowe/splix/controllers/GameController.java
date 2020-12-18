package pl.zespolowe.splix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zespolowe.splix.domain.Player;
import pl.zespolowe.splix.domain.user.User;
import pl.zespolowe.splix.gamerules.Game;
import pl.zespolowe.splix.services.ActivePlayersRegistry;

import javax.servlet.http.HttpSession;

@Controller
public class GameController {

    @Autowired
    private ActivePlayersRegistry cache;

    @GetMapping(value = "/game/play")
    public ResponseEntity<String> play(@RequestParam("username") String username, Authentication auth, HttpSession session) {
        Player player = cache.getPlayer(session.getId());
        if (player == null) {
            try {
                if (auth != null && auth.isAuthenticated())
                    player = cache.addPlayer(session.getId(), (User) auth.getPrincipal());
                else
                    player = cache.addPlayer(session.getId(), username);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

        Game game = player.getGame();
        if (game != null && game.isActive()) {
            //TODO
        }
        return ResponseEntity.ok("1");
    }

    @MessageMapping("/stomp/{gameID}")
    @SendTo("/topic/stomp/{gameID}")
    public String handleMessage(SimpMessageHeaderAccessor accessor) {
        String sessionID = (String) accessor.getSessionAttributes().get("sessionId");
        Player player = cache.getPlayer(sessionID);
        //TODO
        return "";
    }
}
