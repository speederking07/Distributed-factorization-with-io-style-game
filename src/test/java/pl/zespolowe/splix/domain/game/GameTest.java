package pl.zespolowe.splix.domain.game;

import org.junit.Assert;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.dto.SimpleMove;
import javax.security.auth.login.CredentialException;
import java.util.concurrent.atomic.AtomicInteger;

/***
 *
 * @author KalinaMichal
 *
 * Tests for Game class
 */
public class GameTest {

    /***
     * join(Player)
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldJoin() throws CredentialException {

        //given
        Game sut = new Game(1);

        //when
        Boolean joined = sut.join(new Player("testowyGracz"));

        //then
        Assert.assertTrue(sut.getPlayers().size() == sut.getBotsNumber()+1);
        Assert.assertTrue(joined);
    }

    /****
     * move(SimpleMove,PLayer)
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldMovePlayer() throws CredentialException {

        //given
        Game sut = new Game(1);
        Player player = new Player("testowyGracz");
        Boolean joined = sut.join(player);
        SimpleMove move = new SimpleMove();
        move.setMove(Direction.EAST);
        move.setTurn(1);

        //when
        sut.move(move, player);

        //then
        AtomicInteger iter = new AtomicInteger();
        sut.getBoard().getPaths().forEach((k, v) -> {
            if (v.getPlayer().getUsername().equals(player.getUsername())) {
                iter.getAndIncrement();
            }
        });
        int exp = iter.incrementAndGet();
        Assert.assertEquals(exp, 2);
        Assert.assertTrue(joined);
    }

    /***
     * newTurn()
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldMakeNewTurn() throws CredentialException {
        //given
        Game sut = new Game(1);
        Player player = new Player("testowyGracz");
        Boolean joined = sut.join(player);
        AtomicInteger iter0= new AtomicInteger();
        sut.getBoard().getPaths().forEach((k, v) -> {
            if (v.getPlayer().getUsername().equals("Bot Michas")) {
                iter0.getAndIncrement();
            }
        });
        int exp0 = iter0.incrementAndGet()-1;
        Assert.assertEquals(exp0, 0);

        //when
        sut.newTurn();

        //then
        AtomicInteger iter= new AtomicInteger();
        sut.getBoard().getPaths().forEach((k, v) -> {
            if (v.getPlayer().getUsername().equals("Bot Michas")) {
                iter.getAndIncrement();
            }
        });
        int exp = iter.incrementAndGet()-1;
        Assert.assertEquals(exp, 1);
        Assert.assertEquals(sut.getTurn(),1);
        Assert.assertEquals(sut.getBoard().getGls().getTurn(),1);
        Assert.assertEquals(sut.getPlayers().size(),sut.getBotsNumber()+1);
        Assert.assertTrue(joined);
    }

    /***
     * resign(Player)
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldResignPlayer() throws CredentialException {

        //given
        Game sut = new Game(1);
        Player player = new Player("testowyGracz");
        Boolean joined = sut.join(player);

        //when
        sut.resign(player);

        //then
        AtomicInteger iter1 = new AtomicInteger();
        sut.getBoard().getPaths().forEach((k, v) -> {
            if (v.getPlayer().getUsername().equals(player.getUsername())) {
                iter1.getAndIncrement();
            }
        });
        int exp1 = iter1.incrementAndGet()-1;

        Assert.assertEquals(exp1,0);
        Assert.assertTrue(joined);
    }
}