package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;

public class Checker {
    private final Direction direction = Direction.EAST;
    @Getter
    Player player;
    @Getter
    private Point point;
    @Getter
    private int score = 0;

    @Setter
    @Getter
    private Point path;

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
