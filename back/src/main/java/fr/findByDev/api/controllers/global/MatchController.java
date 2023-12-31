package fr.findByDev.api.controllers.global;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Conversation;
import fr.findByDev.api.models.Match;

import fr.findByDev.api.models.User;
import fr.findByDev.api.models.DTO.MatchRequestDTO;
import fr.findByDev.api.models.associations.MatchId;
import fr.findByDev.api.models.enums.Status;
import fr.findByDev.api.repositories.global.ConversationRepository;
import fr.findByDev.api.repositories.global.MatchRepository;

import fr.findByDev.api.repositories.global.UserRepository;

@RestController
@RequestMapping("/matches")
public class MatchController extends GenericController<Match, MatchId> {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    public MatchController(MatchRepository matchRepository, UserRepository userRepository, ConversationRepository conversationRepository) {
        super(matchRepository);
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
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
    public Optional<Match> get(@PathVariable("idReceiver") Integer idReceiver,
            @PathVariable("idSender") Integer idSender) {
        MatchId matchId = new MatchId(idReceiver, idSender);
        return matchRepository.findById(matchId);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public ResponseEntity<?> createMatch(@RequestBody MatchRequestDTO matchData) {
        Integer userSenderId = matchData.getUserSender();
        Integer userReceiverId = matchData.getUserReceiver();
        String swipeDirection = matchData.getSwipeDirection();

        // Recherchez les objets User correspondants dans la base de données
        User userSender = userRepository.findById(userSenderId).orElse(null);
        User userReceiver = userRepository.findById(userReceiverId).orElse(null);

        if (userSender == null || userReceiver == null) {
            throw new IllegalArgumentException("Utilisateur non trouvé.");
        }

        // Si la direction du swipe est "right", créez le match
        if ("right".equals(swipeDirection)) {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            MatchId matchId = new MatchId(userSenderId, userReceiverId);

            Match match = new Match();
            match.setIdMatch(matchId);
            match.setSender(userSender);
            match.setReceiver(userReceiver);
            match.setDateHour(currentTimestamp);
            match.setCurrentStatus(Status.EN_ATTENTE);
            match.setIsRead(false);
            userReceiver.setPopularity(userReceiver.getPopularity() + 5);

            userRepository.save(userReceiver);
            Match savedMatch = matchRepository.save(match);

            if (savedMatch.getCurrentStatus() == Status.VALIDE) {
            Conversation conversation = new Conversation();
            conversation.setDateDebut(currentTimestamp);
            conversation.setArchived(false);
            conversation.setUserSender(userSenderId);
            conversation.setUserReceiver(userReceiverId);

            conversationRepository.save(conversation);
        }

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else if ("left".equals(swipeDirection)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/update-status")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Match updateMatchStatus(@RequestBody Map<String, Object> data) {
        try {
            Integer idUserReceiver = (Integer) data.get("receiver");
            Integer idUserSender = (Integer) data.get("sender");
            String newStatus = (String) data.get("newStatus");
            User userSender = userRepository.findById(idUserSender).orElse(null);

            Match match = matchRepository.findByIdMatch(idUserSender, idUserReceiver);

            if (match != null) {

                if ("VALIDE".equals(newStatus) || "REFUSE".equals(newStatus)) {
                    match.setCurrentStatus(Status.valueOf(newStatus));
                    userSender.setPopularity(userSender.getPopularity() + 5);
                    return matchRepository.save(match);
                } else {
                    throw new IllegalArgumentException("Statut invalide.");
                }
            } else {
                throw new IllegalArgumentException("Match introuvable.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID utilisateur non valide.");
        }
    }
}
