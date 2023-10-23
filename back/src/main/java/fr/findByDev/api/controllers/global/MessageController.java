package fr.findByDev.api.controllers.global;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Conversation;
import fr.findByDev.api.models.Message;
import fr.findByDev.api.repositories.global.MessageRepository;
import fr.findByDev.api.services.websocket.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController extends GenericController<Message, Integer> {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    public MessageController(
            MessageRepository messageRepository,
            SimpMessagingTemplate messagingTemplate,
            MessageService messageService) {
        super(messageRepository);
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
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

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Message message) {

        Message savedMessage = messageService.save(message);

        messagingTemplate.convertAndSend("/topic/public", savedMessage);
    }

    @GetMapping("/{conversationId}/conversation")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public List<Message> getMessagesByConversationId(@PathVariable Integer conversationId) {

        List<Message> messages = messageRepository.findMessagesByConversationId(conversationId);
        return messages;
    }
    
}
