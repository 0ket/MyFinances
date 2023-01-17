package tabia.health.myfinances.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tabia.health.myfinances.model.entity.Launch;

public interface LaunchRepository extends JpaRepository <Launch, Long> {
}
