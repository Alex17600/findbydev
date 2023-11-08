package fr.findByDev.api.repositories.global;

import org.springframework.stereotype.Repository;

import fr.findByDev.api.models.GitProject;
import fr.findByDev.api.repositories.GenericRepository;

@Repository
public interface GitProjectRepository extends GenericRepository<GitProject, Integer>{
    
}
