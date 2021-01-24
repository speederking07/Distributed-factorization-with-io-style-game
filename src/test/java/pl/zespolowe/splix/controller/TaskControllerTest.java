package pl.zespolowe.splix.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.zespolowe.splix.controllers.TaskController;
import pl.zespolowe.splix.domain.factorization.Solution;
import pl.zespolowe.splix.domain.factorization.SubTask;
import pl.zespolowe.splix.domain.factorization.TaskType;
import pl.zespolowe.splix.services.TaskService;
import pl.zespolowe.splix.services.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private TaskService service;
    @MockBean
    private UserService userService;

    @Test
    public void getTask() throws Exception {
        given(service.getSubTask()).willReturn(new SubTask());
        mvc.perform(get("/task"))
                .andExpect(status().isOk());

        given(service.getSubTask()).willReturn(null);
        mvc.perform(get("/task"))
                .andExpect(content().string(""));
    }

    @Test
    public void submitTask() throws Exception {
        Solution solution = new Solution();

        mvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(solution)))
                .andExpect(status().isBadRequest());

        solution.setN("123456");
        mvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(solution)))
                .andExpect(status().isBadRequest());

        solution.setType(TaskType.PAIRS);
        mvc.perform(post("/task")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(solution)))
                .andExpect(status().isOk());
    }
}
