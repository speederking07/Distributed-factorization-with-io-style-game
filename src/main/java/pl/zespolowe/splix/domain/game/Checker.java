package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.player.Player;

import java.awt.*;

@Getter
@Setter
public class Checker {
    private final Direction direction;
    private Player player;
    private int score;
    private Point point;
    private Point path;

    Checker(@NonNull Player pl, @NonNull Point po) {
        this.player = pl;
        this.point = po;
        this.direction = Direction.EAST;
        this.score = 0;
    }

    public Point nextTurn() {
        Point point0 = point;
        switch (direction) {
            case EAST -> point0.x++;
            case WEST -> point0.x--;
            case NORTH -> point0.y++;
            case SOUTH -> point0.y--;
        }
        return point0;
    }

    public void setScore(int x) {
        score += x;
    }

}
