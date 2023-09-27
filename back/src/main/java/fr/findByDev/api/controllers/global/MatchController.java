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
import fr.findByDev.api.models.Match;
import fr.findByDev.api.models.associations.MatchId;
import fr.findByDev.api.repositories.global.MatchRepository;

@RestController
@RequestMapping("/matches")
public class MatchController extends GenericController<Match, MatchId> {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    public MatchController(MatchRepository matchRepository) {
        super(matchRepository);
        this.matchRepository = matchRepository;
    }
    
        
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Match> all() {
        return matchRepository.findAll();
    }
    
    
    @GetMapping("/{idReceiver}/{idSender}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Match> get(@PathVariable("idReceiver") Integer idReceiver, @PathVariable("idSender") Integer idSender) {
        MatchId matchId = new MatchId(idReceiver, idSender);
        return matchRepository.findById(matchId);
    }
}
