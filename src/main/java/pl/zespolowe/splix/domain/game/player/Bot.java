package pl.zespolowe.splix.domain.game.player;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.GameListener;
import pl.zespolowe.splix.domain.game.GameListenerState;

import javax.security.auth.login.CredentialException;

public class Bot extends Player implements GameListener {
    //@Getter
    //@Setter
    //boolean isRoadSet = false;//to tu nie powinno byc tzn powinno ale inaczej TODO: zapytac was

    public Bot(String username) throws CredentialException {
        super(username);
    }

    @Override
    public void event(GameListenerState move) {

    }
}
