package fr.findByDev.api.controllers.global;

import java.sql.Timestamp;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Notice;
import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.global.NoticeRepository;
import fr.findByDev.api.repositories.global.UserRepository;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/notices")
public class NoticeController extends GenericController<Notice, Integer> {

    
    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;  

    @Autowired
    public NoticeController(NoticeRepository noticeRepository) {
        super(noticeRepository);
        this.noticeRepository = noticeRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Notice> all() {
        return noticeRepository.findAll();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Notice createNotice(@RequestBody Map<String, Object> dataNotice) {
        try {
            Integer senderId = (Integer) dataNotice.get("senderId");
            Integer receiverId = (Integer) dataNotice.get("receiverId");
    
            User userSender = userRepository.findById(senderId).orElse(null);
        
                // Si la notice n'existe pas, créez-en une nouvelle
                Notice newNotice = new Notice();
                newNotice.setSenderId(senderId);
                newNotice.setReceiverId(receiverId);
                newNotice.setMessage("Votre match a été accepté.");
                newNotice.setIsRead(false);
                newNotice.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    
                noticeRepository.save(newNotice);
    
                // Utilisez la connexion WebSocket pour envoyer une notification au sender
                String notificationMessage = "Votre match a été accepté.";
                messagingTemplate.convertAndSendToUser(userSender.getPseudo(), "/app/notice/" + userSender.getPseudo() + "/queue/notifications", notificationMessage);

    
                return newNotice; 
            
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID utilisateur non valide.");
        }
    }    
}