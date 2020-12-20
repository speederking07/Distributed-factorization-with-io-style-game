package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.dto.AddPlayer;
import pl.zespolowe.splix.dto.Change;
import pl.zespolowe.splix.dto.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GameListenerState {
    //liste stingow(graczy(sama nazwa))
    //liste change'ow - change
    //lista ruchow - moves
    //lista nowych graczy - players
    private int turnNumer;
    private List<String> killedPlayers;
    private List<Change> changes;
    private List<Move> moves;
    private List<AddPlayer> addedPlayers;

    public GameListenerState(int turn) {
        this.turnNumer = turn;
        this.killedPlayers = new ArrayList<>();
        this.changes = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.addedPlayers = new ArrayList<>();
    }

    public void addPlayer(Checker ch) {
        addedPlayers.add(new AddPlayer(ch));
    }

    public void killPlayer(Checker ch) {
        killedPlayers.add(ch.getPlayer().getUsername());
    }

    public void playerMove(Checker ch, boolean havePath) {
        moves.add(new Move(ch, havePath));
    }

    public void changeField(Checker ch, Point p) {
        changes.add(new Change(ch, p));
    }

    public void changeField(Point p) {
        changes.add(new Change(p));
    }


   /* class Change{//pole ktore zminilo wlasciciela w danej turze.
        int x;
        int y;
        String player;//jak nan nikogo to pusty
    }

    class Move{
        int x;
        int y;
        boolean havePath;
        String player;
    }

    class AddPlayer{
        //kolor z colorsInCsv
        //nazwa String
        //wspolczedne x,y
    }

    */

}
