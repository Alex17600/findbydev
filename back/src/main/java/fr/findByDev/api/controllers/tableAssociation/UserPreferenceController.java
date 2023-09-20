package fr.findByDev.api.controllers.tableAssociation;

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
import fr.findByDev.api.models.associations.UserPreference;
import fr.findByDev.api.repositories.tableAssociation.UserPreferenceRepository;

@RestController
@RequestMapping("/user-preferences")
public class UserPreferenceController extends GenericController<UserPreference, Integer>{
    
    @Autowired
    private UserPreferenceRepository userPreferenceRepository;

    @Autowired
    public UserPreferenceController (UserPreferenceRepository userPreferenceRepository) {
        super(userPreferenceRepository);
        this.userPreferenceRepository = userPreferenceRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<UserPreference> all() {
        return userPreferenceRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<UserPreference> get(@PathVariable Integer id) {
        return userPreferenceRepository.findById(id);
    }
}
