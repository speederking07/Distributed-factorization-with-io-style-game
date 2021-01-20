package pl.zespolowe.splix.domain.game.overtakeElements;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class OverTake {
    /***
     * Znajdowanie sciezki
     *
     * @param p1
     * @param p2
     * @param fields
     * @param path
     * @return
     */
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
        if(area!=null){
            for (Point p : area) {
                System.out.println(p + "p");
                for (Point pointField : fields) {
                    //System.out.println("pointField "+pointField);
                    if (pointField.equals(p)) {
                        if(p!=null){
                            Set<Point> pathTmp = findPath(p, p2, fields, path);
                            if (pathTmp.size() > 0) {
                                return pathTmp;
                            }

                        }
                    }
                }
            }
        }

        System.out.println("bb");
        return new HashSet<>();
    }

    /***
     * Wyzanczanie obwodow
     *
     * @param points
     * @return
     */
    public static Set<Point> getCircuit(Set<Point> points) {
        Set<Point> rtr = new LinkedHashSet<>();
        for (Point point : points) {
            if (isBorderPoint(point, points)) {
                rtr.add(point);
                System.out.println("x" + point);
            }
        }
        return rtr;
    }

    /***
     * Wyznaczanie zakretow
     *
     * @param points
     * @return
     */
    public static ArrayList<Point> getCurves(Set<Point> points) {
        ArrayList<Point> arrPoints = new ArrayList<>(points);//aby szybciej sie dostawac do elementow
        ArrayList<Point> rtr = new ArrayList<>();//to co zwroce
        int len = points.size();
        if(len<=2){ return arrPoints;}
        if(len>2){rtr.add(arrPoints.get(0));}
        for (int i = 1; i < len - 2; i++) {
            Point p0 = arrPoints.get(i);
            Point pf = arrPoints.get(i + 1);//forward point
            Point pb = arrPoints.get(i - 1);//backward point
            if (!(pf.getX() == pb.getX()) && !(pf.getY() == pb.getY())) {
                rtr.add(p0);
            }
        }
        rtr.add(arrPoints.get(len-1));
        return rtr;
    }

    //metoda do wyjebania ale szkoda mi usuwac bo bylo napracowanko
    public static Set<Point> paintPolygon(Set<Point> points) {
        Set<Point> rtr = points;
        ArrayList<Integer[]> myarr = new ArrayList<Integer[]>();

        int min = 1000000;
        int max = 0;
        for (Point p : points) {
            if (p.y < min) min = p.y;
            if (p.y > max) max = p.y;
        }
        System.out.println("max i min"+max+" "+min);
        for (int i = min; i <= max; i++) {
            System.out.println(i + "b");
            ArrayList<Integer> al = new ArrayList<>();
            for (Point p : points) {
                System.out.println(p);
                if (p.getY() == i) {
                    al.add((int)p.getX());
                    System.out.println(p.getX());
                }
            }
            int a = 0;
            int k = al.get(0);
            boolean take = true;
            while (a < al.size() - 1) {
                System.out.println("aaaaaa");
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

    /***
     * Robienie zagrady (pojedynczych pol)
     * pomoc do paintPolygon
     *
     * @param points
     * @return
     */
    private static Set<Point>getEnclousureFields(Set<Point> points){
        double max = 0;
        double maxX=0;
        Set<Point> rtrFields = new LinkedHashSet<>();
        for (Point p : points) {if (p.getY() > max){max = p.getY();maxX=p.getX();} }
        Point firstOne = new Point((int)maxX,(int)max+1);
        rtrFields.add(firstOne);
        rtrFields=getEnclousureField(new Point((int)maxX,(int)firstOne.getY()+1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)maxX,(int)firstOne.getY()-1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)maxX+1,(int)firstOne.getY()+1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)maxX-1,(int)firstOne.getY()+1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)maxX+1,(int)firstOne.getY()-1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)maxX-1,(int)firstOne.getY()-1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)maxX+1,(int)firstOne.getY()),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)maxX-1,(int)firstOne.getY()),points,rtrFields);
        return rtrFields;
    }

    /***
     * Wydzielanie pola zagrody - inicjalizacja
     * pomoc do paintPolygon
     *
     * @param point
     * @param points
     * @param rtrFields
     * @return
     */
    private static Set<Point> getEnclousureField(Point point, Set<Point> points,Set<Point>rtrFields) {
        boolean istGut = false;
        double x = point.getX();
        double y = point.getY();
        if(rtrFields.contains(point))return rtrFields;
        for (Point p : points){
            if (p.getX() == x && p.getY()==y){return rtrFields;}
            if(p.getX()==x && (p.getY()<=y+1 && p.getY()>=y-1))istGut=true;
            if(p.getY()==y && (p.getX()<=x+1 && p.getX()>=x-1))istGut=true;
        }
        if(istGut)rtrFields.add(point);
        else return rtrFields;
        rtrFields=getEnclousureField(new Point((int)x,(int)y+1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)x,(int)y-1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)x+1,(int)y+1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)x-1,(int)y+1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)x+1,(int)y-1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)x-1,(int)y-1),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)x+1,(int)y),points,rtrFields);
        rtrFields=getEnclousureField(new Point((int)x-1,(int)y),points,rtrFields);
        return rtrFields;
    }

    /***
     * Wypelanianie zagrody
     * pomoc do paintPolygon
     *
     * @param point
     * @param enclosureFields
     * @param rtrFields
     * @return
     */
    private static Set<Point> fillEnclousureFields(Point point, Set<Point> enclosureFields,Set<Point>rtrFields) {
        if(rtrFields.contains(point)){return rtrFields;}
        if(enclosureFields.contains(point)){return rtrFields;}
        rtrFields.add(point);
        System.out.println(point);
        int x = (int)point.getX();
        int y = (int)point.getY();
        rtrFields=fillEnclousureFields(new Point(x,y+1),enclosureFields,rtrFields);
        rtrFields=fillEnclousureFields(new Point(x,y-1),enclosureFields,rtrFields);
        rtrFields=fillEnclousureFields(new Point(x+1,y),enclosureFields,rtrFields);
        rtrFields=fillEnclousureFields(new Point(x-1,y),enclosureFields,rtrFields);
        return rtrFields;
    }

    /***
     * Wypelanie obwodu
     *
     * @param points
     * @return
     */
        public static Set<Point> paintPolygon2(Set<Point> points) {
        if(points.isEmpty()) return new LinkedHashSet<>();
        Set<Point> enclosureFields= getEnclousureFields(points);
        Set<Point> rtrFields = new LinkedHashSet<>();//nie dalem "=points" bo wtedy zmienilismyby tez points z nadklasy
        Set<Point> iterateFields = new LinkedHashSet<>();
        for(Point p: points) {
            iterateFields.add(p);
            rtrFields.add(p);
        }
        for(Point p: iterateFields){
            int x = (int)p.getX();
            int y = (int)p.getY();
            rtrFields=fillEnclousureFields(new Point(x,y+1),enclosureFields,rtrFields);
            rtrFields=fillEnclousureFields(new Point(x,y-1),enclosureFields,rtrFields);
            rtrFields=fillEnclousureFields(new Point(x+1,y),enclosureFields,rtrFields);
            rtrFields=fillEnclousureFields(new Point(x-1,y),enclosureFields,rtrFields);
        }
        return rtrFields;
    }

    /***
     * Sprawdzanie czy ten punkt jest na krancu(czy jest obwodem, mowiac inaczej)
     *
     * @param p
     * @param points
     * @return
     */
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
