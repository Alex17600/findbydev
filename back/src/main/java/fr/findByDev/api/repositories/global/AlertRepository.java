package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.Alert;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface AlertRepository extends GenericRepository<Alert, Integer> {
    
}
