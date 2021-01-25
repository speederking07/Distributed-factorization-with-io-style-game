package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.player.Player;

import java.awt.*;

/***
 * @Author KalinaMichal
 */
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

    public void setScore(int x) {
        score += x;
    }
}
