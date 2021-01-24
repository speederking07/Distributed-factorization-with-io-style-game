package pl.zespolowe.splix;

import pl.zespolowe.splix.domain.game.overtakeElements.OverTake;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class CircuitTest {
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
//aa
        p.x = 0;
        p.y = 1;
        fields.add(p);
        p = new Point();
        p.x = 1;
        p.y = 1;
        fields.add(p);
        p = new Point();
        p.x = 2;
        p.y = 1;
        fields.add(p);
        //aa
        p = new Point();
        p.x = 0;
        p.y = 2;
        fields.add(p);
        p = new Point();
        p.x = 1;
        p.y = 2;
        fields.add(p);
        p = new Point();
        p.x = 2;
        p.y = 2;
        fields.add(p);

        /*p=new Point();
        p.x=2;
        p.y=1;*/

        //System.out.println(isBorderPoint(p, fields));
        Set<Point> result = OverTake.getCircuit(fields);

        /*for (Point pe : result) {
            System.out.println(pe);
        }*/

        /*ColorTest ct = new ColorTest();
        Set<Point> result2 = ColorTest.paintPolygon(result);
        for (Point pe : result2) {
            System.out.println(pe);
        }*/

    }

    public static ArrayList<Point> getCurves(Set<Point> points) {
        ArrayList<Point> rtr = new ArrayList<>();
        for (Point p : points) {
            if (isCurvePoint(p, points)) {
                rtr.add(p);
            }
            System.out.println(p);
        }
        return rtr;
    }

    public static boolean isCurvePoint(Point p0, Set<Point> points) {
        ArrayList<Point> rtr = new ArrayList<>();
        boolean north = false;
        boolean south = false;
        boolean west = false;
        boolean east = false;

        for (Point p : points) {
            int pX = (int) p.getX();
            int pY = (int) p.getY();
            int p0X = (int) p0.getX();
            int p0Y = (int) p0.getY();
            if (p0X == pX) {
                if (p0Y + 1 == pY) {
                    south = true;
                }
                if (p0Y - 1 == pY) {
                    north = true;
                }
            }
            if (p0Y == pY) {
                if (p0X + 1 == pX) {
                    east = true;
                }
                if (p0X - 1 == pX) {
                    west = true;
                }
            }
        }
        //if(1<bToI(west)+bToI(east)+bToI(north)+bToI(south) && ){return true;}
        return (north || south) && (east || west);

    }

    /*private static int bToI(boolean b) {
        return b ? 1 : 0;
    }*/

    private static Set<Point> getCircuit(Set<Point> points) {
        Set<Point> rtr = new LinkedHashSet<>();
        for (Point point : points) {
            if (isBorderPoint(point, points)) {
                rtr.add(point);
                System.out.println("x" + point);
            }
        }
        return rtr;
    }

    static boolean isBorderPoint(Point p, Set<Point> points) {
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
