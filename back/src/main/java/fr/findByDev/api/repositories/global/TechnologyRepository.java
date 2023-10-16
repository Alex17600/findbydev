package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;
import fr.findByDev.api.models.Technology;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface TechnologyRepository extends GenericRepository<Technology, Integer>{
    Technology findByName(String name);
}
