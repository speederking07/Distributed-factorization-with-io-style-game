package pl.zespolowe.splix.domain.game;

import javax.security.auth.login.CredentialException;

public class Bot extends Player implements GameListener {
    public Bot(String username) throws CredentialException {
        super(username);
    }

    @Override
    public void event(GameListenerState move) {

    }
}
