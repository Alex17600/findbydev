package fr.findByDev.api.controllers.global;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Conversation;
import fr.findByDev.api.models.Message;
import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.global.ConversationRepository;
import fr.findByDev.api.repositories.global.MessageRepository;
import fr.findByDev.api.repositories.global.UserRepository;

@RestController
@RequestMapping("/messages")
public class MessageController extends GenericController<Message, Integer> {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    public MessageController(
            MessageRepository messageRepository, UserRepository userRepository,
            ConversationRepository conversationRepository) {
        super(messageRepository);
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
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

    @GetMapping("/{conversationId}/conversation")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public List<Message> getMessagesByConversationId(@PathVariable Integer conversationId) {

        List<Message> messages = messageRepository.findAllMessageByConversationId(conversationId);
        return messages;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public ResponseEntity<?> createMessage(@RequestBody Map<String, Object> data) throws Exception {
        try {
            Integer userSenderId = (Integer) data.get("userSender");
            Integer userReceiverId = (Integer) data.get("userReceiver");
            String contain = (String) data.get("contain");
            Integer conversationId = (Integer) data.get("conversation");
    
            User sender = userRepository.findById(userSenderId).orElse(null);
            User receiver = userRepository.findById(userReceiverId).orElse(null);
            Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
    
            if (sender != null && receiver != null && conversation != null) {
                Message message = new Message();
                message.setUserSender(sender);
                message.setUserReceiver(receiver);
                message.setContain(contain);
                message.setConversation(conversation);
                message.setDateHour(new Date());
    
                messageRepository.save(message);
    
                return new ResponseEntity<>(message, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors de la création du message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Erreur lors de la création du message", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
