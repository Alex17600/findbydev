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
import fr.findByDev.api.models.Like;
import fr.findByDev.api.models.associations.LikeId;
import fr.findByDev.api.repositories.global.LikeRepository;

@RestController
@RequestMapping("/likes")
public class LikeController extends GenericController<Like, LikeId>{
    
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    public LikeController(LikeRepository likeRepository) {
        super(likeRepository);
        this.likeRepository = likeRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Like> all() {
        return likeRepository.findAll();
    }

    @GetMapping("/{idUser}/{idExperience}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Like> get(@PathVariable("idUser") Integer idUser, @PathVariable("idExperience") Integer idExperience) {
        LikeId likeId = new LikeId(idUser, idExperience);
        return likeRepository.findById(likeId);
    }
}
