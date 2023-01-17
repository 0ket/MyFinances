package tabia.health.myfinances.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tabia.health.myfinances.api.dto.LaunchDTO;
import tabia.health.myfinances.exception.BusinessRuleException;
import tabia.health.myfinances.model.entity.Launch;
import tabia.health.myfinances.model.entity.User;
import tabia.health.myfinances.model.enums.LaunchStatus;
import tabia.health.myfinances.model.enums.LaunchType;
import tabia.health.myfinances.service.LaunchService;
import tabia.health.myfinances.service.UserService;

@RestController
@RequestMapping("/api/launch")
public class LaunchController {
    
    @Autowired
    private LaunchService launchService;

    @Autowired
    private UserService userService;

    //cria no servidor
    @PostMapping("/save")
    public ResponseEntity save(@RequestBody LaunchDTO launchDTO)
    {
        try{
            Launch launch = convertLaunchDTO(launchDTO);
            launch = launchService.save(launch);
            return new ResponseEntity(launch, HttpStatus.CREATED);
        }catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //atualiza no servidor
    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody LaunchDTO launchDTO){
        
        return launchService.findById(id).map( entityLaunch -> {
			try {
				Launch launch = convertLaunchDTO(launchDTO);
				launch.setId(entityLaunch.getId());
				launchService.update(launch);
				return ResponseEntity.ok(launch);
			}catch (BusinessRuleException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		    }).orElseGet( () ->
			new ResponseEntity("Launch not find.", HttpStatus.BAD_REQUEST) );
    
    }

    @DeleteMapping("/delete/{id}")
	public ResponseEntity delete( @PathVariable("id") Long id ) {
		return launchService.findById(id).map( entityLaunch -> {
			launchService.delete(entityLaunch);
			return new ResponseEntity( HttpStatus.NO_CONTENT );
		}).orElseGet( () -> 
			new ResponseEntity("Launch not find.", HttpStatus.BAD_REQUEST) );
	}

    //value -> false para ser opcional o usuario passar
    @GetMapping("/search")
	public ResponseEntity search(
			@RequestParam(value ="description" , required = false) String description,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam("user") Long userId
			) {
		
		Launch launchFilter = new Launch();
		launchFilter.setDescription(description);
		launchFilter.setMonth(month);
		launchFilter.setYear(year);
		
		Optional<User> user = userService.findById(userId);
		if(!user.isPresent()) {
			return ResponseEntity.badRequest().body("Could not perform. User not found for the given Id.");
		}else {
			launchFilter.setUser(user.get());
		}
		
		List<Launch> launch = launchService.getLaunchList(launchFilter);
		return ResponseEntity.ok(launch);
	}
    @PutMapping("/update-status/{id}")
	public ResponseEntity updateStatus( @PathVariable("id") Long id , @RequestBody String status ) {
		return launchService.findById(id).map( entityLaunch -> {
			LaunchStatus launchStatus = LaunchStatus.valueOf(status);
			
			if(launchStatus == null) {
				return ResponseEntity.badRequest().body("Unable to update, please submit a valid status");
			}
			
			try {
				entityLaunch.setLaunchStatus(launchStatus);
				launchService.update(entityLaunch);
				return ResponseEntity.ok(entityLaunch);
			}catch (BusinessRuleException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		
		}).orElseGet( () ->
		new ResponseEntity("Lancamento não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}

    private Launch convertLaunchDTO( LaunchDTO launchDTO )
    {
        Launch launch = new Launch();
        launch.setId(launchDTO.getId());
        launch.setDescription(launchDTO.getDescription());
        launch.setYear(launchDTO.getYear());
        launch.setMonth(launchDTO.getMonth());
        launch.setValue(launchDTO.getValue());

        User user = userService.findById(launchDTO.getId()).orElseThrow(()-> new BusinessRuleException("User not found."));
        launch.setUser(user);
        launch.setLaunchType(LaunchType.valueOf(launchDTO.getLaunchType()));
        launch.setLaunchStatus(LaunchStatus.valueOf(launchDTO.getLaunchStatus()));

        return launch;
    }
}