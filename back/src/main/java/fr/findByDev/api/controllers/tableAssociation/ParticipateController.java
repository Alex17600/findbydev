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
import fr.findByDev.api.models.associations.Participate;
import fr.findByDev.api.repositories.tableAssociation.ParticipateRepository;

@RestController
@RequestMapping("/participates")
public class ParticipateController extends GenericController<Participate, Integer> {

    @Autowired
    private ParticipateRepository participateRepository;

    @Autowired
    public ParticipateController (ParticipateRepository participateRepository) {
        super(participateRepository);
        this.participateRepository = participateRepository;
    }

    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Participate> all() {
        return participateRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Participate> get(@PathVariable Integer id) {
        return participateRepository.findById(id);
    }
    
}
