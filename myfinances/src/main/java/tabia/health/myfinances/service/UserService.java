package tabia.health.myfinances.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tabia.health.myfinances.exception.AuthenticationError;
import tabia.health.myfinances.exception.BusinessRuleException;
import tabia.health.myfinances.model.entity.User;
import tabia.health.myfinances.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    public User authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        
        if(!user.isPresent()){
            throw new AuthenticationError("User not found!");
        }

        if(! user.get().getPassword().equals(password)){
            throw new AuthenticationError("invalid password!");
        }

        return user.get();
    }

    @Transactional
    public User save(User user) {
        validateEmail(user.getEmail());
        return userRepository.save(user);
    }

    public void validateEmail(String email) {
        boolean exists = userRepository.existsByEmail(email);
        if(exists){
            throw new BusinessRuleException("There is already a user registered with this Email.");
        }
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
