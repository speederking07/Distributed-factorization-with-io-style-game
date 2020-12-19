package pl.zespolowe.splix.domain.game;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PointsTest {
        public static void main(String[] args){
            Checker ch = null;
            Set<Point> fields = new HashSet<>();
            Point p=new Point();
            //a
            p.x=0;
            p.y=0;
            fields.add(p);
            p.x=1;
            p.y=0;
            fields.add(p);
            p.x=2;
            p.y=0;
            fields.add(p);

            p.x=3;
            p.y=0;
            fields.add(p);

            p.x=4;
            p.y=0;
            fields.add(p);

            //a
            p.x=0;
            p.y=1;
            fields.add(p);
            p.x=0;
            p.y=2;
            fields.add(p);
            p.x=0;
            p.y=3;
            fields.add(p);
            //a
            p.x=4;
            p.y=1;
            fields.add(p);
            p.x=4;
            p.y=2;
            fields.add(p);
            p.x=4;
            p.y=3;
            fields.add(p);
            Point po= new Point();
            po.x=0;
            po.y=3;
            Set<Point> walk =findPath(p,po,fields);
            System.out.print("Hello World");
        }

        private static Set<Point> findPath(Point p1, Point p2, Set<Point> fields) {
            if (p1.x < p2.x) {
                if (p1.y < p2.y) {
                    //gora
                    Point tmp = p1;
                    tmp.y = p1.y + 1;
                    Set<Point> tmpFields = fields;
                    tmpFields.remove(p1);
                    Set<Point> tmpPoints = findPath(tmp, p2, tmpFields);
                    if (tmpPoints == null) { //prawo
                        tmp = p1;
                        tmp.x = p1.x + 1;
                        tmpPoints = findPath(tmp, p2, tmpFields);
                        if (tmpPoints == null) {//lewo
                            tmp = p1;
                            tmp.x = p1.x - 1;
                            tmpPoints = findPath(tmp, p2, tmpFields);
                            if (tmpPoints == null) {//dol
                                tmp = p1;
                                tmp.y = p1.y - 1;
                                tmpPoints = findPath(tmp, p2, tmpFields);
                                if (tmpPoints == null) return null;
                            }

                        }
                        //idz w gore, prawo,lewo,dol-jest
                    } else {
                        //idz w dol, prawo,lewo,gore
                    }
                } else {
                    if (p1.y < p2.y) {
                        //idz w gore,lewo,prawo,dol
                    } else {
                        //idz w dol, lewo,prawo,gore
                    }
                }
            }
        }
}
