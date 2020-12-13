package pl.zespolowe.splix.services;

import org.springframework.stereotype.Component;
import pl.zespolowe.splix.domain.Game;
import pl.zespolowe.splix.domain.Player;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameRegistry {
    private final Map<Integer, Game> games;

    public GameRegistry() {
        games = new HashMap<>();
    }

    private Game createGame() {
        //TODO: initialize game
        return new Game(1);
    }

    public int addToGame() {
        Game game = games.values().stream().filter(g -> !g.isFull()).findFirst().orElse(null);
        if (game == null)
            game = createGame();
        //TODO: add to game
        return game.getGameID();
    }

    public boolean containsPlayer(int gameID, Player player) {
        Game game = games.get(gameID);
        return game != null && game.containsPlayer(player);
    }
}
