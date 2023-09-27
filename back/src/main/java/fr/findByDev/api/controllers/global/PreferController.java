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
import fr.findByDev.api.models.Prefer;
import fr.findByDev.api.models.associations.PreferId;
import fr.findByDev.api.repositories.PreferRepository;

@RestController
@RequestMapping("/prefers")
public class PreferController extends GenericController<Prefer, PreferId> {

    @Autowired
    private PreferRepository preferRepository;

    @Autowired
    public PreferController(PreferRepository preferRepository) {
        super(preferRepository);
        this.preferRepository = preferRepository;
    }

        @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Prefer> all() {
        return preferRepository.findAll();
    }

    @GetMapping("/{idUser}/{idTechnology}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Prefer> get(@PathVariable("idUser") Integer idUser, @PathVariable("idTechnology") Integer idTechnology) {
        PreferId preferId = new PreferId(idUser, idTechnology);
        return preferRepository.findById(preferId);
    }
}
