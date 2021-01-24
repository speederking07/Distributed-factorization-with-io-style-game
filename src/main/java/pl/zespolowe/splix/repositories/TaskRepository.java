package pl.zespolowe.splix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zespolowe.splix.domain.factorization.Task;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {

//    @Query(value = "SELECT * FROM task WHERE factor1 = '-1' AND factor2 = '-1' LIMIT 1", nativeQuery = true)
    Optional<Task> findDistinctTopBySolvedFalse();


}
