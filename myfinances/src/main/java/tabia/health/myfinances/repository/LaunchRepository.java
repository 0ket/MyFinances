package tabia.health.myfinances.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tabia.health.myfinances.model.entity.Launch;

public interface LaunchRepository extends JpaRepository <Launch, Long> {

    @Query(value = "SELECT sum(l.value) FROM Launch l JOIN l.user u"
    + "WHERE u.id =:idUser and l.launchType =:type GROUP BY u ")
    BigDecimal getBalanceByUser(@Param("idUser") Long idUser, String type);

}
