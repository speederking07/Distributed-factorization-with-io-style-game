package pl.zespolowe.splix.domain.game;

import lombok.NonNull;

import java.awt.*;

public class Checker {
    private final Direction direction = Direction.EAST;
    Player player;
    private Point point;
    private int score = 0;

    Checker(@NonNull Player pl, Point po) {
        player = pl;
        point = po;
    }

    public Point next_turn() {
        Point point0 = point;
        switch (direction) {
            case EAST -> point0.x++;
            case WEST -> point0.x--;
            case NORTH -> point0.y++;
            case SOUTH -> point0.y--;
        }
        return point0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int x) {
        score += x;
    }

    public void set_position(Point p) {
        point = p;
    }

    private enum Direction {
        WEST,
        EAST,
        NORTH,
        SOUTH
    }

}
