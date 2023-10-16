package fr.findByDev.api.controllers.global;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Technology;
import fr.findByDev.api.repositories.global.TechnologyRepository;
import fr.findByDev.api.services.technos.IconStorageService;

@RestController
@RequestMapping("/technologies")
public class TechnologyController extends GenericController<Technology, Integer> {

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private IconStorageService iconStorageService;

    @Autowired
    public TechnologyController(TechnologyRepository technologyRepository) {
        super(technologyRepository);
        this.technologyRepository = technologyRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<Technology> all() {
        return technologyRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<Technology> get(@PathVariable Integer id) {
        return technologyRepository.findById(id);
    }

    // save une image de techno
    @PostMapping("/{id}/icon")
    @CrossOrigin
    public ResponseEntity<String> createTechnologyIcon(
            @PathVariable Integer id,
            @RequestParam("icon") MultipartFile icon) {
        Technology technology = technologyRepository.findById(id).orElse(null);
        if (technology != null && !icon.isEmpty()) {
            try {
                // Stocker l'icône en utilisant IconStorageService
                String iconFileName = iconStorageService.storeIcon(icon);
                String iconPath = "icons/" + iconFileName;
                technology.setImagePath(iconPath);
                technologyRepository.save(technology);
                return ResponseEntity.ok(iconPath);
            } catch (IOException e) {
                return ResponseEntity.badRequest().body("Impossible de créer l'icône de technologie.");
            }
        } else {
            return ResponseEntity.badRequest().body("Impossible de créer l'icône de technologie.");
        }
    }

    // get l'image associé à la techno
    @GetMapping("/{id}/icon")
    @CrossOrigin
    public ResponseEntity<Resource> getTechnologyIcon(@PathVariable Integer id) throws IOException {
        Technology technology = technologyRepository.findById(id).orElse(null);
        
        if (technology != null && technology.getImagePath() != null) {
            String iconFileName = Paths.get(technology.getImagePath()).getFileName().toString();
            String mimeType = Files.probeContentType(Paths.get(technology.getImagePath()));
        
            if (mimeType != null) {
                try {
                    Path iconPath = iconStorageService.loadIcon(iconFileName);
                    byte[] iconBytes = Files.readAllBytes(iconPath);
                    ByteArrayResource resource = new ByteArrayResource(iconBytes);
        
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + iconFileName);
                    headers.add(HttpHeaders.CONTENT_TYPE, mimeType);
        
                    return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(iconBytes.length)
                        .body(resource);
                } catch (IOException e) {
                    return ResponseEntity.notFound().build();
                }
            }
        }
        
        return ResponseEntity.notFound().build();
    }
    
}
