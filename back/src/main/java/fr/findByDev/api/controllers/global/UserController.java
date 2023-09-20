package fr.findByDev.api.controllers.global;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.global.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<User, Integer>{

    @Autowired
    private UserRepository userRepository;
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<User> all() {
        return userRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<User> get(@PathVariable Integer id) {
        return userRepository.findById(id);
    }
}
