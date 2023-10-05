package fr.findByDev.api.repositories.global;


import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Match;
import fr.findByDev.api.models.associations.MatchId;
import fr.findByDev.api.models.enums.Status;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface MatchRepository extends GenericRepository<Match, MatchId>{
    boolean existsByReceiverIdAndCurrentStatus(Integer receiverId, Status currentStatus);
}
