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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import fr.findByDev.api.controllers.GenericController;
import fr.findByDev.api.models.Match;
import fr.findByDev.api.models.User;
import fr.findByDev.api.models.enums.Status;
import fr.findByDev.api.repositories.global.MatchRepository;
import fr.findByDev.api.repositories.global.UserRepository;
import fr.findByDev.api.security.JwtUtil;
import fr.findByDev.api.services.emails.ActivationEmail;
import fr.findByDev.api.services.user.FileStorageService;
import fr.findByDev.api.services.user.UserService;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<User, Integer> {

    Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActivationEmail passwordEmailService;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    public UserController(UserRepository userRepository, MatchRepository matchRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.matchRepository = matchRepository;
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

    // Recuperation de la photo d'un user
    @GetMapping("/{idUser}/photo")
    @CrossOrigin
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

            String randomPassword = generatePasswordWithCriteria();

            User newUser = new User();
            newUser.setPseudo(jsonData.getPseudo());
            newUser.setLastName(jsonData.getLastName());
            newUser.setFirstName(jsonData.getFirstName());
            newUser.setTown(jsonData.getTown());
            newUser.setBirthday(jsonData.getBirthday());
            newUser.setMail(jsonData.getMail());
            newUser.setPassword(passwordEncoder.encode(randomPassword));
            newUser.setActiveAccount(false);
            newUser.setDescription(jsonData.getDescription());
            newUser.setGitProfile(jsonData.getGitProfile());
            newUser.setGender(jsonData.getGender());
            newUser.setType("U");

            newUser = userRepository.save(newUser);

            // Envoyer l'e-mail du mot de passe temporaire
            passwordEmailService.sendPasswordEmail(newUser, randomPassword);

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Erreur lors de la création de l'utilisateur",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * maj user
     * 
     * @param userId
     * @return
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (updatedUser.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            if (updatedUser.getDescription() != null) {
                user.setDescription(updatedUser.getDescription());
            }

            if (updatedUser.getPseudo() != null) {
                user.setPseudo(updatedUser.getPseudo());
            }

            if (updatedUser.getLastName() != null) {
                user.setDescription(updatedUser.getLastName());
            }

            if (updatedUser.getFirstName() != null) {
                user.setFirstName(updatedUser.getFirstName());
            }

            if (updatedUser.getTown() != null) {
                user.setTown(updatedUser.getTown());
            }

            if (updatedUser.getGitProfile() != null) {
                user.setGitProfile(updatedUser.getGitProfile());
            }

            if (updatedUser.getMail() != null) {
                user.setMail(updatedUser.getMail());
            }

            if (updatedUser.getBirthday() != null) {
                user.setBirthday(updatedUser.getBirthday());
            }

            User updated = userRepository.save(user);

            return ResponseEntity.ok(updated);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/unread-matches")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public List<Match> getUnreadMatches(@PathVariable Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Récupérez tous les matches non lus et en attente de l'utilisateur
            List<Match> unreadMatches = matchRepository.findByReceiverAndIsReadAndCurrentStatus(user, false,
                    Status.EN_ATTENTE);
            return unreadMatches;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
    }

    /**
     * 
     * @param userId
     * @return
     */
    @GetMapping("/{userId}/read-matches")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public List<Match> getReadMatches(@PathVariable Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Récupérez tous les matches non lus et en attente de l'utilisateur
            List<Match> readMatches = matchRepository.findByReceiverAndCurrentStatus(user, Status.EN_ATTENTE);

            // Marquez ces matches comme lus
            for (Match match : readMatches) {
                match.setIsRead(true);
                matchRepository.save(match);
            }

            return readMatches;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé");
    }

    /**
     * 
     * @param newPassword
     * @param headers
     * @return
     */
    @PatchMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public User resetPassword(@RequestBody String newPassword, @RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization");

        try {
            String mail = JwtUtil.parseTokentoEmail(token);
            User user = userRepository.findByMail(mail);
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            user.setActiveAccount(true);
            return userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Patch de l'user en y ajoutant sa photo
     * (ce n'est donc pas un POST)
     * 
     * @param userId
     * @param image
     * @return
     */
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
     * Méthode pour patch sa photo sur son compte avec une authorisation
     * (On est pas censé acceder à son compte si pas de token, donc authorisation)
     * 
     * @param userId
     * @param image
     * @return
     */
    @PatchMapping(value = "/{userId}/update-photo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public User updatePhoto(@PathVariable Integer userId, @RequestPart MultipartFile image) {

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
     * Methode pour activé le compte via Token généré
     * 
     * @param file
     * @return
     */

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

    public String generatePasswordWithCriteria() {
        int passwordLength = 11;
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?";

        String allChars = upperCaseLetters + lowerCaseLetters + digits + specialChars;
        Random random = new Random();

        StringBuilder password = new StringBuilder();

        // Add at least one uppercase letter
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));

        // Add at least one lowercase letter
        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));

        // Add at least one digit
        password.append(digits.charAt(random.nextInt(digits.length())));

        // Add at least one special character
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        // Fill the rest of the password with random characters
        for (int i = 4; i < passwordLength; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        return password.toString();
    }

}
