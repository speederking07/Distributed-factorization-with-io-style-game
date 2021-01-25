package pl.zespolowe.splix.domain.game;

import org.junit.Assert;
import pl.zespolowe.splix.domain.game.player.Bot;
import pl.zespolowe.splix.domain.game.player.Player;
import javax.security.auth.login.CredentialException;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

/***
 *
 * @author KalinaMichal
 * Tests for Board class
 *
 */
public class BoardTest {
    /***
     * Checker respawnPlayer(int,int, Player)
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldStartGame() throws CredentialException {

        //given
        Board sut = new Board(20,20);

        //when
        Checker ch = sut.respawnPlayer(20, 20, new Player("testowyGracz") );

        //then
        Assert.assertTrue(sut.getFields().containsValue(ch));
        Assert.assertTrue(sut.getGls().getAddedPlayers().get(0).getName().equals("testowyGracz"));
    }

    @org.junit.Test
    public void shouldMovePlayer() throws CredentialException {

        //given
        Board sut = new Board(20,20);
        Checker ch = sut.respawnPlayer(20, 20, new Player("testowyGracz") );
        int x=(int)ch.getPoint().getX();
        int y=(int)ch.getPoint().getY();

        //when
        sut.move(ch,Direction.EAST);

        //then
        Assert.assertTrue((int)ch.getPoint().getX()-1==x && (int)ch.getPoint().getY()==y);
    }

    /***
     * killPlayer(Checker)
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldKillPlayer() throws CredentialException {

        //given
        Board sut = new Board(20,20);
        Checker ch = sut.respawnPlayer(20, 20, new Player("testowyGracz") );
        Checker ch2 = sut.respawnPlayer(20, 20, new Player("testowyGracz") );
        sut.move(ch2,Direction.EAST);
        sut.move(ch,Direction.EAST);

        //when
        sut.killPlayer(ch);

        //then
        Assert.assertFalse(sut.getFields().containsValue(ch));
        Assert.assertFalse(sut.getPaths().containsValue(ch));
        Assert.assertTrue(sut.getFields().containsValue(ch2));
        Assert.assertTrue(sut.getPaths().containsValue(ch2));
    }

    /***
     * move(Checker, Direction)
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldOvertake() throws CredentialException {
/***
 * Uwaga test moze nie przejsc jezeli zotanie wylosowane niefortunne miejsce resawnu ale szansa na to jest b. mala
 */
        //given
        Board sut = new Board(10000,10000);
        Checker ch = sut.respawnPlayer(20, 20, new Player("testowyGracz") );
        int x= (int)ch.getPoint().getX();
        int y= (int)ch.getPoint().getY();

        //when
        //robimy slad powodujacy przejecie
        sut.move(ch,Direction.EAST);
        sut.move(ch,Direction.EAST);
        sut.move(ch,Direction.EAST);
        sut.move(ch,Direction.SOUTH);
        sut.move(ch,Direction.SOUTH);
        sut.move(ch,Direction.WEST);
        sut.move(ch,Direction.WEST);
        sut.move(ch,Direction.WEST);

        //then
        //sprawdzam czy pole poboczne(slad) nalezy do mnie
        Assert.assertTrue(sut.getFields().get(new Point(x+1,y+1)).getPlayer().getUsername().equals("testowyGracz"));
        //sprawdzam czy pole ze srodka nalezy do mnie
        Assert.assertTrue(sut.getFields().get(new Point(x+2,y+1)).getPlayer().getUsername().equals("testowyGracz"));
    }

    /****
     * botMove(Checker)
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldPlayBot() throws CredentialException {
        //given
        Board sut = new Board(10000,10000);
        Bot bot = new Bot("testBot");
        Checker ch = sut.respawnPlayer(20, 20, bot);
        int x= (int)ch.getPoint().getX();
        int y= (int)ch.getPoint().getY();
        //when
        //robimy 'kilka' ruchow bota. Po nich Bot powinien zamalowac kilka pol
        sut.botMove(ch);
        sut.botMove(ch);
        sut.botMove(ch);
        sut.botMove(ch);
        sut.botMove(ch);
        sut.botMove(ch);
        sut.botMove(ch);
        sut.botMove(ch);

        //then
        //sprawdzam czy pole poboczne(slad) nalezy do mnie
        AtomicInteger iter= new AtomicInteger();
        sut.getFields().forEach((k, v) -> {
            if (v.equals(ch)) {
                iter.getAndIncrement();
            }
        });
        Assert.assertTrue(iter.get() >=5);
  }
}
