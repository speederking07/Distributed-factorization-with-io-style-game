package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.dto.AddPlayer;
import pl.zespolowe.splix.dto.Change;
import pl.zespolowe.splix.dto.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GameListenerState {
    //liste stingow(graczy(sama nazwa))
    //liste change'ow - change
    //lista ruchow - moves
    //lista nowych graczy - players
    private int turn;
    private List<String> killedPlayers;
    private final Map<String,ArrayList<int[]>> changes;//TODO: Marek ustawilem tak jak chciales
    //private List<Change> changes;
    private List<Move> moves;
    private List<AddPlayer> addedPlayers;

    public GameListenerState(int turn) {
        this.turn = turn;
        this.killedPlayers = new ArrayList<>();
        this.changes = new HashMap<>();
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

    public void changeField(String name, Point p) {
        ArrayList<int[]> arr = new ArrayList<>();
        if(changes.size()>0){
            changes.forEach((k, v) -> {
                if (k.equals(name)) {
                    for(int[] a: v){
                        arr.add(a);
                    }
                }
            });
            arr.add(new int[]{(int) p.getX(), (int) p.getY()});
            changes.remove(name);
            changes.put(name,arr);
        }
        else{
            arr.add(new int[]{(int) p.getX(), (int) p.getY()});
            changes.put(name,arr);
        }
    }

    public void changeField(Point p) {
        changeField("",p);
        //changes.add(new Change(p));
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
