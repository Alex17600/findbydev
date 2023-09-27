package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Realise;
import fr.findByDev.api.models.associations.RealiseId;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface RealiseRepository extends GenericRepository<Realise, RealiseId> {
    
}
