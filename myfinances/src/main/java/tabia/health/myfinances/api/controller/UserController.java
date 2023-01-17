package tabia.health.myfinances.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tabia.health.myfinances.exception.AuthenticationError;
import tabia.health.myfinances.exception.BusinessRuleException;
import tabia.health.myfinances.model.entity.User;
import tabia.health.myfinances.service.UserService;
import tabia.health.myfinances.api.dto.UserDTO;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody UserDTO userDTO){
        try{
            User userAuthenticate = userService.authenticate(userDTO.getEmail(), userDTO.getPassword());
            return ResponseEntity.ok(userAuthenticate);
        }
        catch(AuthenticationError e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody UserDTO dto){

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        try{
            User savedUser = userService.save(user);
            return new ResponseEntity(savedUser, HttpStatus.CREATED);
        } catch (BusinessRuleException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    
}
