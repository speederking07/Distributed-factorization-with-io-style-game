package pl.zespolowe.splix.services;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.zespolowe.splix.domain.factorization.*;
import pl.zespolowe.splix.repositories.TaskRepository;

import javax.annotation.PostConstruct;
import java.util.*;
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

        FactorizedNumber num = new FactorizedNumber();
        num.setNum("12");
        num.setFactors(Map.of("2", "2", "3", "1"));
        Set<FactorizedNumber> factorized = new HashSet<>();
        factorized.add(num);
        Solution solution = new Solution();
        solution.setN("1095128860381775205687587248337");
        solution.setType(TaskType.PAIRS);
        solution.setFactorizedNumbers(factorized);

        Task t = tasks.get(7);
        t.submitSolution(solution);
        taskRepository.saveAll(tasks);
    }

    public SubTask getSubTask() throws NotFoundException {
        Optional<Task> opt = taskRepository.findDistinctTopBySolvedFalse();
        if (opt.isEmpty()) throw new NotFoundException("No available tasks");
        Task t = opt.get();
        SubTask result = t.getSubTask();
        taskRepository.save(t);
        return result;
    }

    public void submitSolution(Solution solution) {
        Optional<Task> opt = taskRepository.findById(solution.getN());
        if (opt.isEmpty()) return;
        Task task = opt.get();
        task.submitSolution(solution);
        taskRepository.save(task);
    }
}
