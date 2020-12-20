package pl.zespolowe.splix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import pl.zespolowe.splix.domain.game.GameListener;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.domain.user.User;
import pl.zespolowe.splix.dto.IncomingMove;
import pl.zespolowe.splix.exceptions.GameException;
import pl.zespolowe.splix.services.ActivePlayersService;
import pl.zespolowe.splix.services.GameService;

import javax.servlet.http.HttpSession;

@Controller
public class GameController {

    @Autowired
    private ActivePlayersService playersService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate messaging;

    @GetMapping(value = "/game/play")
    public ResponseEntity<String> play(@RequestParam("username") String username, Authentication auth, HttpSession session) {
        Player player = playersService.getPlayer(session.getId());
        if (player == null) {
            try {
                if (auth != null && auth.isAuthenticated())
                    player = playersService.addPlayer(session.getId(), (User) auth.getPrincipal());
                else player = playersService.addPlayer(session.getId(), username);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

        player.resign();
        try {
            int gameID = gameService.addToGame(player);
            GameListener gameListener = move -> messaging.convertAndSend("/topic/stomp/" + gameID, move);
            gameService.addListener(gameID, gameListener);
            return ResponseEntity.ok(Integer.toString(gameID));
        } catch (GameException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    //WEBSOCKET


    @MessageMapping("/stomp/{gameID}")
    //@SendTo("/topic/stomp/{gameID}")
    public void handleMessage(@Payload IncomingMove move, SimpMessageHeaderAccessor accessor) {
        String sessionID = (String) accessor.getSessionAttributes().get("sessionID");
        Player player = playersService.getPlayer(sessionID);


    }


    @EventListener
    public void handleDisconnected(SessionDisconnectEvent event) {
        String sessionID = (String) StompHeaderAccessor.wrap(event.getMessage()).getSessionAttributes().get("sessionID");
        playersService.playerDisconnected(sessionID);
    }

    @EventListener
    public void handleUnsubscribe(SessionUnsubscribeEvent event) {
        String sessionID = (String) StompHeaderAccessor.wrap(event.getMessage()).getSessionAttributes().get("sessionID");
        playersService.playerDisconnected(sessionID);
    }
}
