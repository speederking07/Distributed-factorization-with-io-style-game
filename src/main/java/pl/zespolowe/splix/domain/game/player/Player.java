package pl.zespolowe.splix.domain.game.player;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.Game;
import pl.zespolowe.splix.domain.user.User;
import pl.zespolowe.splix.domain.user.UserSettings;
import pl.zespolowe.splix.dto.SimpleMove;

import javax.security.auth.login.CredentialException;
import java.util.Objects;

/**
 * Wrapper for User used in game logic
 *
 * @author Tomasz
 */
@Getter
public class Player {

    private final String username;
    @Setter
    private Game game;
    private String colorsInCsv;

    public Player(String username) throws CredentialException {
        if (username == null || username.length() == 0)
            throw new CredentialException("username%Username cannot be empty");
        this.username = username;
        this.colorsInCsv = UserSettings.getRandomColors();
    }

    public Player(User user) throws CredentialException {
        this(user.getUsername());
        this.colorsInCsv = user.getSettings().getColorsInCSV();
    }

    /**
     * Resign from current game
     *
     * @see Game
     */
    public void resign() {
        if (game != null) game.resign(this);
    }

    /**
     * Perform move in current game
     *
     * @param move
     */
    public void move(SimpleMove move) {
        if (game != null) game.move(move, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(username, player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                '}';
    }
}
