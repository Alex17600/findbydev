package fr.findByDev.api.security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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

         http.csrf(AbstractHttpConfigurer::disable) // désactivation de la vérification par défaut des attaques CSRF (pas grave vu qu'on va mettre en place un système de jetons)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/localhost:3000/**").permitAll()//a changer et definir precisement les bonnes requetes
                        .requestMatchers(HttpMethod.POST, "/localhost:3000/**").permitAll()//a changer et definir precisement les bonnes requetes
                        .requestMatchers(HttpMethod.GET, "/login/oauth2/**").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/login/oauth2/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/getgituserinfo/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/getgitprojects/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/getaccesstoken/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/getgitlabusername/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/getlastpushoflastproject/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.PATCH, "/users/gitconfirm/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "https://gitlab.com/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"https://gitlab.com/oauth/token").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/chekMailExist").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.POST, "/users/**").permitAll() //user pas encore crééer donc permitall
                        .requestMatchers(HttpMethod.PATCH, "/users/**").hasAuthority("ROLE_USER")
                        .requestMatchers(HttpMethod.PATCH, "/notices/**").hasAuthority("ROLE_USER")   
                        .requestMatchers(HttpMethod.PATCH, "/users/{userid}/update-photo").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") 
                        .requestMatchers(HttpMethod.GET, "/genders/**").permitAll()               
                        .requestMatchers(HttpMethod.GET, "/matches/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")              
                        .requestMatchers(HttpMethod.POST, "/messages/**").hasAuthority("ROLE_USER")                               
                        .requestMatchers(HttpMethod.POST, "/chat/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/chat/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/chat/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/chat/**").permitAll() 
                        .requestMatchers(HttpMethod.POST, "/matches/**").hasAuthority("ROLE_USER")               
                        .requestMatchers(HttpMethod.GET, "/account/**").hasAuthority("ROLE_USER")               
                        .requestMatchers(HttpMethod.POST, "/prefers/**").hasAuthority("ROLE_USER")               
                        .requestMatchers(HttpMethod.GET, "/technologies/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/conversations/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/messages/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/messages/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/realises/**").permitAll()               
                        .requestMatchers(HttpMethod.GET, "/experiences/**").permitAll()               
                        .requestMatchers(HttpMethod.GET, "/likes/**").permitAll()               
                        .requestMatchers(HttpMethod.GET, "/concernes/**").permitAll()               
                        .requestMatchers(HttpMethod.GET, "/prefers/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")             
                        .requestMatchers(HttpMethod.GET, "/alerts/**").hasAuthority("ROLE_USER")             
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
