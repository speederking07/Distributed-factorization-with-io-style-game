package pl.zespolowe.splix.domain.game.sendElements;

import pl.zespolowe.splix.domain.game.Checker;

import java.awt.*;

public class Move {
    int x;
    int y;
    //boolean havePath;
    String player;
    public Move(Checker ch) {
        this.x=ch.getPoint().x;
        this.y=ch.getPoint().y;
        this.player=ch.getPlayer().getUsername();
        //this.havePath= TODO://MAREK PO CO CI TO xd, NIECH KAZDY MA PATH

    }

}
