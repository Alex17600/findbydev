package fr.findByDev.api.controllers.global;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Conversation;
import fr.findByDev.api.repositories.global.ConversationRepository;

@RestController
@RequestMapping("/conversations")
public class ConversationController extends GenericController<Conversation, Integer> {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    public ConversationController(ConversationRepository conversationRepository) {
        super(conversationRepository);
        this.conversationRepository = conversationRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Conversation> all() {
        return conversationRepository.findAll();
    }

    @GetMapping("/{idUser}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public List<Conversation> getConversationFromidUser() {
    }
}
