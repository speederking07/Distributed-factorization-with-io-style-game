package pl.zespolowe.splix.domain.game.player;

import pl.zespolowe.splix.domain.game.GameListener;
import pl.zespolowe.splix.domain.game.GameListenerState;

import javax.security.auth.login.CredentialException;

public class Bot extends Player implements GameListener {
    public Bot(String username) throws CredentialException {
        super(username);
    }

    @Override
    public void event(GameListenerState move) {

    }
}
