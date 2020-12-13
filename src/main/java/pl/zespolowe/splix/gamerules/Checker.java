package pl.zespolowe.splix.gamerules;

import java.awt.*;

public class Checker {
    Player player;
    private Point point;
    private int score=0;
    private Direction direction = Direction.EAST;

    Checker(Player p, int x, int y){
        player=p;
        point.x = x;
        point.y=y;
    }

    private enum Direction {
        WEST,
        EAST,
        NORTH,
        SOUTH
    }

    public Point next_turn(){
        switch (direction) {
            case EAST:
                //zapytaj czy mozna
                point.x++;
                //return point;
            case WEST:
                //zapytaj czy mozna
                point.x--;
                //return point;
            case NORTH:
                //zapytaj czy mozna
                point.y++;
                //return point;
            case SOUTH:
                //zapytaj czy mozna
                point.y--;
                //return point;
        }
        return point;
    }

    public int getScore(){return score;}
    public void setScore(int x){score+=x;}
}
