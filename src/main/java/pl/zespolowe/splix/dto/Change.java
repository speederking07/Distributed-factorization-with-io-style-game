package pl.zespolowe.splix.dto;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.Checker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/****
 *
 *
 * TODO: TA KLASA JEST DO WYWALENIA (ale wywale ja potem)
 *
 *
 */
@Getter
@Setter
public class Change {
    ArrayList<int[]> pos = new ArrayList<>();
    private int x, y;
    //private String player;//jak nan nikogo to pusty

    public Change(Checker ch, Point p) {
        int[] tmp = new int[2];
        tmp[0]= (int) p.getX();
        tmp[1]= (int) p.getY();

        //pos[pos.length()-1][pos.length-1]=p.getX(),p.getY();
        //pos.add(tmp);
        this.x = p.x;
        this.y = p.y;
        //this.player = ch.getPlayer().getUsername();
    }


    public Change(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

}
