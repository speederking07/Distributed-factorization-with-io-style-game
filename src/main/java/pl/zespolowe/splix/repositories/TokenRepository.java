package pl.zespolowe.splix.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zespolowe.splix.domain.user.RecoveryToken;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<RecoveryToken, String> {
    Optional<RecoveryToken> findFirstByToken(String token);
}
