package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;
import pl.zespolowe.splix.domain.game.player.Player;

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
    private GameListenerState gameListenerState=new GameListenerState(0);
    private int turn;
    private Set<Checker> players;

    public Game(int gameID) {
        this.players = new HashSet<>();
        this.listeners = new ArrayList<>();
        this.gameID = gameID;
        this.board = new Board(x_size, y_size);
        this.turn = 0;
    }

    //TODO: co ma dawać do listenerow - ok
    private void publishEvent() {
        listeners.forEach(l -> l.event(gameListenerState));
    }

    public void resign(Player player) {
        //TODO: co sie dzieje, gdy gracz sie rozlacza - Bereitet
        List<Checker> ch = players.stream()
                .filter(t -> t.getPlayer().equals(player))
                .collect(Collectors.toList());
        if(!(ch==null)){
        kill_player(ch.get(0));}
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

    //TODO: ma zwracać aktualny stan gry, a nie void - Bereitet
    public boolean join(Player p) {
        if (!isFull()) {
            Checker ch = board.respawnPlayer(x_size, y_size, p);
            if (ch == null) return false;
            players.add(ch);
            gameListenerState.addPlayer(ch);
            return true;
        } else return false;
    }

    private void kill_player(Checker checker) {
        players.remove(checker);
        board.killPlayer(checker);
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