package pl.zespolowe.splix.domain;

import javax.security.auth.login.CredentialException;

public class Bot extends Player implements GameListener{
    public Bot(String username) throws CredentialException {
        super(username);
    }

    @Override
    public void event() {

    }
}
