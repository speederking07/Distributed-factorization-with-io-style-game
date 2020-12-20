package pl.zespolowe.splix.domain.game;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class ColorTest {


    public static Set<Point> paintPolygon(Set<Point> points){
        Set<Point> rtr = points;
        ArrayList<Integer[]> myarr = new ArrayList<Integer[]>();

        int min=1000000;
        int max=0;
        for(Point p:points){
            if(p.y<min) min=p.y;
            if(p.y>max) max=p.y;
        }
        //System.out.println(max+" "+min);
        for(int i=min;i<=max;i++){
            System.out.println(i+"b");
            ArrayList<Integer> al = new ArrayList<>();
            for(Point p:points){
                //System.out.println(p);
                if(p.y==i){
                    al.add(p.x);
                    //System.out.println(p.x);
                }
            }
            int a=0;
            int k= al.get(0);
            boolean take =true;
            while(a<al.size()-1){
                //System.out.println("aaaaaa");
                int curr=al.get(a);
                int next=al.get(a+1);
                while(k<next) {
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
                take=!take;
                a++;
            }

        }

        return rtr;


    }
}
