package fr.findByDev.api.repositories.tableAssociation;

import org.springframework.stereotype.Repository;
import fr.findByDev.api.models.associations.UserPreference;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface UserPreferenceRepository extends GenericRepository<UserPreference, Integer>{
    
}
