package pl.zespolowe.splix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.zespolowe.splix.controllers.UserController;
import pl.zespolowe.splix.dto.UserSettingsDTO;
import pl.zespolowe.splix.services.UserService;

import javax.security.auth.login.AccountException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService service;


    @Test
    public void registerTest() throws Exception {
        String s = "{ \"username\":\"ss\", \"password\":\"passss\" }";

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(s))
                .andExpect(status().is(400));

        s = "{ \"username\":\"ss\", \"password\":\"passss\", \"email\":\"email@gmail.com\" }";
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(s))
                .andExpect(status().isOk());


        doThrow(new AccountException()).when(service).registerUser(any());
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(s))
                .andExpect(status().is(500));
    }

    @Test
    @WithMockUser
    public void updateSettingsTest() throws Exception {
        UserSettingsDTO dto = new UserSettingsDTO();

        mvc.perform(post("/user/settings")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().is(400));

        dto.setColorsInCSV("ss");
        mvc.perform(post("/user/settings")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(dto)))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser
    public void changePasswordTest() throws Exception {
        mvc.perform(post("/user/password")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content("password"))
                .andExpect(status().is(200));
    }

    @Test
    @WithMockUser
    public void changeEmailTest() throws Exception {
        mvc.perform(post("/user/email")
                .contentType(MediaType.TEXT_PLAIN_VALUE)
                .content("password"))
                .andExpect(status().is(200));
    }
}
