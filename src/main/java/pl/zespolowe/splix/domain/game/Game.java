package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.dto.SimpleMove;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Game implements ObservableGame {
    private final static int x_size = 20;
    private final static int y_size = 20;
    private final static int max_players = 20;

    @Getter
    private final int gameID;

    @Getter
    private final List<GameListener> listeners;
    private final Board board;
    private GameListenerState gameListenerState;
    private int turn;
    private Set<Checker> players;

    public Game(int gameID) {
        this.players = new HashSet<>();
        this.listeners = new ArrayList<>();
        this.gameID = gameID;
        this.board = new Board(x_size, y_size);
        this.turn = 0;
        this.gameListenerState = new GameListenerState(0);
    }

    private void publishEvent() {
        listeners.forEach(l -> l.event(gameListenerState));
    }

    private void kill_player(Checker checker) {
        players.remove(checker);
        board.killPlayer(checker);
    }

    public void move(SimpleMove move, Player player) {
        //TODO: niech się dzieje magia
        System.out.println("MOVE: " + player);
    }

    public void resign(Player player) {
        List<Checker> ch = players.stream()
                .filter(t -> t.getPlayer().equals(player))
                .collect(Collectors.toList());
        if (!(ch.get(0) == null))
            kill_player(ch.get(0));
    }

    public boolean isActive() {
        return !players.isEmpty();
    }

    public boolean containsPlayer(Player player) {
        return players.stream().anyMatch(c -> c.getPlayer().equals(player));
    }

    public boolean isFull() {
        return players.size() >= max_players;
    }

    public boolean join(Player p) {
        if (!isFull()) {
            Checker ch = board.respawnPlayer(x_size, y_size, p);
            if (ch == null) return false;
            players.add(ch);
            gameListenerState.addPlayer(ch);
            return true;
        }
        return false;
    }


    public GameCurrentState getGameCurrentState() {
        //TODO: Na pełną wersję ta metoda za zwracać ten objekt reprezentujący obecny stan gry dla nowych graczy
        return null;
    }

    public void newTurn() {
        turn++;
        gameListenerState = new GameListenerState(turn);
        players = board.newMove(players, gameListenerState);
        gameListenerState = board.getGameListenerState();
        //dajmy mu liste checkerow i listnera on tam poustawia co trza i zwroci listnera i liste checkerow

    }

    @Override
    public void addListener(@NonNull GameListener listener) {
        listeners.add(listener);
    }
}