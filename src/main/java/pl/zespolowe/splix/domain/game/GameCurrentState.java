package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.Setter;
import pl.zespolowe.splix.dto.Change;

import java.awt.*;
import java.util.List;

//TODO: Na pełnią wersję to też ma myć
@Getter
@Setter
public class GameCurrentState {
    private List<CurrentPlayers> addedPlayers; //lista wszystkich obecnych graczy
    private List<Change> changes; //lista wszystkich pól które są zajęte

}

@Getter
@Setter
class CurrentPlayers {
    private String color;
    private String name;
    private List<Point> path; // wspołrzędne scieżki danego gracza od początku do kończa najlepiej tylko w zgięciach
    private int x, y;
    //kolor z colorsInCsv
    //nazwa String
    //wspolczedne x,y
}
