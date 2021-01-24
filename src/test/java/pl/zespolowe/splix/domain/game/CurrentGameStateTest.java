package pl.zespolowe.splix.domain.game;

import org.junit.Assert;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.dto.SimpleMove;
import javax.security.auth.login.CredentialException;

/***
 *
 * @author KalinaMichal
 *
 * Test for CurrentGameState
 */
public class CurrentGameStateTest {
    /***
     * GameCurrentState Game.getGameCurrentState()
     * @throws CredentialException
     */
    @org.junit.Test
    public void shouldGetCurrentGameState() throws CredentialException {

        //given
        Game game = new Game(1);
        Player player = new Player("testowyGracz");
        Boolean joined = game.join(player);
        SimpleMove move = new SimpleMove();
        move.setMove(Direction.EAST);
        move.setTurn(1);
        game.move(move,player);

        //when
        GameCurrentState sut = game.getGameCurrentState();

        //then
        Assert.assertEquals(sut.getAddedPlayers().size(),1);
        Assert.assertEquals(sut.getAddedPlayers().get(0).getFields().size(),5);
        Assert.assertEquals(sut.getAddedPlayers().get(0).getName(),player.getUsername());
        Assert.assertTrue(joined);
    }
}
