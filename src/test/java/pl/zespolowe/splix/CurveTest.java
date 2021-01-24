package pl.zespolowe.splix;

import pl.zespolowe.splix.domain.game.overtakeElements.OverTake;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class CurveTest {
    public static void main(String[] args) {
        Set<Point> myWalk = new LinkedHashSet<>();
        /**
         * ###################
         * V - ide w dol
         * T - ide w gore
         * > - ide w prawo
         * < - ide w lewo
         * X - koniec
         * ##################
         *
         * > > V
         * V < <
         * V
         * X
         *
         **/
        Point p = new Point();
        p.x = 0;
        p.y = 0;
        myWalk.add(p);
        p = new Point();
        p.x = 1;
        p.y = 0;
        myWalk.add(p);
        p = new Point();
        p.x = 2;
        p.y = 0;
        myWalk.add(p);
        p = new Point();
        p.x = 2;
        p.y = 1;
        myWalk.add(p);
        p = new Point();
        p.x = 1;
        p.y = 1;
        myWalk.add(p);
        p = new Point();
        p.x = 0;
        p.y = 1;
        myWalk.add(p);
        p = new Point();
        p.x = 0;
        p.y = 2;
        myWalk.add(p);
        p = new Point();
        p.x = 0;
        p.y = 3;
        myWalk.add(p);

        for (Point pr : OverTake.getCurves(myWalk)) {
            System.out.println(pr);
        }
        System.out.println(OverTake.getCurves(myWalk));


    }


}
