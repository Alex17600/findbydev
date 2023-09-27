package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Experience;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface ExperienceRepository extends GenericRepository<Experience, Integer>{
    
}
