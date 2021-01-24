package pl.zespolowe.splix.services;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zespolowe.splix.domain.factorization.SubTask;
import pl.zespolowe.splix.domain.factorization.Task;
import pl.zespolowe.splix.repositories.TaskRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    @PostConstruct
    public void init() {
        List<String> l = List.of(
                "998849245634991311441046256591",
                "414782699278360330885109570707",
                "624529431468516597137074298827",
                "962142481556275932309678962903",
                "679651520652051232283099109191",
                "583144022837369459062341397661",
                "482584681099983926444520218369",
                "1095128860381775205687587248337",
                "670839346822676884520470342081",
                "556541425254413945752110419531"
        );

        List<Task> tasks = l.stream().map(Task::new).collect(Collectors.toList());
        taskRepository.saveAll(tasks);
    }

    public SubTask getSubTask() throws NotFoundException {
        Optional<Task> opt = taskRepository.getTask();
        if (opt.isEmpty()) throw new NotFoundException("No available task");
        Task t = opt.get();
        return t.getSubTask();
    }
}
