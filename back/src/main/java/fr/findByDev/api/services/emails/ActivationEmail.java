package fr.findByDev.api.services.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.findByDev.api.models.User;

@Service
public class ActivationEmail {
    @Autowired
    private EmailService emailService;

    public void sendPasswordEmail(User user, String temporaryPassword) {
        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getMail());
        emailDetails.setSubject("Mot de passe temporaire");

        // Personnalisez le contenu de l'e-mail ici
        String emailBody = "Bonjour " + user.getFirstName() + ",\n\n";
        emailBody += "Bienvenue sur notre site. Voici votre mot de passe temporaire :\n";
        emailBody += temporaryPassword + "\n\n";
        emailBody += "Vous devrez le changer dès la première connexion.\n\n";
        emailBody += "Cordialement,\nL'équipe de findByDev";

        emailDetails.setMsgBody(emailBody);

        // Envoyer l'e-mail du mot de passe temporaire
        emailService.sendHtmlMail(emailDetails);
    }
}
