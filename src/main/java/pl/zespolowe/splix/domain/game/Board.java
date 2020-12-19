package pl.zespolowe.splix.domain.game;

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
                //gls.
                //TODO: GLS USUN SLAD ALE SPRAWDZ CZY W FIELDS COS JEST NA TYM MIEJSCU
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
        paths.forEach((k, v) -> {
            if (v == checker) {
                paths.remove(k);
                fields.remove(k);
                //TODO: GLS.ADD CHANGE
            }
        });
    }

    public Point findPlaceForRespawn(int size_x, int size_y) {
        Point point0 = null;
        for (int i = 0; i < size_x * size_y / 5; i++) {
            point0.x = ThreadLocalRandom.current().nextInt(0, size_x + 1);
            point0.y = ThreadLocalRandom.current().nextInt(0, size_y + 1);
            if (!fields.containsKey(point0)) return point0;//TODO: GLS CHANGE
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
                kill_player(ch);
            }
            else if (!(p.x >= x_size && p.y >= y_size)) {
                ch.set_position(ch.next_turn());
                if (fields.get(p) == ch) {
                    overtake(ch);
                }
                gls.playersAdder(ch);
                gls.playerMove(ch);
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