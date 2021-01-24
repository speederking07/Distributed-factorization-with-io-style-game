package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.dto.Change;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: Na pełnią wersję to też ma myć
@Getter
@Setter
public class GameCurrentState {
    private List<CurrentPlayer> addedPlayers; //lista wszystkich obecnych graczy

    public GameCurrentState(){
        this.addedPlayers=new ArrayList<>();
    }

    public void addPlayer(Player player, ArrayList<Point> points, ArrayList<Point> path, Point pos){
        CurrentPlayer cp = new CurrentPlayer(player, points, path, (int)pos.getY(), (int)pos.getY(), System.currentTimeMillis());
        addedPlayers.add(cp);
    }

}

@Getter
@Setter
class CurrentPlayer {
    private String color;
    private String name;
    private ArrayList<int[]> path; // wspołrzędne scieżki danego gracza od początku do kończa najlepiej tylko w zgięciach
    private ArrayList<int[]> fields; //lista pól tego grasza
    private int x, y;//pozycja
    private long time;

    public CurrentPlayer(Player player, ArrayList<Point> points, ArrayList<Point> path, int x, int y, long time1){
        this.x=x;
        this.y=y;
        this.time=time1;
        this.color= player.getColorsInCsv();
        this.name= player.getUsername();
        this.fields = new ArrayList<>();
        this.path = new ArrayList<>();
        for(Point p: points)this.fields.add(new int[]{(int)p.getX(),(int)p.getY()});
        for(Point p: path)this.path.add(new int[]{(int)p.getX(),(int)p.getY()});
    }
}
