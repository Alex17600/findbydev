package fr.findByDev.api.controllers.global;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.ByteArrayOutputStream;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.MediaType;
import java.security.MessageDigest;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.global.UserRepository;
import fr.findByDev.api.services.user.FileStorageService;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<User, Integer> {

    Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public UserController(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    @Override
    public Iterable<User> all() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<User> get(@PathVariable Integer id) {
        return userRepository.findById(id);
    }

    @PostMapping(value = "/create-user", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public ResponseEntity<?> createUser(@RequestPart("userData") User userData,
            @RequestPart("photo") MultipartFile photo) {
        try {
            String password = userData.getPassword();
            String encryptedPassword = passwordEncoder.encode(password);

            User newUser = new User();
            newUser.setLastName(userData.getLastName());
            newUser.setFirstName(userData.getFirstName());
            newUser.setTown(userData.getTown());
            newUser.setBirthday(userData.getBirthday());
            newUser.setMail(userData.getMail());
            newUser.setPassword(encryptedPassword);
            newUser.setActiveAccount(false);
            newUser.setDescription(userData.getDescription());
            newUser.setGitProfile(userData.getGitProfile());
            newUser.setGender(userData.getGender());
            newUser.setType("U");

            if (!photo.isEmpty()) {
                logger.info("Sauvegarde du fichier image");

                String storageHash = getStorageHash(photo).orElse(null);
                if (storageHash != null) {
                    Path rootLocation = this.fileStorageService.getRootLocation();
                    String fileExtension = mimeTypeToExtension(photo.getContentType());
                    storageHash = storageHash + fileExtension;
                    Path saveLocation = rootLocation.resolve(storageHash);

                    // suppression du fichier au besoin
                    Files.deleteIfExists(saveLocation);

                    // tentative de sauvegarde
                    Files.copy(photo.getInputStream(), saveLocation, StandardCopyOption.REPLACE_EXISTING);

                    newUser.setPhoto(storageHash);
                }
            }

            newUser = userRepository.save(newUser);

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors de la création de l'utilisateur",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // methode de recuperation du dossier image
    private Optional<String> getStorageHash(MultipartFile file) {
        String hashString = null;

        if (!file.isEmpty()) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");

                // La méthode digest de la classe "MessageDigest" prend en paramètre un byte[]
                // il faut donc transformer les différents objets utilisés pour le hachage en
                // tableau d'octets
                // Nous utiliserons la classe "ByteArrayOutputStream" pour se faire
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                outputStream.write(file.getOriginalFilename().getBytes());
                outputStream.write(file.getContentType().getBytes());
                LocalDate date = LocalDate.now();
                outputStream.write(date.toString().getBytes());

                // calcul du hash, on obtient un tableau d'octets
                byte[] hashBytes = messageDigest.digest(outputStream.toByteArray());

                // on retrouve une chaîne de caractères à partir d'un tableau d'octets
                hashString = String.format("%032x", new BigInteger(1, hashBytes));
            } catch (NoSuchAlgorithmException | IOException e) {
                logger.error(e.getMessage());
            }
        }

        return Optional.ofNullable(hashString);
    }

    /**
     * Retourne l'extension d'un fichier en fonction d'un type MIME
     * pour plus d'informations sur les types MIME :
     * https://developer.mozilla.org/fr/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
     */
    private String mimeTypeToExtension(String mimeType) {
        return switch (mimeType) {
            case "image/jpeg" -> ".jpeg";
            case "image/png" -> ".png";
            case "image/svg" -> ".svg";
            default -> "";
        };
    }

    @GetMapping(value = "/photos/{userId}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> getImage(@PathVariable Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String imageName = user.getPhoto();

            if (imageName != null) {
                try {
                    Path imagePath = fileStorageService.load(imageName);
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
                } catch (IOException e) {
                    logger.error("Erreur lors de la lecture de l'image : " + e.getMessage());
                }
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found.");
    }
}
