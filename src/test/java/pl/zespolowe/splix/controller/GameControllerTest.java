package pl.zespolowe.splix.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.zespolowe.splix.controllers.GameController;
import pl.zespolowe.splix.domain.game.GameCurrentState;
import pl.zespolowe.splix.domain.game.player.Player;
import pl.zespolowe.splix.exceptions.GameException;
import pl.zespolowe.splix.services.ActivePlayersService;
import pl.zespolowe.splix.services.GameService;
import pl.zespolowe.splix.services.UserService;

import javax.security.auth.login.CredentialException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private GameService service;
    @MockBean
    private UserService userService;
    @MockBean
    private SimpMessagingTemplate messaging;
    @MockBean
    private ActivePlayersService playersService;

    @Before
    public void init() throws CredentialException, GameException {
        given(playersService.getPlayer(any())).willReturn(new Player("kk"));
        given(service.addToGame(any(Player.class))).willReturn(1);
    }

    @Test
    public void playGame() throws Exception {
        mvc.perform(get("/game/play?username=user"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1"));
    }

    @Test
    public void getState() throws Exception {
        given(service.getState(1)).willReturn(new GameCurrentState());
        mvc.perform(get("/game/state/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));

        mvc.perform(get("/game/state/2"))
                .andExpect(status().isBadRequest());
    }

}
