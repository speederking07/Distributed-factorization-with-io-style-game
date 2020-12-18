package pl.zespolowe.splix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zespolowe.splix.domain.user.User;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM user ORDER BY score DESC LIMIT 10", nativeQuery = true)
    Collection<User> getTopUsers();
}
