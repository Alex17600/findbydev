package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Concerne;
import fr.findByDev.api.models.associations.ConcerneId;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface ConcerneRepository extends GenericRepository<Concerne, ConcerneId> {
    
}
