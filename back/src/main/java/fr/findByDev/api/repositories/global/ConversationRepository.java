package fr.findByDev.api.repositories.global;


import java.util.List;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Conversation;

import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface ConversationRepository extends GenericRepository<Conversation, Integer>{
    List<Conversation> findByUser1OrUser2(Integer user1, Integer user2);
}
