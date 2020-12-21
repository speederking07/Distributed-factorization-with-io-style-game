package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.dto.SimpleMove;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
    private final Set<Checker> players;


    public Game(int gameID) {
        this.players = new HashSet<>();
        this.listeners = new ArrayList<>();
        this.gameID = gameID;
        this.board = new Board(x_size, y_size);
        this.turn = 0;
        this.gameListenerState = new GameListenerState(0);
        log.info("NEW GAME: " + gameID);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                newTurn();
            }
        }, 1000, 250);
    }

    //TODO: nie dostaję inforamcji o zmianie statustu pola
    private void publishEvent() {
        listeners.forEach(l -> l.event(gameListenerState));
    }

    private void killPlayer(Checker checker) {
        players.remove(checker);
        board.killPlayer(checker);
    }

    //TODO: brak automatycznego ruchu
    public void move(SimpleMove move, Player player) {
        //TODO: niech się dzieje magia
        Checker tmpChecker=null;
        for(Checker ch: players){
            if(ch.getPlayer().equals(player)){
                tmpChecker=ch;
            }
        }

        gameListenerState=board.move(tmpChecker,move.getMove(),gameListenerState);

        log.info("MOVE: " + player);
    }

    public void resign(Player player) {
        List<Checker> ch = players.stream()
                .filter(t -> t.getPlayer().equals(player))
                .collect(Collectors.toList());
        if (!(ch.get(0) == null))
            killPlayer(ch.get(0));
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
            Point point0=ch.getPoint();
            gameListenerState.changeField(ch, new Point(point0.x, point0.y + 1));
            gameListenerState.changeField(ch, new Point(point0.x + 1, point0.y + 1));
            gameListenerState.changeField(ch, new Point(point0.x, point0.y + 2));
            gameListenerState.changeField(ch, new Point(point0.x + 1, point0.y + 2));


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
        log.info("NEXT TURN");
        turn++;
        publishEvent();
        gameListenerState = new GameListenerState(turn);
        //TODO: to daje NullPointer
        //players = board.newMove(players, gameListenerState);
        //gameListenerState = board.getGameListenerState();
        //dajmy mu liste checkerow i listnera on tam poustawia co trza i zwroci listnera i liste checkerow

    }

    @Override
    public void addListener(@NonNull GameListener listener) {
        listeners.add(listener);
    }
}