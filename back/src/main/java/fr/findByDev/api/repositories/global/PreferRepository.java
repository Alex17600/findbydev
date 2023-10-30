package fr.findByDev.api.repositories.global;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Prefer;
import fr.findByDev.api.models.associations.PreferId;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface PreferRepository extends GenericRepository<Prefer, PreferId>{
    @Query("SELECT p FROM Prefer p WHERE p.user.idUser = :userId")
    List<Prefer> findAllByUserIdUser(@Param("userId") Integer userId);
}
