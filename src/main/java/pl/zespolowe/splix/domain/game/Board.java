package pl.zespolowe.splix.domain.game;
/***
 *
 * @author Michal Kalina
 *
 */

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
// Bimap
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class Board {
    protected static int x_size = 20;
    protected static int y_size = 20;
    private GameListenerState gls;

    Map<Point, Checker> fields = new HashMap<>();
    Map<Point, Checker> paths = new HashMap<>();

    Board(int x, int y) {
        x_size = x;
        y_size = y;
    }

  /*  public void clear_board() {
        fields = new HashMap<>();
        paths = new HashMap<>();
    }*/

    public void clear_players_sign(Checker checker) {
        paths.forEach((k, v) -> {
            if (v == checker) {
                paths.remove(k);
                //fields.containsKey(k) ? gls.changeField(fields.get(k),k) :  gls.changeField(k);
                if(fields.containsKey(k)){
                    gls.changeField(fields.get(k),k);
                }
                else {
                    gls.changeField(k);
                }
            }
        });
    }

    public void clear_field(Point p) {
        paths.forEach((k, v) -> {
            if (k == p) {
                fields.remove(k);
            }
        });
    }

    public void overtake(Checker checker) {
        //TODO: Znajdz co zostalo otoczone i to zamaluj chyba bym potrzebowal serwera do tego chlopaki
        //TODO: GLS CHANGE NA WIELU ELEMENTACH
        clear_players_sign(checker);
    }

    public void kill_player(Checker checker) {
        clear_players_sign(checker);
        fields.forEach((k, v) -> {
            if (v == checker) {
                fields.remove(k);
                gls.changeField(k);
            }
        });
    }

    public Checker respawnPlayer(int size_x, int size_y, Player p) {
        Point point0 = null;
        for (int i = 0; i < size_x * size_y / 5; i++) {
            //tu dalem od 2 do x-1 zeby sie nie respil przy scianie tak bardzo
            point0.x = ThreadLocalRandom.current().nextInt(2, size_x -1);
            point0.y = ThreadLocalRandom.current().nextInt(2, size_y -1);
            if (!fields.containsKey(point0)){
                Checker ch = new Checker(p, point0);
                fields.put(new Point(point0.x,point0.y),ch);
                //gls.changeField(ch,point0);

                fields.put(new Point(point0.x,point0.y+1),ch);
                gls.changeField(ch,new Point(point0.x,point0.y+1));

                fields.put(new Point(point0.x+1,point0.y+1),ch);
                gls.changeField(ch,new Point(point0.x+1,point0.y+1));

                fields.put(new Point(point0.x,point0.y+2),ch);
                gls.changeField(ch,new Point(point0.x,point0.y+2));

                fields.put(new Point(point0.x+1,point0.y+2),ch);
                gls.changeField(ch,new Point(point0.x+1,point0.y+2));

                gls.playersAdder(ch);
                return ch;
            }
        }
        return null;
    }

    /**
     * automatic
     */

    public Set<Checker> newMove(Set<Checker> checkers, GameListenerState glstmp) {
        gls = glstmp;
        for (Checker ch : checkers) {
            Point p = ch.next_turn();
            if (paths.containsKey(p)) {
                //drobna uwaga: zabijam tego ktorego slad zostal najechany
                kill_player(paths.get(p));
            }
            else if (!(p.x >= x_size && p.y >= y_size)) {
                ch.set_position(ch.next_turn());
                if (fields.get(p) == ch) {
                    overtake(ch);
                }
                paths.put(ch.getPoint(),ch);
                gls.playerMove(ch,true);
            }
        }
        return checkers;
    }

    public GameListenerState getGameListenerState(){
        GameListenerState tmp=gls;
        gls= null;
        return tmp;
    }
}