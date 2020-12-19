package pl.zespolowe.splix.domain.game.sendElements;

import pl.zespolowe.splix.domain.game.Checker;

import java.awt.*;

public class Change {
    int x;
    int y;
    String player;//jak nan nikogo to pusty

    public Change(Checker ch, Point p) {
        this.x=p.x;
        this.y=p.y;
        this.player=ch.getPlayer().getUsername();
    }


    public Change(Point p) {
        this.x=p.x;
        this.y=p.y;
    }

}
