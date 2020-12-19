package pl.zespolowe.splix.domain.game.sendElements;

import pl.zespolowe.splix.domain.game.Checker;

import java.awt.*;

public class Move {
    int x;
    int y;
    //boolean havePath;
    String player;
    public Move(Checker ch, Point p) {
        this.x=p.x;
        this.y=p.y;
        this.player=ch.getPlayer().getUsername();
        //this.havePath= TODO://MAREK PO CO CI TO, NIECH KAZDY MA PATH

    }

}
