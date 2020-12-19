package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements ObservableGame  {
    private final int x_size=20;
    private final int y_size=20;
    private final int max_players=20;
    private int turn=0;

    private final Board board = new Board(x_size,y_size);


    @Getter
    private final int gameID;

    @Getter
    private Set<Checker> players;

    private final List<GameListener> listeners;


    public Game(int gameID) {
        players = new HashSet<>();
        listeners = new ArrayList<>();
        this.gameID = gameID;
    }
    GameListenerState gameListenerState;
    //TODO: co ma dawać do listenerow
    private void publishEvent(){
        listeners.forEach(l -> l.event(gameListenerState));
    }

    public void resign(Player player) {
        //TODO: co sie dzieje, gdy gracz sie rozlacza - Bereitet
        List<Checker> ch = players.stream()
                .filter(t->t.player==player)
                .collect(Collectors.toList());
        kill_player(ch.get(0));
    }

    public boolean isActive() {
        return players.size()<0 ? false : true;
    }

    public boolean containsPlayer(Player player) {
        return players.contains(player);
    }

    public boolean isFull() { return players.size()<max_players ? false : true; }

    //TODO: ma zwracać aktualny stan gry, a nie void - Bereitet
    public boolean join(Player p){
        if(!isFull()) {
            Point pin = board.findPlaceForRespawn(x_size,y_size);
            if(pin == null)return false;
            Checker ch=new Checker(p, pin);
            players.add(ch);
            gameListenerState.addPlayer(ch);
            return true;
        }
        else return false;
    }

    private void kill_player(Checker checker){
        players.remove(checker);
        board.kill_player(checker);
    }

    public void newTurn(){
        turn++;
        gameListenerState= new GameListenerState(turn);
        players=board.newMove(players, gameListenerState);
        gameListenerState=board.getGameListenerState();
        for(Checker ch: players){//dajmy mu liste checkerow i listnera on tam poustawia co trza i zwroci listnera i liste checkerow
            Point p=ch.next_turn();
            //if(board.newMove(p,ch)) ch.set_position(p);
            //gameListenerState=//to co zwroci
            //players=board.getCheckers();
        }
    }
    @Override
    public void addListener(@NonNull GameListener listener) {
        listeners.add(listener);
    }
}