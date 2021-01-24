package pl.zespolowe.splix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zespolowe.splix.domain.factorization.Task;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

    Optional<Task> findDistinctTopBySolvedFalse();
}
