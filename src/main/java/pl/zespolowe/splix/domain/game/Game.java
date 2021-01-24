package pl.zespolowe.splix.domain.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.zespolowe.splix.domain.game.player.Bot;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.dto.SimpleMove;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Game implements ObservableGame {
    private final static int x_size = 50;
    private final static int y_size = 50;
    private final static int max_players = 20;
    @Getter
    private final static int botsNumber = 4;
    @Getter
    private final int gameID;
    @Getter
    private final List<GameListener> listeners;
    @Getter
    private final Board board;
    @Getter
    private final Set<Checker> players;
    long startTime = -1;
    long allGameSTart = 0;
    @Getter
    private int turn;


    public Game(int gameID) {
        //this.players = new HashSet<>();
        this.listeners = new ArrayList<>();
        this.gameID = gameID;
        this.board = new Board(x_size, y_size);
        this.turn = 0;
        this.allGameSTart = System.currentTimeMillis();
        board.setGls(new GameListenerState(0));
        this.players = makeBots(botsNumber);
        log.info("NEW GAME: " + gameID);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                newTurn();
//            }
//        }, 1000, 250);
    }

    @SneakyThrows
    private Set<Checker> makeBots(int ammount) {
        String[] botNames = {"Bot Michas",
                "Bot Marek",
                "Bot Tomek",
                "Bot Basia",
                "Bot Barbara",
                "Bot Bartek",
                "Bot Bronek",
                "Bot Bogus",
                "Bot Bogdan",
        };
        Set<Checker> playersLocal = new HashSet<>();
        for (int i = 0; i < ammount; i++) {
            Bot bot = new Bot(botNames[i]);
            if (i >= botNames.length) bot = new Bot(botNames[i] + (i - botNames.length + 1));
            Checker ch = board.respawnPlayer(x_size, y_size, bot);
            if (ch == null) break;
            playersLocal.add(ch); //Mozliwe że nie potrzebe ale u mnie nie działało bez tego
            GameListenerState gls = board.getGls();
            gls.addPlayer(ch);
            board.setGls(gls);
        }
        return playersLocal;
    }

    //TODO: nie dostaję inforamcji o zmianie statustu pola - jest ok?
    private void publishEvent() {
        listeners.forEach(l -> l.event(board.getGls()));
    }

    private void killPlayer(Checker checker) {
        players.remove(checker);
        board.killPlayer(checker);
    }

    //TODO: brak automatycznego ruchu - to jest aktualne?
    public void move(SimpleMove move, Player player) {
        //TODO: niech się dzieje magia - ok, magia chyba juz dziala c'nie?
        Checker tmpChecker = null;
        for (Checker ch : players) if (ch.getPlayer().equals(player)) tmpChecker = ch;
        Checker chs = board.move(tmpChecker, move.getMove());
        if (chs != null) killPlayer(chs);
//        log.info("Player's MOVE: " + player.getUsername());
    }

    public void resign(Player player) {
        List<Checker> ch = players.stream()
                .filter(t -> t.getPlayer().equals(player))
                .collect(Collectors.toList());

        if (ch.size() > 1) killPlayer(ch.get(0));
    }

    public boolean isActive() {
        return !players.isEmpty();
    }

    public boolean containsPlayer(Player player) {
        return players.stream().anyMatch(c -> c.getPlayer().equals(player));
    }

    /***
     * Pelna plansza lub nie ma miejsca na respawn
     * @return
     */
    public boolean isFull() {
        return players.size() >= max_players;
    }

    /***
     * Dołącznie gracza
     * @param p
     * @return
     */
    public boolean join(Player p) {
        if (!isFull()) {
            Checker ch = board.respawnPlayer(x_size, y_size, p);
            if (ch == null) return false;
            players.add(ch); //Mozliwe że nie potrzebe ale u mnie nie działało bez tego
            board.addGlsPlayer(ch);
            //GameListenerState gls = board.getGls();
            //gls.addPlayer(ch);
            //board.setGls(gls);

            return true;
        }
        return false;
    }

    /***
     * Obecny stan gry dla nowego gracza który dołączył
     * @return
     */
    public GameCurrentState getGameCurrentState() {
        GameCurrentState gcs = new GameCurrentState();
        for (Checker ch : players) {
            gcs = board.setInfoForNewPlayer(ch, gcs);
        }
//        System.out.println("to dostaje nowy gracz:\n");
//        List<CurrentPlayer> a = gcs.getAddedPlayers();
//        if (a.size() == 0) System.out.println("Nic nie dostal - pewnie to pierwszy gracz jest\n");
//        for (int i = 0; i < a.size(); i++) {
////            System.out.println(gcs.getAddedPlayers().get(i).getName());
//            for(int[] pole :gcs.getAddedPlayers().get(i).getFields() ){
////                System.out.println("pola: x: " + pole[0]+" y: "+pole[1]);
//            }
//
////            System.out.println("sciezki: " + gcs.getAddedPlayers().get(i).getPath());
//        }
//        System.out.println("----------------\n");
//        gcs.addTime(allGameSTart);
        return gcs;
    }

    /**
     * Nstępna tura
     */
    public void newTurn() {
        startTime = (startTime == -1) ? System.currentTimeMillis() : -1;
//        log.info("NEXT TURN");
        turn++;
        board.printGls();//Marek rutaj sb sprawdz co wysyla server
        publishEvent();
        board.setGls(new GameListenerState(turn));
        moveBots();
    }

    @Override
    public void addListener(@NonNull GameListener listener) {
        listeners.add(listener);
    }

    /**
     * Ruch botow
     */
    public void moveBots() {
        Iterator<Checker> iter = players.iterator();
        while (iter.hasNext()) {
            Checker ch = iter.next();
            if (ch.getPlayer() instanceof Bot) {
                Checker chs = board.botMove(ch);
                if (chs != null) {
                    iter.remove();
                    killPlayer(chs);
                }
            }
        }
//        players.forEach((ch) -> {
//            Checker chs = null;
//            if (ch.getPlayer() instanceof Bot) {
//                chs = board.botMove(ch);
//                if (chs != null) killPlayer(chs);
//            }
//        });
    }

}