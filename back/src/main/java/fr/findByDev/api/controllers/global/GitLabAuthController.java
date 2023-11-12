package fr.findByDev.api.controllers.global;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import fr.findByDev.api.services.utils.OAuthUtil;
import jakarta.servlet.http.HttpSession;

@Controller
public class GitLabAuthController {
    private final String clientId = "03c6edda281830e0b2b456bb31487af95738fb4672a695adb59a2f1f71f6b013";
    private final String clientSecret = "gloas-6208a617921de4ba9278a05d577882f4f48533229674d2405bf9d780fe78bf0a";
    private String accessToken;
    private Object idGit;

    @GetMapping("/login/oauth2/authorization/gitlab")
    public String redirectToGitLabAuth(HttpSession session) {
        String state = OAuthUtil.generateRandomState(session);
        String codeVerifier = OAuthUtil.generateCodeVerifier();
        String codeChallenge = OAuthUtil.generateCodeChallenge(codeVerifier);

        // Stocker le codeVerifier dans la session pour une utilisation ultérieure (si
        // nécessaire)
        session.setAttribute("gitlab_state", state);
        session.setAttribute("gitlab_code_verifier", codeVerifier);
        System.out.println("État stocké en session : " + state);
        // Inclure le codeChallenge dans la requête d'autorisation
        return "redirect:https://gitlab.com/oauth/authorize?"
                + "client_id=" + clientId
                + "&redirect_uri=http://localhost:8000/api/login/oauth2/code/gitlab"
                + "&response_type=code"
                + "&scope=read_api+read_user"
                + "&state=" + state
                + "&code_challenge=" + codeChallenge
                + "&code_challenge_method=S256";
    }

    @GetMapping("/login/oauth2/code/gitlab")
    @CrossOrigin
    public String handleGitLabAuthResponse(@RequestParam("code") String code, @RequestParam("state") String state,
            HttpSession session) {
        String storedState = (String) session.getAttribute("gitlab_state");

        if (storedState != null && state != null && storedState.equals(state)) {
            try {
                accessToken = exchangeCodeForAccessToken(code, session);
                System.out.println("------------------------------ID de l'utilisateur GitLab : " + accessToken);
                return "redirect:http://localhost:3000/register/gitdetails";
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/error";
            }
        } else {
            return "redirect:/error";
        }
    }

    private String exchangeCodeForAccessToken(String code, HttpSession session) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("code", code);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", "http://localhost:8000/api/login/oauth2/code/gitlab");
        requestBody.add("code_verifier", (String) session.getAttribute("gitlab_code_verifier"));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map<String, Object>> response = new RestTemplate()
                .exchange("https://gitlab.com/oauth/token", HttpMethod.POST, request,
                        new ParameterizedTypeReference<Map<String, Object>>() {
                        });

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                return responseBody.get("access_token").toString();
            }
        }

        return "";
    }

    @GetMapping("/getaccesstoken")
    @CrossOrigin
    public ResponseEntity<String> getAccessToken() {
        if (accessToken != null && !accessToken.isEmpty()) {
            return new ResponseEntity<>(accessToken, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Access token is missing or invalid.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getgituserinfo")
    @CrossOrigin
    public ResponseEntity<Map<String, Object>> getGitLabUserInfo(@RequestParam("access_token") String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            // Vous avez maintenant `accessToken` en tant que paramètre

            HttpHeaders headers = new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map<String, Object>> response = new RestTemplate().exchange(
                    "https://gitlab.com/api/v4/user?access_token=" + accessToken,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> userInfo = response.getBody();
                // Extraire l'ID de l'utilisateur GitLab
                int userId = (int) userInfo.get("id");
                String username = userInfo.get("username").toString();

                // Créez une carte pour stocker ces informations
                Map<String, Object> userInformation = new HashMap<>();
                userInformation.put("id", userId);
                userInformation.put("username", username);

                return ResponseEntity.ok(userInformation);
            } else {
                // Gérez les erreurs ici
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getgitprojects")
    @CrossOrigin
    public ResponseEntity<String> getGitLabProjects(@RequestParam("id_git") String idGit) {
        if (idGit != null && !idGit.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
    
            String projectsUrl = "https://gitlab.com/api/v4/users/" + idGit + "/projects";
    
            HttpEntity<String> entity = new HttpEntity<>(headers);
    
            ResponseEntity<String> response = new RestTemplate().exchange(
                projectsUrl,
                HttpMethod.GET,
                entity,
                String.class  
            );
    
            if (response.getStatusCode().is2xxSuccessful()) {
                String gitLabProjects = response.getBody();
                // Vous avez maintenant la réponse sous forme de chaîne, qui peut contenir la liste des projets JSON
    
                return new ResponseEntity<>(gitLabProjects, HttpStatus.OK);
            } else {
                // Gérez les erreurs ici
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Gérez le cas où l'ID GitLab n'est pas défini
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getlastpushoflastproject")
    public ResponseEntity<String> getLastPushOfLastProject(@RequestParam("id_git") String projectId) {
        String gitLabApiUrl = "https://gitlab.com/api/v4/projects/" + projectId + "/repository/commits";
        
        HttpHeaders headers = new HttpHeaders();
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        RestTemplate restTemplate = new RestTemplate();
        
        ResponseEntity<String> response = restTemplate.getForEntity(gitLabApiUrl, String.class, entity);
        
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        } else {
            // Gérez les erreurs ici
            return ResponseEntity.status(response.getStatusCode()).body("Erreur lors de la récupération du dernier push.");
        }
    }

}
