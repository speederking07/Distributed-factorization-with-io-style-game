package pl.zespolowe.splix.services;

import org.springframework.stereotype.Service;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.domain.user.User;

import javax.security.auth.login.CredentialException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomasz
 */
@Service
public class ActivePlayersService {
    private final Map<String, Player> players;

    public ActivePlayersService() {
        players = new HashMap<>();
    }

    /**
     * Handles Player disconnection
     *
     * @param sessionID sessionID to which Player was assigned
     */
    public void playerDisconnected(String sessionID) {
        Player player = players.remove(sessionID);
        if (player != null) player.resign();
    }

    /**
     * @param sessionID
     * @return Player assigned to session
     */
    public Player getPlayer(String sessionID) {
        return players.get(sessionID);
    }

    /**
     * Create and add Player with assigned sessionID to registry
     *
     * @param sessionID
     * @param user
     * @return new Player object
     * @throws CredentialException Player with this username already registered
     * @see Player
     */
    public Player addPlayer(String sessionID, User user) throws CredentialException {
        Player player = new Player(user);
        if (players.containsValue(player)) throw new CredentialException("username%Username already taken");
        players.put(sessionID, player);
        return player;
    }

    /**
     * @see ActivePlayersService#addPlayer(String, User)
     */
    public Player addPlayer(String sessionID, String username) throws CredentialException {
        Player player = new Player(username);
        if (players.containsValue(player)) throw new CredentialException("username%Username already taken");
        players.put(sessionID, player);
        return player;
    }
}
