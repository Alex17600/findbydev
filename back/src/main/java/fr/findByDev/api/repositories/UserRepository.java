package fr.findByDev.api.repositories;

import org.springframework.stereotype.Repository;
import fr.findByDev.api.models.User;


@Repository
public interface UserRepository extends GenericRepository<User, Integer>{
    
    User findByMail(String mail);
}
