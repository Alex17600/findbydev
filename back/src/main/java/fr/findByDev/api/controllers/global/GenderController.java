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
import fr.findByDev.api.models.Gender;
import fr.findByDev.api.repositories.global.GenderRepository;

@RestController
@RequestMapping("/genders")
public class GenderController extends GenericController<Gender, Integer>{

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    public GenderController(GenderRepository genderRepository){
        super(genderRepository);
        this.genderRepository = genderRepository;
    }

    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Gender> all() {
        return genderRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Gender> get(@PathVariable Integer id) {
        return genderRepository.findById(id);
    }
}
