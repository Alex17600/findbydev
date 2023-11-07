package fr.findByDev.api.services.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import jakarta.servlet.http.HttpSession;

public class OAuthUtil {
        public static String generateRandomState(HttpSession session) {
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        session.setAttribute("oauth_state", state);
        return state;
    }

    // Générer un CODE_VERIFIER
    public static String generateCodeVerifier() {
        byte[] randomBytes = new byte[64]; // Entre 43 et 128 caractères
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    // Générer un CODE_CHALLENGE à partir du CODE_VERIFIER
    public static String generateCodeChallenge(String codeVerifier) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
