package fr.findByDev.api.services.technos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.findByDev.api.models.Technology;
import fr.findByDev.api.repositories.global.TechnologyRepository;

import java.io.File;

@Service
public class UpdateIconPathsService {

    @Autowired
    private TechnologyRepository technologyRepository;

    public void updateIconPaths() {
        // Chemin vers le répertoire de stockage des icônes
        String iconDirectory = "language-icons/"; // Assurez-vous d'utiliser le bon chemin

        // Récupérez la liste des fichiers dans le répertoire
        File iconDir = new File(iconDirectory);
        File[] iconFiles = iconDir.listFiles();

        if (iconFiles != null) {
            for (File iconFile : iconFiles) {
                String iconFileName = iconFile.getName();
                // Recherchez la technologie par nom d'icône (supposons que le nom de l'icône corresponde au nom de la technologie)
                Technology technology = technologyRepository.findByName(iconFileName);
                if (technology != null) {
                    // Mettez à jour le chemin de l'icône dans la base de données
                    technology.setImagePath("language-icons/" + iconFileName);
                    technologyRepository.save(technology);
                }
            }
        }
    }
}

