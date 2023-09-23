package fr.findByDev.api.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Classe de configuration des mécanismes de sécurité de l'application.
 */
@Configuration
public class SecurityConfig {

    /**
     * L'annotation @Bean est PRIMORDIALE et permet de spécifier que la méthode
     * renvoie une instance de classe qui
     * devra être gérée par "spring context"
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Méthode permettant de configurer la chaîne de filtres de sécurité de spring
     *
     * Pour plus d'informations concernant la chaîne de filtres de Spring veuillez
     * vous référer à la
     * documentation suivante :
     * https://docs.spring.io/spring-security/reference/servlet/architecture.html#servlet-securityfilterchain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager)
            throws Exception {

        // Le code suivant permet de modifier la chaîne de filtres afin de :
        // - autoriser toutes les requêtes sur le endpoint 'login'
        // - autoriser les requêtes sur le endpoint 'users' uniquement si l'utilisateur
        // a le "ROLE_ADMIN" et qu'il est authentifié
        // - ajouter les filtre d'authorisation et d'authentification

        http.cors().configurationSource(corsConfigurationSource()).and()
                .csrf(csrf -> csrf.disable()) // désactivation de la vérification par défaut des attaques CSRF (pas
                                              // grave vu qu'on va mettre en place un système de jetons)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/genders/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/create-user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "//photos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/forgotten-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/check-email").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN", "ROLE_TRAINER")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/**").authenticated() // Changer son mot de passe                    
                        .requestMatchers(HttpMethod.DELETE, "/models/**").hasAnyAuthority("ROLE_TRAINER", "ROLE_SUPER_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/sessions/**").authenticated()
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new CustomAuthenticationFilter(authenticationManager)) // ajout du filtre pour la phase
                                                                                  // d'authentificaiton, utilisé
                                                                                  // uniquement lors de la phase de
                                                                                  // login
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class) 
                // filtre qui va se déclencher à chaque requetes juste avant le "CustomAuthentication"
                                                                                                             
                .headers(headers -> headers.cacheControl(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowCredentials(false);
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Accept"));
        config.setExposedHeaders(Arrays.asList("Access_token", "refresh_token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /**
     * Enregistrement de la classe "BCryptPasswordEncoder" dans le contexte de
     * l'application.
     *
     * L'annotation @Bean permet de le faire et garantit une seule instance en
     * mémoire (à la manière d'un singleton)
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
