package fr.findByDev.api.controllers.global;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Alert;
import fr.findByDev.api.repositories.global.AlertRepository;

@RestController
@RequestMapping("alerts")
public class AlertController extends GenericController<Alert, Integer> {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    public AlertController(AlertRepository alertRepository) {
        super(alertRepository);
        this.alertRepository = alertRepository;
    }
    
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Alert> all() {
        return alertRepository.findAll();
    }
    
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Alert> get(@PathVariable Integer id) {
        return alertRepository.findById(id);
    }
}
