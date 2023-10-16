package fr.findByDev.api.services.technos;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class IconStorageService {

    @Value("${language-icon-storage.path}")
    private String iconStoragePath;  

    public String storeIcon(MultipartFile icon) throws IOException {
        Path targetLocation = Paths.get(iconStoragePath).resolve(icon.getOriginalFilename());
        Files.copy(icon.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        return icon.getOriginalFilename(); // Retourne le nom du fichier stocké
    }
    
    public Path loadIcon(String iconFileName) {
        return Paths.get(iconStoragePath).resolve(iconFileName).normalize();
    }
    
    public void deleteIcon(String iconName) throws IOException {
        Path iconPath = Paths.get(iconStoragePath).resolve(iconName);
        Files.delete(iconPath);
    }
}

