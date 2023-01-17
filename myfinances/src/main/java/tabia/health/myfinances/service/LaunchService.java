package tabia.health.myfinances.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import tabia.health.myfinances.exception.BusinessRuleException;
import tabia.health.myfinances.model.entity.Launch;
import tabia.health.myfinances.model.enums.LaunchStatus;
import tabia.health.myfinances.repository.LaunchRepository;

@Service
public class LaunchService {
    
    @Autowired
    LaunchRepository launchRepository;

    public Launch save(Launch launch){
        validate(launch);
        //o usuario precisa aprovar o lançamento
        launch.setLaunchStatus(LaunchStatus.PENDING);
        return launchRepository.save(launch);
    }
    
    public Launch update(Launch launch){
        Objects.requireNonNull(launch.getId());
        validate(launch);
        return launchRepository.save(launch);
    }

    public void delete(Launch launch){
        Objects.requireNonNull(launch.getId());
        launchRepository.delete(launch);
    }
    
    public Optional<Launch> findById(Long id){
        return launchRepository.findById(id);
    }

    public List<Launch> getLaunchList(Launch filterLaunch){
        Example example = Example.of(filterLaunch, ExampleMatcher.matching().
        withIgnoreCase().
        withStringMatcher(StringMatcher.CONTAINING));

        return launchRepository.findAll(example);
    }

    public void updateStatus(Launch launch, LaunchStatus status){
        launch.setLaunchStatus(status);
        update(launch);
    }

    private void validate(Launch launch){
        //verifica se é nulo ou se o usuario apenas colocou espaços
        if(launch.getDescription() == null || launch.getDescription().trim().equals("")){
            throw new BusinessRuleException("Enter a valid description.");
        }
        if( launch.getMonth() == null || launch.getMonth() < 1 || launch.getMonth() > 12){
            throw new BusinessRuleException ("Inform a valid month.");
        }
        if(launch.getYear() == null || launch.getYear() < 1 || Integer.toString(launch.getYear()).length() != 4){
            throw new BusinessRuleException ("Inform a valid year.");
        }
        if(launch.getUser() == null || launch.getUser().getId() == null){
            throw new BusinessRuleException("Inform a valid user.");
        }
        if(launch.getValue() == null || launch.getValue().compareTo(BigDecimal.ZERO) < 1){
            throw new BusinessRuleException ("Inform a valid value.");
        }
        if(launch.getLaunchType() == null){
            throw new BusinessRuleException ("Inform a launch type.");
        }
    }

}
