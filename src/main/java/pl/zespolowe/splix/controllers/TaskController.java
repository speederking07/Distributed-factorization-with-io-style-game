package pl.zespolowe.splix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.zespolowe.splix.domain.factorization.Solution;
import pl.zespolowe.splix.domain.factorization.SubTask;
import pl.zespolowe.splix.services.TaskService;

import javax.validation.Valid;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public ResponseEntity<SubTask> getTask() {
        try {
            return ResponseEntity.ok(taskService.getSubTask());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/task")
    public ResponseEntity<String> submit(@Valid @RequestBody Solution solution) {
        taskService.submitSolution(solution);
        return ResponseEntity.ok("ok");
    }
}
