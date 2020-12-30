package pl.zespolowe.splix;
import pl.zespolowe.splix.domain.game.overtakeElements.OverTake;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/***
 *
 * tak to wyglada:
  xxxxx
  x...x
  x...x
  x...x
  xxxxx
 */

public class PaintPolygonTest {
    public static void main(String[] args) {
        Set<Point> fields = new HashSet<>();
        Point p = new Point();
        //a
        p.x = 0;
        p.y = 0;
        fields.add(p);
        p = new Point();
        p.x = 1;
        p.y = 0;
        fields.add(p);
        p = new Point();
        p.x = 2;
        p.y = 0;
        fields.add(p);
        p = new Point();
        p.x = 3;
        p.y = 0;
        fields.add(p);
        p = new Point();
        p.x = 4;
        p.y = 0;
        fields.add(p);
        p = new Point();
        //a
        p.x = 0;
        p.y = 1;
        fields.add(p);
        p = new Point();
        p.x = 0;
        p.y = 2;
        fields.add(p);
        p = new Point();
        p.x = 0;
        p.y = 3;
        fields.add(p);
        p = new Point();
        //a
        p.x = 4;
        p.y = 1;
        fields.add(p);
        p = new Point();
        p.x = 4;
        p.y = 2;
        fields.add(p);
        p = new Point();
        p.x = 4;
        p.y = 3;
        fields.add(p);
        p = new Point();
        p.x = 2;
        p.y = 3;
        fields.add(p);
        p = new Point();
        p.x = 3;
        p.y = 3;
        fields.add(p);
        p = new Point();
        p.x = 1;
        p.y = 3;
        fields.add(p);
        Set<Point> result = OverTake.paintPolygon2(fields);
        System.out.println(fields.size());
        System.out.println(result.size());
    }

}
