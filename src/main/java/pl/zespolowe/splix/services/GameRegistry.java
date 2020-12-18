package pl.zespolowe.splix.services;

import org.springframework.stereotype.Component;
import pl.zespolowe.splix.domain.game.Game;
import pl.zespolowe.splix.domain.game.Player;

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

    public int addToGame(Player player) {
        Game game = games.values().stream().filter(g -> !g.isFull()).findFirst().orElse(null);
        if (game == null)
            game = createGame();
        game.join(player);
        //TODO: zwróć stan gry return game.join(player)
        return game.getGameID();
    }

    public boolean containsPlayer(int gameID, Player player) {
        Game game = games.get(gameID);
        return game != null && game.containsPlayer(player);
    }
}
