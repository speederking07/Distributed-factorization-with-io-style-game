package pl.zespolowe.splix.domain.game;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    protected static int x_size = 20;
    protected static int y_size = 20;

    Map<Point, Checker> fields = new HashMap<>();
    Map<Point, Checker> paths = new HashMap<>();

    Board(int x, int y) {
        x_size = x;
        y_size = y;
    }

    public void clearBoard() {
        fields = new HashMap<>();
        paths = new HashMap<>();
    }

    public void clearPlayersSign(Checker checker) {
        paths.forEach((k, v) -> {
            if (v == checker) {
                paths.remove(k);
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
        clearPlayersSign(checker);
    }

    public void killPlayer(Checker checker) {
        paths.forEach((k, v) -> {
            if (v == checker) {
                paths.remove(k);
                fields.remove(k);
            }
        });
    }

    public Point findPlaceForRespawn(int size_x, int size_y) {
        Point point0 = null;
        for (int i = 0; i < size_x * size_y / 5; i++) {
            point0.x = ThreadLocalRandom.current().nextInt(0, size_x + 1);
            point0.y = ThreadLocalRandom.current().nextInt(0, size_y + 1);
            if (!fields.containsKey(point0)) return point0;
        }
        return null;
    }

    /**
     * automatic
     */

    public boolean newMove(Point p, Checker ch) {
        return true;
    }


}
