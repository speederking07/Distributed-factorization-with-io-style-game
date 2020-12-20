package pl.zespolowe.splix.dto;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.Checker;

import java.awt.*;

@Getter
@Setter
public class AddPlayer {
    private String color;
    private String name;
    private int x, y;

    public AddPlayer(Checker ch) {
        this.color = ch.getPlayer().getColorsInCsv();
        this.name = ch.getPlayer().getUsername();
        Point p = ch.getPoint();
        this.x = p.x;
        this.y = p.y;
    }
    //kolor z colorsInCsv
    //nazwa String
    //wspolczedne x,y
}
