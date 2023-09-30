package fr.findByDev.api.services.emails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

    void sendHtmlMail(EmailDetails details);
}
