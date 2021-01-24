package pl.zespolowe.splix.domain.game.overtakeElements;

import org.junit.Assert;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/***
 *
 * @author KalinaMichal
 * testy dla funkcji z klasy OverTake
 * Ogolny przebieg klasy - czynności wykonywane podczas przejecia pól:
 *     zdobacz obwod (return Set<Point>)
 *     podaj obwod w findpath
 *     polacz sciezke z tym co zwroci find path
 *     zamaluj pole z tego co polaczyles
 */
public class OverTakeTest {
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
    @org.junit.Test
    public void getCurvesTest() {

        //given
        Set<Point> myWalk = new LinkedHashSet<>();
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
        StringWriter writer = new StringWriter();
        PrintWriter out1 = new PrintWriter(writer);

        //when
        out1.println(OverTake.getCurves(myWalk));

        //then
        Assert.assertEquals(writer.toString(),"[java.awt.Point[x=0,y=0], java.awt.Point[x=2,y=0], java.awt.Point[x=2,y=1], java.awt.Point[x=0,y=1], java.awt.Point[x=0,y=3]]\n");
    }
    /****
     *
     * Tests for drowing polygons 1
     *
     *
     */
    @org.junit.Test
    public void PaintPolygon1Test() {

        //given
        Set<Point> fields = new HashSet<>();
        Point p = new Point();
        p.x = 4;
        p.y = 10;
        fields.add(p);
        p = new Point();
        p.x = 5;
        p.y = 10;
        fields.add(p);
        p = new Point();
        p.x = 6;
        p.y = 10;
        fields.add(p);
        p = new Point();
        p.x = 7;
        p.y = 10;
        fields.add(p);
        p = new Point();
        p.x = 8;
        p.y = 10;
        fields.add(p);
        p = new Point();
        //a
        p.x = 8;
        p.y = 11;
        fields.add(p);
        p = new Point();
        p.x = 8;
        p.y = 12;
        fields.add(p);
        p = new Point();
        p.x = 8;
        p.y = 13;
        fields.add(p);
        p = new Point();
        //a
        p.x = 7;
        p.y = 13;
        fields.add(p);
        p = new Point();
        p.x = 6;
        p.y = 13;
        fields.add(p);
        p = new Point();
        p.x = 5;
        p.y = 13;
        fields.add(p);
        p = new Point();
        p.x = 4;
        p.y = 12;
        fields.add(p);
        p = new Point();
        p.x = 4;
        p.y = 11;
        fields.add(p);
        p = new Point();
        p.x = 5;
        p.y = 12;
        fields.add(p);
        p = new Point();
        p.x = 5;
        p.y = 11;
        fields.add(p);
        Set<Point> result = OverTake.paintPolygon2(fields);
        StringWriter writer = new StringWriter();
        PrintWriter out1 = new PrintWriter(writer);

        //when
        out1.println(result);

        //then
        Assert.assertEquals(writer.toString(),"[java.awt.Point[x=7,y=10], java.awt.Point[x=5,y=13], java.awt.Point[x=6,y=10], java.awt.Point[x=5,y=10], java.awt.Point[x=4,y=12], java.awt.Point[x=7,y=13], java.awt.Point[x=4,y=11], java.awt.Point[x=4,y=10], java.awt.Point[x=5,y=12], java.awt.Point[x=6,y=13], java.awt.Point[x=5,y=11], java.awt.Point[x=8,y=13], java.awt.Point[x=8,y=12], java.awt.Point[x=8,y=11], java.awt.Point[x=8,y=10], java.awt.Point[x=7,y=11], java.awt.Point[x=7,y=12], java.awt.Point[x=6,y=12], java.awt.Point[x=6,y=11]]\n");
        Assert.assertEquals(fields.size(),15);
        Assert.assertEquals(result.size(),19);
    }
    /****
     *
     * Tests for drowing polygons 2
     *
     *
     */
    @org.junit.Test
    public void PaintPolygon2Test() {

        //given
        Set<Point> fields = new HashSet<>();
        Point p = new Point();
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
        StringWriter writer = new StringWriter();
        PrintWriter out1 = new PrintWriter(writer);

        //when
        out1.println(result);

        //then
        Assert.assertEquals(writer.toString(),"[java.awt.Point[x=0,y=0], java.awt.Point[x=2,y=0], java.awt.Point[x=0,y=2], java.awt.Point[x=4,y=1], java.awt.Point[x=3,y=0], java.awt.Point[x=4,y=3], java.awt.Point[x=1,y=3], java.awt.Point[x=1,y=0], java.awt.Point[x=4,y=0], java.awt.Point[x=0,y=1], java.awt.Point[x=4,y=2], java.awt.Point[x=3,y=3], java.awt.Point[x=0,y=3], java.awt.Point[x=2,y=3], java.awt.Point[x=2,y=1], java.awt.Point[x=2,y=2], java.awt.Point[x=3,y=2], java.awt.Point[x=3,y=1], java.awt.Point[x=1,y=2], java.awt.Point[x=1,y=1]]\n");
        Assert.assertEquals(fields.size(),14);
        Assert.assertEquals(result.size(),20);
    }

    @org.junit.Test
    public void circuitTest() {

        //given
        Set<Point> fields = new HashSet<>();
        Point p = new Point();
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

        //when
        Set<Point> result = OverTake.getCircuit(fields);
        StringWriter writer = new StringWriter();
        PrintWriter out1 = new PrintWriter(writer);

        //when
        out1.print(result);

        //then
        Assert.assertEquals(writer.toString(),"[java.awt.Point[x=0,y=0], java.awt.Point[x=2,y=0], java.awt.Point[x=0,y=2], java.awt.Point[x=2,y=2], java.awt.Point[x=1,y=0], java.awt.Point[x=0,y=1], java.awt.Point[x=2,y=1], java.awt.Point[x=1,y=2]]");
        out1.print(OverTake.paintPolygon2(result));
        Assert.assertEquals(writer.toString(),"[java.awt.Point[x=0,y=0], java.awt.Point[x=2,y=0], java.awt.Point[x=0,y=2], java.awt.Point[x=2,y=2], java.awt.Point[x=1,y=0], java.awt.Point[x=0,y=1], java.awt.Point[x=2,y=1], java.awt.Point[x=1,y=2]][java.awt.Point[x=0,y=0], java.awt.Point[x=2,y=0], java.awt.Point[x=0,y=2], java.awt.Point[x=2,y=2], java.awt.Point[x=1,y=0], java.awt.Point[x=0,y=1], java.awt.Point[x=2,y=1], java.awt.Point[x=1,y=2], java.awt.Point[x=1,y=1]]");
    }

    @org.junit.Test
    public void findPathTest() {
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
        StringWriter writer = new StringWriter();
        PrintWriter out1 = new PrintWriter(writer);

        //when
        out1.println(OverTake.findPath(p, po, fields, new HashSet<>()));

        //then
        Assert.assertEquals(writer.toString(), "[java.awt.Point[x=4,y=2], java.awt.Point[x=4,y=1], java.awt.Point[x=4,y=0], java.awt.Point[x=2,y=0], java.awt.Point[x=1,y=0], java.awt.Point[x=0,y=0], java.awt.Point[x=0,y=1], java.awt.Point[x=0,y=2], java.awt.Point[x=4,y=3], java.awt.Point[x=3,y=0]]\n");
        Assert.assertEquals(fields.size(), 1);
    }

}
