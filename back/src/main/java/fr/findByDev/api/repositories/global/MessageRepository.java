package fr.findByDev.api.repositories.global;

import java.util.List;


import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Message;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface MessageRepository extends GenericRepository<Message, Integer>{
    List<Message> findAllByidConversation(Integer conversationId);
}