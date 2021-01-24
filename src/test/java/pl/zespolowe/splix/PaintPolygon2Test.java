package pl.zespolowe.splix;

import pl.zespolowe.splix.domain.game.overtakeElements.OverTake;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

public class PaintPolygon2Test {
    public static void main(String[] args) {
        Set<Point> fields = new HashSet<>();
        Point p = new Point();
        //a
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
        System.out.println(result.size());

        StringWriter writer = new StringWriter();
        PrintWriter out1 = new PrintWriter(writer);
        out1.println(result);
    }

}