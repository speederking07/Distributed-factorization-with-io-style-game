package pl.zespolowe.splix;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class PointsTest {
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
        Point po = new Point();
        po.x = 0;
        po.y = 3;

        Set<Point> walk = findPath(p, po, fields, new HashSet<>());
        for (Point w : walk) {
            System.out.println(w.x + "<-x, y->" + w.y);
        }
        System.out.print(walk.size() + "Hello World");
    }

    //zdobacz obwod (return Set<Point>)
    //wsadz obwod w findpath
    //polacz sciezke z tym co zwroci find path
    //zamaluj pole z tego co polaczyles


    private static Set<Point> findPath(Point p1, Point p2, Set<Point> fields, Set<Point> path) {
        if (p1.equals(p2)) {
            return path;
        }
        path.add(p1);
        fields.remove(p1);
        System.out.println(p1 + "aa" + p2);
        Set<Point> area = new HashSet<>();
        Point tmpPoint = new Point();
        tmpPoint.x = p1.x + 1;
        tmpPoint.y = p1.y;
        area.add(tmpPoint);
        tmpPoint = new Point();
        tmpPoint.x = p1.x - 1;
        tmpPoint.y = p1.y;
        area.add(tmpPoint);
        tmpPoint = new Point();
        tmpPoint.x = p1.x;
        tmpPoint.y = p1.y + 1;
        area.add(tmpPoint);
        tmpPoint = new Point();
        tmpPoint.x = p1.x;
        tmpPoint.y = p1.y - 1;
        area.add(tmpPoint);
        for (Point p : area) {
            System.out.println(p + "p");
            for (Point pointField : fields) {
                //System.out.println("pointField "+pointField);
                if (pointField.equals(p)) {
                    Set<Point> pathTmp = findPath(p, p2, fields, path);
                    if (pathTmp.size() > 0) {
                        return pathTmp;
                    }
                }
            }
        }
        System.out.println("bb");
        return new HashSet<>();
    }

}
