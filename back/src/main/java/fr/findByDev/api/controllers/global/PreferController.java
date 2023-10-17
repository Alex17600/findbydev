package fr.findByDev.api.controllers.global;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Prefer;
import fr.findByDev.api.models.Technology;
import fr.findByDev.api.models.User;
import fr.findByDev.api.models.DTO.PreferDTO;
import fr.findByDev.api.models.associations.PreferId;
import fr.findByDev.api.repositories.PreferRepository;
import fr.findByDev.api.repositories.global.TechnologyRepository;
import fr.findByDev.api.repositories.global.UserRepository;

@RestController
@RequestMapping("/prefers")
public class PreferController extends GenericController<Prefer, PreferId> {

    @Autowired
    private PreferRepository preferRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    public PreferController(PreferRepository preferRepository, UserRepository userRepository,
            TechnologyRepository technologyRepository) {
        super(preferRepository);
        this.preferRepository = preferRepository;
        this.userRepository = userRepository;
        this.technologyRepository = technologyRepository;
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
    public Optional<Prefer> get(@PathVariable("idUser") Integer idUser,
            @PathVariable("idTechnology") Integer idTechnology) {
        PreferId preferId = new PreferId(idUser, idTechnology);
        return preferRepository.findById(preferId);
    }

    @PostMapping("create")
    @CrossOrigin
    public List<Prefer> save(@RequestBody PreferDTO preferData) {
        Integer idUser = preferData.getIdUser();
        List<Integer> idTechnologies = preferData.getIdTechnologys();

        User user = userRepository.findById(idUser).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé.");
        }

        List<Prefer> prefers = new ArrayList<>();

        for (Integer idTechnology : idTechnologies) {
            Technology technology = technologyRepository.findById(idTechnology).orElse(null);

            if (technology != null) {
                PreferId preferId = new PreferId(idUser, idTechnology);

                Prefer prefer = new Prefer();
                prefer.setIdPrefer(preferId);
                prefer.setUser(user);
                prefer.setTechnology(technology);

                prefers.add(preferRepository.save(prefer));
            }
        }

        return prefers;
    }
}
