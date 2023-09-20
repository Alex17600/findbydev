package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;
import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.GenericRepository;


@Repository
public interface UserRepository extends GenericRepository<User, Integer>{
    
    User findBymail(String mail);
}
