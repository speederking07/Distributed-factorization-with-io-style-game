package pl.zespolowe.splix.gamerules;

import java.awt.*;

public class Checker {
    Player player;
    private Point point;
    private int score=0;
    private Direction direction = Direction.EAST;

    Checker(Player pl, Point po){
        player=pl;
        point=po;
    }

    private enum Direction {
        WEST,
        EAST,
        NORTH,
        SOUTH
    }

    public Point next_turn(){
        Point point0=point;
        switch (direction) {
            case EAST:
                point0.x++;
            case WEST:
                point0.x--;
            case NORTH:
                point0.y++;
            case SOUTH:
                point0.y--;
        }
        return point0;
    }

    public int getScore(){return score;}
    public void setScore(int x){score+=x;}
    public void set_position(Point p){point=p;}

}
