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
import fr.findByDev.api.models.Realise;
import fr.findByDev.api.models.associations.RealiseId;
import fr.findByDev.api.repositories.global.RealiseRepository;

@RestController
@RequestMapping("/realises")
public class RealiseController extends GenericController<Realise, RealiseId> {
    
    @Autowired
    private RealiseRepository realiseRepository;

    @Autowired
    public RealiseController(RealiseRepository realiseRepository) {
        super(realiseRepository);
        this.realiseRepository = realiseRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Realise> all() {
        return realiseRepository.findAll();
    }
    
    
    @GetMapping("/{idUser}/{idExperience}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Realise> get(@PathVariable("idUser") Integer idUser, @PathVariable("idExperience") Integer idExperience) {
        RealiseId realiseId = new RealiseId(idUser, idExperience);
        return realiseRepository.findById(realiseId);
    }
}
