package tabia.health.myfinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tabia.health.myfinances.model.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
