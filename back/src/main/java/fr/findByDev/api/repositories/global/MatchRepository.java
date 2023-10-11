package fr.findByDev.api.repositories.global;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Match;
import fr.findByDev.api.models.User;
import fr.findByDev.api.models.associations.MatchId;
import fr.findByDev.api.models.enums.Status;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface MatchRepository extends GenericRepository<Match, MatchId>{
    List<Match> findByReceiverAndIsReadAndCurrentStatus(User receiver, boolean isRead, Status currentStatus);

    List<Match> findByReceiverAndCurrentStatus(User receiver, Status currentStatus);
    
   @Query("SELECT m FROM Match m WHERE m.idMatch.idUserSender = :idUserSender AND m.idMatch.idUserReceiver = :idUserReceiver")
    Match findByIdMatch(@Param("idUserSender") Integer idUserSender, @Param("idUserReceiver") Integer idUserReceiver);
}
