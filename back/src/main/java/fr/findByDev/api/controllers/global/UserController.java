package fr.findByDev.api.controllers.global;

import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Optional;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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

    //Recuperation de la photo d'un user
    @GetMapping("/{idUser}/photo")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Integer idUser) {
        Optional<User> optionalUser = userRepository.findById(idUser);
    
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String photoFileName = user.getPhoto(); 
    
            if (photoFileName != null) {
                Resource photoResource = fileStorageService.loadAsResource(photoFileName);
    
                if (photoResource.exists() && photoResource.isReadable()) {
                    try {
                        InputStream photoInputStream = photoResource.getInputStream();
                        byte[] buffer = new byte[8192]; 
                        int bytesRead;
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
                        while ((bytesRead = photoInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
    
                        byte[] photoBytes = outputStream.toByteArray();
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_PNG); 
                        return new ResponseEntity<>(photoBytes, headers, HttpStatus.OK);
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                }
            }
        }
    
        return ResponseEntity.notFound().build();
    }
    
    

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public ResponseEntity<?> createUser(@RequestBody User jsonData) {
        try {
            String password = jsonData.getPassword();
            String encryptedPassword = passwordEncoder.encode(password);

            User newUser = new User();
            newUser.setPseudo(jsonData.getPseudo());
            newUser.setLastName(jsonData.getLastName());
            newUser.setFirstName(jsonData.getFirstName());
            newUser.setTown(jsonData.getTown());
            newUser.setBirthday(jsonData.getBirthday());
            newUser.setMail(jsonData.getMail());
            newUser.setPassword(encryptedPassword);
            newUser.setActiveAccount(false);
            newUser.setDescription(jsonData.getDescription());
            newUser.setGitProfile(jsonData.getGitProfile());
            newUser.setGender(jsonData.getGender());
            newUser.setType("U");

            newUser = userRepository.save(newUser);

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors de la création de l'utilisateur",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/{userId}/upload-photo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public User downloadPhoto(@PathVariable Integer userId, @RequestPart MultipartFile image) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            try {
                if (!image.isEmpty()) {
                    logger.info("Sauvegarde du fichier image");

                    String storageHash = getStorageHash(image).get();

                    Path rootLocation = this.fileStorageService.getRootLocation();

                    String fileExtension = mimeTypeToExtension(image.getContentType());

                    storageHash = storageHash + fileExtension;

                    Path saveLocation = rootLocation.resolve(storageHash);

                    Files.deleteIfExists(saveLocation);

                    Files.copy(image.getInputStream(), saveLocation);

                    user.setPhoto(storageHash);

                    return userRepository.save(user);
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Impossible de sauvegarder l'image.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossible de sauvegarder la ressource.");
    }

    /**
     * Permet de retrouver un hash qui pourra être utilisé comme nom de fichier
     * uniquement pour le stockage.
     *
     * Le hash sera calculé à partir du nom du fichier, de son type MIME
     * (https://developer.mozilla.org/fr/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types)
     * et de la date d'upload.
     *
     * @return Le hash encodé en base64
     */
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

}
