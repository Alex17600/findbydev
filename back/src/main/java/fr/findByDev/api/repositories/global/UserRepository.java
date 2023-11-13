package fr.findByDev.api.repositories.global;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.GenericRepository;


@Repository
public interface UserRepository extends GenericRepository<User, Integer>{
    User findByPseudo(String pseudo);
    User findByMail(String mail);
   
        @Query("SELECT u FROM User u WHERE " +
           "(:pseudo IS NULL OR UPPER(u.pseudo) LIKE UPPER(:pseudo)) " +
           "AND (:town IS NULL OR UPPER(u.town) LIKE UPPER(:town)) " +
           "AND (:gitProfile IS NULL OR UPPER(u.gitProfile) LIKE UPPER(:gitProfile)) " +
           "AND (:genderId IS NULL OR u.gender.id = :genderId)")
    List<User> searchUsers(
        @Param("pseudo") String pseudo,
        @Param("town") String town,
        @Param("gitProfile") String gitProfile,
        @Param("genderId") Integer genderId
    );
}
