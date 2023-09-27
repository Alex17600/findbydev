package fr.findByDev.api.repositories;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Prefer;
import fr.findByDev.api.models.associations.PreferId;

@Repository
public interface PreferRepository extends GenericRepository<Prefer, PreferId>{
    
}
