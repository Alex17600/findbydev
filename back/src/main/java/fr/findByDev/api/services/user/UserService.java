package fr.findByDev.api.services.user;

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

import fr.findByDev.api.models.User;
import fr.findByDev.api.repositories.global.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "L'utilisateur avec le nom %s n'existe pas.";
    private static final String USER_FOUND_MESSAGE = "L'utilisateur avec le nom %s existe en base de données.";

    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByMail(name);
        if (user == null) {

            String message = String.format(USER_NOT_FOUND_MESSAGE, name);
            logger.error(message);
            throw new UsernameNotFoundException(message);
        } else {
            // utilisateur retrouvé, on instancie une liste d' "authorities" qui
            // correspondent à des roles
            logger.debug(USER_FOUND_MESSAGE, name);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            if (user.getType().equals("U")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            } else if (user.getType().equals("A")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }

            return new CustomUserDetails(user.getId(), user.getMail(), user.getPassword(), user.getLastName(),
                    user.getFirstName(), user.isActiveAccount(), authorities);
        }
    }

}
