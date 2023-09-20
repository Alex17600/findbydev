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
import fr.findByDev.api.models.Message;
import fr.findByDev.api.repositories.global.MessageRepository;

@RestController
@RequestMapping("/messages")
public class MessageController extends GenericController<Message, Integer> {
    
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        super(messageRepository);
        this.messageRepository = messageRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Message> all() {
        return messageRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Message> get(@PathVariable Integer id) {
        return messageRepository.findById(id);
    }
}
