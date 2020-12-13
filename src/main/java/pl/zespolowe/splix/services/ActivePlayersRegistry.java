package pl.zespolowe.splix.services;

import org.springframework.stereotype.Component;
import pl.zespolowe.splix.domain.Player;

import javax.security.auth.login.CredentialException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActivePlayersRegistry {
    private final Map<String, Player> players;

    public ActivePlayersRegistry() {
        players = new HashMap<>();
    }

    public void playerDisconnected(String sessionID) {
        Player player = players.remove(sessionID);
        if (player != null) player.resign();
    }

    public Player getPlayer(String sessionID) {
        return players.get(sessionID);
    }


    public Player addPlayer(String sessionID, String username) throws CredentialException {
        Player player = new Player(username);
        if (players.containsValue(player)) throw new CredentialException("username%Username already taken");
        players.put(sessionID, player);
        return player;
    }
}
