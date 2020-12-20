package pl.zespolowe.splix.dto;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.Checker;

@Getter
@Setter
public class Move {
    private int x, y;
    private boolean havePath;
    private String player;

    public Move(Checker ch, boolean isPath) {
        this.x = ch.getPoint().x;
        this.y = ch.getPoint().y;
        this.player = ch.getPlayer().getUsername();
        this.havePath = isPath;// TODO://MAREK PO CO CI TO xd, NIECH KAZDY MA PATH
    }

}
