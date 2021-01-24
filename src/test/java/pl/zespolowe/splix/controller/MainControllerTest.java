package pl.zespolowe.splix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.zespolowe.splix.controllers.MainController;
import pl.zespolowe.splix.services.UserService;

import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService service;
    @MockBean
    private SimpMessagingTemplate template;

    @Test
    public void passwordRecoverTest() throws Exception {
        mvc.perform(get("/recover?username=uu"))
                .andExpect(status().is(200));

        mvc.perform(post("/recover/12345").content("pass"))
                .andExpect(status().is(200));
    }

    @Test
    public void errorTest() throws Exception{
        mvc.perform(get("/error"))
                .andExpect(status().is(302));
    }

    @Test
    public void getLeadersTest() throws Exception {
        Map<String, Long> leaders = Map.of(
                "hej", 100L,
                "hh", 200L
        );

        given(service.getLeaders()).willReturn(leaders);

        mvc.perform(get("/game/leaders"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(leaders)));
    }
}
