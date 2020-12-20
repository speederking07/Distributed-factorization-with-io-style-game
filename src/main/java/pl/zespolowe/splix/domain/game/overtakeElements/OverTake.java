package pl.zespolowe.splix.domain.game.overtakeElements;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OverTake {
    public static Set<Point> findPath(Point p1, Point p2, Set<Point> fields, Set<Point> path) {
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

    public static Set<Point> getCircuit(Set<Point> points) {
        Set<Point> rtr = new HashSet<>();
        for (Point point : points) {
            if (isBorderPoint(point, points)) {
                rtr.add(point);
                System.out.println("x" + point);
            }
        }
        return rtr;
    }

    public static Set<Point> paintPolygon(Set<Point> points) {
        Set<Point> rtr = points;
        ArrayList<Integer[]> myarr = new ArrayList<Integer[]>();

        int min = 1000000;
        int max = 0;
        for (Point p : points) {
            if (p.y < min) min = p.y;
            if (p.y > max) max = p.y;
        }
        //System.out.println(max+" "+min);
        for (int i = min; i <= max; i++) {
            System.out.println(i + "b");
            ArrayList<Integer> al = new ArrayList<>();
            for (Point p : points) {
                //System.out.println(p);
                if (p.y == i) {
                    al.add(p.x);
                    //System.out.println(p.x);
                }
            }
            int a = 0;
            int k = al.get(0);
            boolean take = true;
            while (a < al.size() - 1) {
                //System.out.println("aaaaaa");
                int curr = al.get(a);
                int next = al.get(a + 1);
                while (k < next) {
                    if (take) {
                        for (int j = curr; j <= next; j++) {
                            Point p = new Point();
                            p.y = i;
                            p.x = j;
                            rtr.add(p);
                        }
                    }
                    k++;
                }
                take = !take;
                a++;
            }

        }

        return rtr;


    }

    private static boolean isBorderPoint(Point p, Set<Point> points) {
        Set<Point> potentialPoints = new HashSet<>();
        int x = p.x;
        int y = p.y;

        //pointy potencjalne
        Point pot = new Point();
        pot.x = x + 1;
        pot.y = y;
        potentialPoints.add(pot);
        pot = new Point();

        pot.x = x - 1;
        pot.y = y;
        potentialPoints.add(pot);
        pot = new Point();

        pot.x = x;
        pot.y = y + 1;
        potentialPoints.add(pot);
        pot = new Point();

        pot.x = x;
        pot.y = y - 1;
        potentialPoints.add(pot);
        pot = new Point();

        pot.x = x + 1;
        pot.y = y + 1;
        potentialPoints.add(pot);
        pot = new Point();

        pot.x = x + 1;
        pot.y = y - 1;
        potentialPoints.add(pot);
        pot = new Point();

        pot.x = x - 1;
        pot.y = y + 1;
        potentialPoints.add(pot);
        pot = new Point();

        pot.x = x - 1;
        pot.y = y - 1;
        potentialPoints.add(pot);

        //koniec

        int i = 0;
        for (Point potPo : potentialPoints) {
            for (Point po : points) {
                if (potPo.equals(po)) {
                    i++;
                }
            }
        }
        return i != 8;

    }
}
