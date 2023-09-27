package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Like;
import fr.findByDev.api.models.associations.LikeId;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface LikeRepository extends GenericRepository<Like, LikeId> {
    
}
