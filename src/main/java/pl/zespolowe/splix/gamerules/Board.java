package pl.zespolowe.splix.gamerules;

// Bimap
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class Board {
    protected static int x_size=20;
    protected static int y_size=20;

    Map<Point, Checker> fields = new HashMap<Point, Checker>();
    Map<Point, Checker> paths = new HashMap<Point, Checker>();

    Board(int x, int y){
        x_size=x;
        y_size=y;
    }

    public static void clear_board(){ }
    public static void clear_players_sign(Checker checker){ }
    public static void clear_field(){ }
    public static void overtake(Checker checker){ }
    public void kill_player(){ }




}
