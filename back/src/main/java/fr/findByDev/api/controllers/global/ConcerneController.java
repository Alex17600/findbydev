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
import fr.findByDev.api.models.Concerne;
import fr.findByDev.api.models.associations.ConcerneId;
import fr.findByDev.api.repositories.global.ConcerneRepository;

@RestController
@RequestMapping("/concernes")
public class ConcerneController extends GenericController<Concerne, ConcerneId> {
    
    @Autowired
    private ConcerneRepository concerneRepository;

    @Autowired
    public ConcerneController(ConcerneRepository concerneRepository) {
        super(concerneRepository);
        this.concerneRepository = concerneRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Concerne> all() {
        return concerneRepository.findAll();
    }

    @GetMapping("/{idTechnology}/{idExperience}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Concerne> get(@PathVariable("idTechnology") Integer idTechnology, @PathVariable("idExperience") Integer idExperience) {
        ConcerneId concerneId = new ConcerneId(idTechnology, idExperience);
        return concerneRepository.findById(concerneId);
    }
}
