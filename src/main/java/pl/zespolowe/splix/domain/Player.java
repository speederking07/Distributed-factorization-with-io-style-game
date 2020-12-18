package pl.zespolowe.splix.domain;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.user.User;
import pl.zespolowe.splix.domain.user.UserSettings;

import javax.security.auth.login.CredentialException;
import java.util.Objects;

public class Player {

    @Getter
    private final String username;

    @Getter
    @Setter
    private Game game;

    @Getter
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


    public void resign() {
        if (game != null) game.resign(this);
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
}
