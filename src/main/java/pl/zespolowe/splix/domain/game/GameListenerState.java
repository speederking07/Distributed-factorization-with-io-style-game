package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.sendElements.AddPlayer;
import pl.zespolowe.splix.domain.game.sendElements.Change;
import pl.zespolowe.splix.domain.game.sendElements.Move;

import java.awt.*;
import java.util.List;

public class GameListenerState{

    public GameListenerState(int turn) {
        this.turnNumer = turn;
    }

    @Getter
    int turnNumer=0;
    //liste stingow(graczy(sama nazwa))
    //liste change'ow - change
    //lista ruchow - moves
    //lista nowych graczy - players

    List<String >players;//TODO: zmiana na tych co zabici zmien nazwe
    List<Change> changes;
    List<Move> moves;
    List<AddPlayer> addedPlayers;

    public void addPlayer(Checker ch){
        addedPlayers.add(new AddPlayer(ch));
    }

    public void playersAdder(Checker ch){
        players.add(ch.getPlayer().getUsername());
    }

    public void playerMove(Checker ch, boolean havePath){
        moves.add(new Move(ch,havePath));
    }

    public void changeField(Checker ch,Point p){
        changes.add(new Change(ch, p));
    }

    public void changeField(Point p){
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
