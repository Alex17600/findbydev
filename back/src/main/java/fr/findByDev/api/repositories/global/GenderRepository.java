package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;
import fr.findByDev.api.models.Gender;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface GenderRepository extends GenericRepository<Gender, Integer>{
}
