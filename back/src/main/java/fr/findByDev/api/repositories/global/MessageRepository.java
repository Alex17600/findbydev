package fr.findByDev.api.repositories.global;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Message;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface MessageRepository extends GenericRepository<Message, Integer> {
    @Query("SELECT m FROM Message m WHERE m.conversation.idConversation = :idConversation")
    List<Message> findAllMessageByConversationId(Integer idConversation);
}