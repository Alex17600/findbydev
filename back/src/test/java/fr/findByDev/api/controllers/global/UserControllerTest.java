package fr.findByDev.api.controllers.global;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.global.UserRepository;

@SpringBootTest
@Transactional
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User userToCreate = new User();
        userToCreate.setPseudo("pseudoTest");
        userToCreate.setLastName("lastname");
        userToCreate.setFirstName("firstname");
        userToCreate.setTown("town");
        userToCreate.setMail("email@email.email");
        userToCreate.setPassword("$2y$10$hdcop0JmljwX0pkef.p5IOClt6qXxN.rOX7Q3Atzg/Ldx90cAH86W");
        userToCreate.setActiveAccount(false);
        userToCreate.setType("U");

        try {
            ResponseEntity<?> responseEntity = userController.createUser(userToCreate);

            assertEquals(ResponseEntity.status(HttpStatus.CREATED).build(), responseEntity);

            // Après l'appel du contrôleur, vous pourriez vouloir vérifier que l'utilisateur a été enregistré
            User savedUser = userRepository.findByPseudo("pseudoTest");
            assertNotNull(savedUser);
            assertEquals("pseudoTest", savedUser.getPseudo());
            // ... autres assertions sur les propriétés de l'utilisateur enregistré
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Le test a échoué avec une exception", e);
        }
    }
}
