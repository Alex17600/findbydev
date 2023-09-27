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
import fr.findByDev.api.models.Technology;
import fr.findByDev.api.repositories.global.TechnologyRepository;

@RestController
@RequestMapping("/technologies")
public class TechnologyController extends GenericController<Technology, Integer> {
    
    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    public TechnologyController(TechnologyRepository technologyRepository){
        super(technologyRepository);
        this.technologyRepository = technologyRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Technology> all() {
        return technologyRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Technology> get(@PathVariable Integer id) {
        return technologyRepository.findById(id);
    }

}
