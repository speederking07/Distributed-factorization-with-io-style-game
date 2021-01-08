package pl.zespolowe.splix.domain.game;
/***
 *
 * @author Michal Kalina
 *
 */

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.overtakeElements.OverTake;
import pl.zespolowe.splix.domain.game.player.Bot;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.dto.AddPlayer;
import pl.zespolowe.splix.dto.Move;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Board {
    private final Map<Point, Checker> fields;
    private final Map<Point, Checker> paths;
    protected int x_size;
    protected int y_size;
    @Getter
    @Setter
    private GameListenerState gls;

    Board(int x, int y) {
        this.x_size = x;
        this.y_size = y;
        this.fields = new HashMap<>();
        this.paths = new LinkedHashMap<>();//musi byc linked abym wiedzial gdzie on doklasnie chodzil
        this.gls = new GameListenerState(0);
    }

  /*  public void clear_board() {
        fields = new HashMap<>();
        paths = new HashMap<>();
    }*/

    public void clearPlayersSign(Checker checker) {

        ArrayList<Point> arrayPaths = new ArrayList<>();
        paths.forEach((k, v) -> {
            if (v.equals(checker)) {
                arrayPaths.add(k);
                //paths.remove(k);
                //fields.containsKey(k) ? gls.changeField(fields.get(k),k) :  gls.changeField(k);
                //if (fields.containsKey(k)) gls.changeField(fields.get(k), k);
                //else gls.changeField(k);
            }
        });
        for(Point p: arrayPaths){
            paths.remove(p);
        }
    }

    public void clearField(Point p) {
        paths.forEach((k, v) -> {
            if (k.equals(p)) fields.remove(k);
        });
    }

    public void overtake(Checker checker) {
        Point p2 = checker.getPoint();
        Point p1 = checker.getPath();
        Set<Point> myFields = new LinkedHashSet<>();
        Set<Point> finalMyFields = myFields;
        Set<Point> finalMyPaths = myFields;
        fields.forEach((k, v) -> {
            if (v.equals(checker)) finalMyFields.add(k);
        });
        paths.forEach((k, v) -> {
            if (v.equals(checker)) finalMyPaths.add(k);
        });
        //zdobywa obwod jako Set<Point> uporzadkowany
        myFields = OverTake.getCircuit(finalMyFields);
        Set<Point> myPath;//myPath to obwod ktory nalezy zamalowac
        //teraz szukamy sciezki z tego obwodu(szukamy jej szybko, nie szukam najkrotszej)
        myPath = OverTake.findPath(p1, p2, myFields, new LinkedHashSet<>());
        //polacz sciezke z tym co zwroci find path
        myPath.addAll(finalMyPaths);
        //teraz mam liste pol ktore nalezy zamalowac
        //maluje je
        for(Point p: myPath ){
            System.out.println("to wchodzi: "+p);
        }
        Set<Point> taken = OverTake.paintPolygon2(myPath);
        //na prosbe Pana Marka biore tez liste samych zakretow do wyslania
        ArrayList<Point> curves = OverTake.getCurves(myPath);
        //gls.changeFields(checker,curves);
        for (Point tmp : taken) {
            fields.put(tmp, checker);
            gls.changeField(checker.getPlayer().getUsername(), tmp);
            System.out.println("zamalowalem: "+tmp);
        }
        clearPlayersSign(checker);
    }

    public void killPlayer(Checker checker) {
        clearPlayersSign(checker);
        Iterator<Map.Entry<Point, Checker>> iterator = fields.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Point, Checker> entry = iterator.next();
            if (entry.getValue().equals(checker)) {
                gls.killPlayer(entry.getValue());
                iterator.remove();
            }
        }
        if(fields.size()>1){
            fields.forEach((k, v) -> {
                if (v == checker) {
                    fields.remove(k);
                    gls.killPlayer(v);
                }
            });
        }
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

                String name=ch.getPlayer().getUsername();
                fields.put(new Point(point0.x, point0.y + 1), ch);
                gls.changeField(name, new Point(point0.x, point0.y + 1));

                fields.put(new Point(point0.x + 1, point0.y + 1), ch);
                gls.changeField(name, new Point(point0.x + 1, point0.y + 1));

                fields.put(new Point(point0.x, point0.y + 2), ch);
                gls.changeField(name, new Point(point0.x, point0.y + 2));

                fields.put(new Point(point0.x + 1, point0.y + 2), ch);
                gls.changeField(name, new Point(point0.x + 1, point0.y + 2));

                gls.addPlayer(ch);
                return ch;
            }
        }
        return null;
    }

    /**
     * automatic
     */
    public void move(Checker ch, Direction dir) {
        Point oldPoint = ch.getPoint();
        int x =oldPoint.x;
        int y = oldPoint.y;
        switch (dir) {
            case EAST -> x++;
            case WEST -> x--;
            case NORTH -> y--;
            case SOUTH -> y++;
        }
        Point p = new Point();
        p.x=x;
        p.y=y;
        if(p.getX()<0 || p.getY()<0 || p.getX() >= x_size || p.getY() >= y_size){
            killPlayer(ch);
            return;
        }
        System.out.println(p);
        if (paths.containsKey(p)) {
            //drobna uwaga: zabijam tego ktorego slad zostal najechany
            killPlayer(paths.get(p));
        } else{
            //jesli byl u siebie i nie jest to nowy path
            //jesli byl u siebie i jest to nic
                    if(fields.get(oldPoint)!= null && fields.get(oldPoint).equals(ch)) {//byl u siebie
                        ch.setPath(oldPoint);//ustawiam Patha jakby stąd zaczynal wyjazd z w next turn to bede wiedzial skad wyjechal
                        if (fields.get(p)!= null && fields.get(p).equals(ch)) {//i jest u siebie
                            ch.setPoint(p);
                            gls.playerMove(ch, false);
                        } else {
                            ch.setPoint(p);
                            gls.playerMove(ch, true);
                            paths.put(ch.getPoint(), ch);
                        }
                }

            //jesli nie byl u siebie i jest to overtake
            //jesli nie byl u siebie i jest to kolejny path
            else {//nie byl u siebie
                if (fields.get(p)!=null && fields.get(p).equals(ch)) {// i jest u siebie
                    ch.setPoint(p);
                    overtake(ch);
                    ch.setPath(p);
                    gls.playerMove(ch, false);
                } else {//i nie jest u siebie
                    ch.setPoint(p);
                    paths.put(ch.getPoint(), ch);
                    gls.playerMove(ch, true);
                }
            }
        }
        paths.forEach((k, v) -> {
            System.out.println(k);
        });

    }

    public GameCurrentState setInfoForNewPlayer(Checker ch, GameCurrentState gcs) {
        ArrayList<Point> points= new ArrayList<>();
        Set<Point> localPath;
        fields.forEach((k, v) -> {
            if(v==ch)points.add(k);
        });
        Set<Point>  finalLocalPath = new LinkedHashSet<>();
        paths.forEach((k, v) -> {
            if(v==ch) finalLocalPath.add(k);
        });
        localPath= OverTake.findPath(ch.getPath(),ch.getPoint(), finalLocalPath, new LinkedHashSet<>());
        ArrayList<Point> curves = OverTake.getCurves(localPath);
        gcs.addPlayer(ch.getPlayer(), points, curves, ch.getPoint());
        return gcs;
    }

    public void printGls(){
         int turn = gls.getTurn();
         List<String> killedPlayers=gls.getKilledPlayers();
         Map<String,ArrayList<int[]>> changes=gls.getChanges();
         List<Move> moves=gls.getMoves();
         List<AddPlayer> addedPlayers=gls.getAddedPlayers();
        System.out.println("###################################");
        System.out.println("info co zostalo wyslane w "+turn+" turze");
        System.out.println("killed players: "+killedPlayers);
        System.out.println("moves: "+moves);
        System.out.println("addedPlayers"+addedPlayers);
        System.out.println("field's changes"+changes);
        System.out.println("###################################");
    }

    /**
     * Ruch botow
     */
    public void botMove(Checker ch) {
        move(ch,Direction.EAST);
                //TODO: TU SIE DZIEJE MAGIA ROBIENIA RUCHU PIONEM
    }
}