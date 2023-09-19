package fr.findByDev.api.services;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.findByDev.api.model.User;
import jakarta.transaction.Transactional;

/**
 * Classe encapsulant le code permettant de gérer les utilisateurs.
 * Doit nécessairement implémenter "UserDetailsService" qui sera utilisée par
 * "AuthenticationManager"
 */
@Service
public class UserService implements UserDetailsService{
    private static final String USER_NOT_FOUND_MESSAGE = "L'utilisateur avec le nom %s n'existe pas.";
    private static final String USER_FOUND_MESSAGE = "L'utilisateur avec le nom %s existe en base de données.";
    private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByMail(name);
        if (user == null) {
            // pas d'utilisateur, on renvoie une exception
            String message = String.format(USER_NOT_FOUND_MESSAGE, name);
            logger.error(message);
            throw new UsernameNotFoundException(message);
        } else {
            // utilisateur retrouvé, on instancie une liste d' "authorities" qui
            // correspondent à des roles
            logger.debug(USER_FOUND_MESSAGE, name);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            // user.getRoles().forEach(role -> {
            // authorities.add(new SimpleGrantedAuthority(role.getName()));
            // });

            if (user.getType().equals("T")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_TRAINER"));
            } else if (user.getType().equals("A")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else if (user.getType().equals("S")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ASSISTANT"));
            }else if (user.getType().equals("SA")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
            }
             
            return new CustomUserDetails(user.getId(), user.getMail(), user.getPassword(), user.getLastName(), user.getFirstName(), user.getFirstConnection(), user.getTutorialStep(),
                    authorities);
        }
    }
    
}
