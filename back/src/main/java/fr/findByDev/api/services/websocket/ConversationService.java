package fr.findByDev.api.services.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.findByDev.api.models.Conversation;
import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.global.ConversationRepository;
import fr.findByDev.api.repositories.global.UserRepository;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Autowired
    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public Conversation createConversation(Integer userId1, Integer userId2) {

        User user1 = userRepository.findById(userId1).orElse(null);
        User user2 = userRepository.findById(userId2).orElse(null);

        if (user1 != null && user2 != null) {
            // Créez une nouvelle conversation
            Conversation conversation = new Conversation();
            conversation.setSender(user1);
            conversation.setReceiver(user2);
            conversation.setArchived(false);

            conversation = conversationRepository.save(conversation);

            return conversation;
        }

        return null;
    }
}

