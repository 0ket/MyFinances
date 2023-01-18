package tabia.health.myfinances.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tabia.health.myfinances.model.entity.Launch;
import tabia.health.myfinances.model.enums.LaunchStatus;
import tabia.health.myfinances.model.enums.LaunchType;

public interface LaunchRepository extends JpaRepository <Launch, Long> {

    @Query(value = " select sum(l.value) from Launch l join l.user u  where u.id = :idUser and l.launchType =:type and l.launchStatus = :status group by u ")
    BigDecimal getBalanceByUser(@Param("idUser") Long idUser, 
                                @Param("type")   LaunchType type, 
                                @Param("status") LaunchStatus status );
    
    
}
