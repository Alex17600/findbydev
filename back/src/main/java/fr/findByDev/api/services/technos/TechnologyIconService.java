package fr.findByDev.api.services.technos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import fr.findByDev.api.models.Technology;
import fr.findByDev.api.repositories.global.TechnologyRepository;
import fr.findByDev.api.services.user.FileStorageService;

@Service
public class TechnologyIconService {

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public Technology createTechnologyIcon(Technology technology, MultipartFile icon) {
        fileStorageService.store(icon); 
        String fileName = icon.getOriginalFilename(); 
        String iconPath = "language-icons/" + fileName; 
        technology.setImagePath(iconPath);
        return technologyRepository.save(technology);
    }

    public Technology getTechnologyIconById(Integer id) {
        return technologyRepository.findById(id).orElse(null);
    }

    public void deleteTechnologyIcon(Integer id) {
        Optional<Technology> technology = technologyRepository.findById(id);
        if (technology.isPresent()) {
            // Récupérez le chemin de l'icône depuis la base de données
            String iconPath = technology.get().getImagePath();
            // Supprimez l'icône de la base de données
            technologyRepository.deleteById(id);

            
            if (iconPath != null && !iconPath.isEmpty()) {
                Path iconFile = Paths.get(iconPath);
                if (Files.exists(iconFile)) {
                    try {
                        Files.delete(iconFile);
                    } catch (IOException e) {
                       
                    }
                }
            }
        }
    }
}
