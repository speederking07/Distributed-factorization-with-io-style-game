package pl.zespolowe.splix.gamerules;
import lombok.Getter;
import pl.zespolowe.splix.domain.GameListener;
import pl.zespolowe.splix.domain.Player;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    private final int x_size=20;
    private final int y_size=20;
    private final int max_players=20;

    private Board board = new Board(x_size,y_size);


    @Getter
    private final int gameID;

    @Getter
    private final Set<Checker> players;

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
            Point pin = board.find_place_for_respawn(x_size,y_size);
            if(pin == null)return false;
            players.add(new Checker(p, pin));
            return true;
        }
        else return false;
    }

    private void kill_player(Checker checker){
        players.remove(checker);
        board.kill_player(checker);
    }

    private void close_area(Checker checker){
        board.overtake(checker);
    }

    public void next_turn(){
        for(Checker ch: players){
            Point p=ch.next_turn();
            if(board.new_move(p,ch)) ch.set_position(p);
        }
    }
}
