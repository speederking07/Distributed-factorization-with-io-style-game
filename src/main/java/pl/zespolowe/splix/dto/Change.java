package pl.zespolowe.splix.dto;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.Checker;

import java.awt.*;

@Getter
@Setter
public class Change {
    private int x, y;
    private String player;//jak nan nikogo to pusty

    public Change(Checker ch, Point p) {
        this.x = p.x;
        this.y = p.y;
        this.player = ch.getPlayer().getUsername();
    }


    public Change(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

}
