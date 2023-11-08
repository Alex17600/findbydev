package fr.findByDev.api.controllers.global;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.GitProject;
import fr.findByDev.api.repositories.global.GitProjectRepository;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/gitprojects")
public class GitProjectControlleur extends GenericController<GitProject, Integer> {
    @Autowired
    private GitProjectRepository gitProjectRepository;

    @Autowired
    public GitProjectControlleur(GitProjectRepository gitProjectRepository) {
        super(gitProjectRepository);
        this.gitProjectRepository = gitProjectRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<GitProject> all() {
        return gitProjectRepository.findAll();
    }
}
