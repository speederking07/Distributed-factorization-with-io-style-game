package pl.zespolowe.splix.gamerules;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private static final int x_size=20;
    private static final int y_size=20;
    private static final int max_players=20;

    private static Board board = new Board(x_size,y_size);
    public static List<Checker> players = new ArrayList<>();

    public boolean add_player(Player p){
        if(players.size()<max_players) {
            Point pin = board.find_place_for_respawn(x_size,y_size);
            if(pin == null)return false;
            players.add(new Checker(p, pin));
            return true;
        }
        else return false;
    }

    private void kill_player(Checker checker){
        for(Checker ch : players){
            if (ch==checker) {
                board.kill_player(ch);
                players.remove(ch);
            }
        }
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
