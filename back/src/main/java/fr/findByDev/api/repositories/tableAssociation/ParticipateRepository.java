package fr.findByDev.api.repositories.tableAssociation;

import org.springframework.stereotype.Repository;
import fr.findByDev.api.models.associations.Participate;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface ParticipateRepository extends GenericRepository<Participate, Integer>{
    
}
