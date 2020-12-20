package pl.zespolowe.splix.services;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import pl.zespolowe.splix.domain.game.Game;
import pl.zespolowe.splix.domain.game.GameListener;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.exceptions.GameException;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {
    private final Map<Integer, Game> games;

    public GameService() {
        games = new HashMap<>();
    }

    private Game createGame() {
        int id = games.keySet().stream().max(Integer::compare).orElse(0) + 1;
        Game game = new Game(id);
        games.put(id, game);
        return game;
    }

    public synchronized int addToGame(Player player) throws GameException {
        Game game = games.values().stream().filter(g -> !g.isFull())
                .findFirst()
                .orElse(createGame());
        if (!game.join(player)) throw new GameException("Unable to join any game, please try again");
        player.setGame(game);
        return game.getGameID();
    }

    public synchronized void addListener(int gameID, @NonNull GameListener listener) throws GameException {
        Game game = games.get(gameID);
        if (game == null) throw new GameException("No such game");
        //TODO: tragedia
        if (game.getListeners().isEmpty())
            game.addListener(listener);
    }

    public boolean containsPlayer(int gameID, Player player) {
        Game game = games.get(gameID);
        return game != null && game.containsPlayer(player);
    }

}
