package pl.zespolowe.splix.services;

import lombok.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.zespolowe.splix.domain.game.Game;
import pl.zespolowe.splix.domain.game.GameListener;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.exceptions.GameException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tomasz
 */
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

    @Scheduled(fixedRate = 250)
    public void nextTurn() {
        games.values().parallelStream().forEach(Game::newTurn);
    }

    /**
     * Find and add Player to Game
     *
     * @param player
     * @return gameID
     * @throws GameException cannot join any game
     * @see Player
     * @see Game
     */
    public synchronized int addToGame(Player player) throws GameException {
        Game game = games.values().stream().filter(g -> !g.isFull())
                .findFirst()
                .orElse(createGame());
        if (!game.join(player)) throw new GameException("Unable to join any game, please try again");
        player.setGame(game);
        return game.getGameID();
    }

    /**
     * Add GameListener to specified Game
     *
     * @param gameID
     * @param listener
     * @throws GameException no game with such gameID
     * @see Game
     */
    public synchronized void addListener(int gameID, @NonNull GameListener listener) throws GameException {
        Game game = games.get(gameID);
        if (game == null) throw new GameException("No such game");
        //TODO: tragedia
        if (game.getListeners().isEmpty())
            game.addListener(listener);
    }

    /**
     * @param gameID
     * @param player
     * @return true if Game contains Player, false otherwise
     * @see Game#containsPlayer(Player)
     */
    public boolean containsPlayer(int gameID, Player player) {
        Game game = games.get(gameID);
        return game != null && game.containsPlayer(player);
    }

}
