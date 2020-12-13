package pl.zespolowe.splix.domain;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class Game {
    @Getter
    private final int gameID;

    @Getter
    private final Set<Player> players;

    public Game(int gameID) {
        players = new HashSet<>();
        this.gameID = gameID;
    }

    public void resign(Player player) {
        //TODO: co sie dzieje, gdy gracz sie rozlacza

        players.remove(player);
    }

    public boolean isActive(){
        //TODO: czy gra trwa
        return true;
    }

    public boolean containsPlayer(Player player){
        return players.contains(player);
    }

    public boolean isFull(){
        return true;
    }
}
