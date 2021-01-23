package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.player.Player;

import java.awt.*;

@Getter
@Setter
public class Checker {
    private Direction direction;//aktualny kierunek
    private Player player;
    private int score;
    private Point point;//aktualna pozycja
    private Point path;//poczatek jego sciezki -punkt z ktorego wyruszyla (znajdujacy sie w jego wladaniu)

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

    public void makeMove(Direction dir) {
        int x = point.x;
        int y = point.y;
        switch (direction) {
            case EAST -> x++;
            case WEST -> x--;
            case NORTH -> y++;
            case SOUTH -> y--;
        }
        point = new Point();
        point.x = x;
        point.y = y;
    }

    public void setScore(int x) {
        score += x;
    }
}
