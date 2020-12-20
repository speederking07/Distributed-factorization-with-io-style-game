package pl.zespolowe.splix.domain.game;
/***
 *
 * @author Michal Kalina
 *
 */

import pl.zespolowe.splix.domain.game.overtakeElements.OverTake;
import pl.zespolowe.splix.domain.game.player.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

class Board {
    private final Map<Point, Checker> fields;
    private final Map<Point, Checker> paths;
    protected int x_size;
    protected int y_size;
    private GameListenerState gls=new GameListenerState(0);

    Board(int x, int y) {
        this.x_size = x;
        this.y_size = y;
        this.fields = new HashMap<>();
        this.paths = new HashMap<>();
    }

  /*  public void clear_board() {
        fields = new HashMap<>();
        paths = new HashMap<>();
    }*/

    public void clearPlayersSign(Checker checker) {
        paths.forEach((k, v) -> {
            if (v.equals(checker)) {
                paths.remove(k);
                //fields.containsKey(k) ? gls.changeField(fields.get(k),k) :  gls.changeField(k);
                if (fields.containsKey(k)) gls.changeField(fields.get(k), k);
                else gls.changeField(k);

            }
        });
    }

    public void clearField(Point p) {
        paths.forEach((k, v) -> {
            if (k.equals(p)) fields.remove(k);
        });
    }

    public void overtake(Checker checker) {
        Point p2 = checker.getPoint();
        Point p1 = checker.getPath();
        Set<Point> myFields = new HashSet<>();
        Set<Point> finalMyFields = myFields;
        Set<Point> finalMyPaths = myFields;
        fields.forEach((k, v) -> {
            if (v.equals(checker)) finalMyFields.add(k);
        });
        paths.forEach((k, v) -> {
            if (v.equals(checker)) finalMyPaths.add(k);
        });
        //zdobacz obwod (return Set<Point>)
        myFields = OverTake.getCircuit(finalMyFields);
        Set<Point> myPath = new HashSet<>();
        //wsadz obwod w findpath
        myPath = OverTake.findPath(p1, p2, myFields, new HashSet<>());
        //polacz sciezke z tym co zwroci find path
        myPath.addAll(finalMyPaths);
        myPath.addAll(finalMyFields);
        //zamaluj pole z tego co polaczyles
        Set<Point> taken = OverTake.paintPolygon(myPath);
        for (Point tmp : taken) {
            fields.put(tmp, checker);
            gls.changeField(checker, tmp);
        }
        clearPlayersSign(checker);
    }

    public void killPlayer(Checker checker) {
        clearPlayersSign(checker);
        fields.forEach((k, v) -> {
            if (v == checker) {
                fields.remove(k);
                gls.killPlayer(v);
            }
        });
    }

    public Checker respawnPlayer(int size_x, int size_y, Player p) {
        Point point0 = new Point();
        for (int i = 0; i < size_x * size_y / 5; i++) {
            //tu dalem od 2 do x-1 zeby sie nie respil przy scianie tak bardzo
            point0.x = ThreadLocalRandom.current().nextInt(2, size_x - 1);
            point0.y = ThreadLocalRandom.current().nextInt(2, size_y - 1);
            if (!fields.containsKey(point0)) {
                Checker ch = new Checker(p, point0);
                fields.put(new Point(point0.x, point0.y), ch);
                //gls.changeField(ch,point0);

                fields.put(new Point(point0.x, point0.y + 1), ch);
                gls.changeField(ch, new Point(point0.x, point0.y + 1));

                fields.put(new Point(point0.x + 1, point0.y + 1), ch);
                gls.changeField(ch, new Point(point0.x + 1, point0.y + 1));

                fields.put(new Point(point0.x, point0.y + 2), ch);
                gls.changeField(ch, new Point(point0.x, point0.y + 2));

                fields.put(new Point(point0.x + 1, point0.y + 2), ch);
                gls.changeField(ch, new Point(point0.x + 1, point0.y + 2));

                gls.addPlayer(ch);
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
            Point oldPoint = ch.getPoint();
            Point p = ch.nextTurn();
            if (paths.containsKey(p)) {
                //drobna uwaga: zabijam tego ktorego slad zostal najechany
                killPlayer(paths.get(p));
            } else if (!(p.x >= x_size && p.y >= y_size)) {
                //jesli byl u siebie i nie jest to nowy path
                //jesli byl u siebie i jest to nic
                if (fields.get(oldPoint).equals(ch)) {//byl u siebie
                    if (fields.get(p).equals(ch)) {//i jest u siebie
                        ch.setPoint(ch.nextTurn());
                        gls.playerMove(ch, false);
                    } else {
                        ch.setPath(p);
                        ch.setPoint(ch.nextTurn());
                        gls.playerMove(ch, true);
                        paths.put(ch.getPoint(), ch);
                    }
                }
                //jesli nie byl u siebie i jest to overtake
                //jesli nie byl u siebie i jest to kolejny path
                else {//nie byl u siebie
                    if (fields.get(p).equals(ch)) {// i jest u siebie
                        ch.setPoint(ch.nextTurn());
                        overtake(ch);
                        ch.setPath(new Point());
                        gls.playerMove(ch, false);
                    } else {//i nie jest u siebie
                        ch.setPoint(ch.nextTurn());
                        paths.put(ch.getPoint(), ch);
                        gls.playerMove(ch, true);
                    }
                }
            }
        }
        return checkers;
    }

    public GameListenerState getGameListenerState() {
        GameListenerState tmp = gls;
        gls = null;
        return tmp;
    }
}