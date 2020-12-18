package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game implements ObservableGame {
    private static final int x_size = 20;
    private static final int y_size = 20;
    private static final int max_players = 20;

    @Getter
    private final int gameID;

    private final Set<GameListener> listeners;
    private final Board board;
    public List<Checker> players;

    public Game(int gameID) {
        this.board = new Board(x_size, y_size);
        this.players = new ArrayList<>();
        this.listeners = new HashSet<>();
        this.gameID = gameID;
    }

    //TODO: co ma dawać do listenerow
    private void publishEvent() {
        listeners.forEach(GameListener::event);
    }

    private void closeArea(Checker checker) {
        board.overtake(checker);
    }

    public boolean addPlayer(Player p) {
        if (players.size() < max_players) {
            Point pin = board.findPlaceForRespawn(x_size, y_size);
            if (pin == null) return false;
            players.add(new Checker(p, pin));
            return true;
        } else return false;
    }

    private void killPlayer(Checker checker) {
        for (Checker ch : players) {
            if (ch == checker) {
                board.killPlayer(ch);
                players.remove(ch);
            }
        }
    }

    public void nextTurn() {
        for (Checker ch : players) {
            Point p = ch.next_turn();
            if (board.newMove(p, ch)) ch.set_position(p);
        }
    }

    public void resign(Player player) {
        //TODO: co sie dzieje, gdy gracz sie rozlacza

        players.removeIf(c -> c.player.equals(player));
    }

    //TODO: ma zwracać aktualny stan gry, a nie void
    public void join(Player player) {

    }

    public boolean isActive() {
        //TODO: czy gra trwa
        return true;
    }

    public boolean containsPlayer(Player player) {
        return players.stream().anyMatch(c -> c.player.equals(player));
    }

    public boolean isFull() {
        //TODO
        return true;
    }


    @Override
    public void addListener(@NonNull GameListener listener) {
        listeners.add(listener);
    }
}
