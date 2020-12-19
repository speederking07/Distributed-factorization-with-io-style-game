package pl.zespolowe.splix.domain.game.sendElements;

import pl.zespolowe.splix.domain.game.Checker;

import java.awt.*;

public class AddPlayer {
    String color;
    String name;
    int x,y;
    public AddPlayer(Checker ch) {

        this.color=ch.getPlayer().getColorsInCsv();
        this.name=ch.getPlayer().getUsername();
        Point p=ch.getPoint();
        this.x=p.x;
        this.y=p.y;
    }
    //kolor z colorsInCsv
    //nazwa String
    //wspolczedne x,y
}
