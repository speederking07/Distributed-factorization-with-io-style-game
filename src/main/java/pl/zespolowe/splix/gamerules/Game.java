package pl.zespolowe.splix.gamerules;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    Board board = new Board(20,20);
    List<Checker> players = new ArrayList<>();

    public boolean add_player(Checker checker){
        if(players.size()<20) {
            players.add(checker);
            return true;
        }
        else return false;
    }

    private void kill_player(Checker checker){
        for(Checker ch : players){
            if (players.get_player==player) players.remove(ch);
        }

    }

    private void close_area(Checker checker){
        board.overtake(checker);
    }
    private void resurection_player(Player player){

    }

}
