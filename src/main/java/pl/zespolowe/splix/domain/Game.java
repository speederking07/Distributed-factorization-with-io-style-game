package pl.zespolowe.splix.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    @Getter
    private final int gameID;

    @Getter
    private final Set<Player> players;

    private final List<GameListener> listeners;


    public Game(int gameID) {
        players = new HashSet<>();
        listeners = new ArrayList<>();
        this.gameID = gameID;
    }

    //TODO: co ma dawać do listenerow
    private void publishEvent(){
        listeners.forEach(GameListener::event);
    }

    public void resign(Player player) {
        //TODO: co sie dzieje, gdy gracz sie rozlacza

        players.remove(player);
    }

    //TODO: ma zwracać aktualny stan gry, a nie void
    public void join(Player player ){

    }

    public boolean isActive() {
        //TODO: czy gra trwa
        return true;
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public boolean isFull() {
        return true;
    }
}
