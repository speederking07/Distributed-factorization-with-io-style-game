package pl.zespolowe.splix.gamerules;
import java.util.concurrent.ThreadLocalRandom;
// Bimap
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class Board {
    protected static int x_size=20;
    protected static int y_size=20;

    Map<Point, Checker> fields = new HashMap<>();
    Map<Point, Checker> paths = new HashMap<>();

    Board(int x, int y){
        x_size=x;
        y_size=y;
    }

    public void clear_board(){
        fields = new HashMap<Point, Checker>();
        paths = new HashMap<Point, Checker>();
    }
    public void clear_players_sign(Checker checker){
        paths.forEach((k, v) -> {
            if(v==checker){
                paths.remove(k);
            }
        });
    }
    public void clear_field(Point p){
        paths.forEach((k, v) -> {
            if(k==p){
                fields.remove(k);
            }
        });
    }
    public void overtake(Checker checker){
        //TODO: Znajdz co zostalo otoczone i to zamaluj chyba bym potrzebowal serwera do tego chlopaki
        clear_players_sign(checker);
    }
    public void kill_player(Checker checker){
        paths.forEach((k, v) -> {
            if(v==checker){
                paths.remove(k);
                fields.remove(k);
            }
        });
    }
    public Point find_place_for_respawn(int size_x, int size_y){
        Point point0 = null;
        for (int i=0;i<size_x*size_y/5;i++){
            point0.x = ThreadLocalRandom.current().nextInt(0, size_x + 1);
            point0.y = ThreadLocalRandom.current().nextInt(0, size_y + 1);
            if(!fields.containsKey(point0)) return point0;
        }
        return null;
    }

    /** automatic
     *
     */

    public boolean new_move(Point p, Checker ch){
        if(paths.containsKey(p)){
            kill_player(ch);
            return true;
        }
        if(p.x>=x_size || p.y>=y_size){
            return false;
        }
        if(fields.get(p)==ch){
            overtake(ch);
        }
        return true;
    }




}
