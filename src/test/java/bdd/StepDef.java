package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pl.zespolowe.splix.domain.game.Direction;
import pl.zespolowe.splix.domain.game.Game;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.dto.SimpleMove;

import javax.security.auth.login.CredentialException;
import java.util.concurrent.atomic.AtomicInteger;

public class StepDef {
    Game sut;
    Player player;
    SimpleMove move;
    @Given("gra została wystartowana")
    public void started_game() throws CredentialException {
        sut = new Game(1);
        player = new Player("testowyGracz");
    }

    @Given("gracz dołączył")
    public void joined_player() {
        sut.join(player);
    }

    @When("gracz pójdzie w kierunku wschodnim")
    public void player_move_to_EAST() {
        move = new SimpleMove();
        move.setMove(Direction.EAST);
        move.setTurn(1);
        sut.move(move, player);
    }

    @Then("gracz zyska {int} nowe ślady")
    public void player_is_moved(int assertion) {
        AtomicInteger iter = new AtomicInteger();
        sut.getBoard().getPaths().forEach((k, v) -> {
            if (v.getPlayer().getUsername().equals(player.getUsername())) {
                iter.getAndIncrement();
            }
        });
        int exp = iter.incrementAndGet();
        Assert.assertEquals(exp, assertion);
    }
}
