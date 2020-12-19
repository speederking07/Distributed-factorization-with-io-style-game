package pl.zespolowe.splix.domain.game;

public interface GameListener {
    void event(GameListenerState move);
    //void event(); //TODO: klasa ruchu, którą dostanie listener
}
