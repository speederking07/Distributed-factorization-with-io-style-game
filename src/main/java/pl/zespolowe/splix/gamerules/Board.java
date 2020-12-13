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

/*        for(int i=0; i<x;i++){
            for(int j=0; j<y; j++){
                Point point = new Point();
                point.x=i;
                point.y=j;
                fields.put(point,null);
            }
        }*/
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




}
