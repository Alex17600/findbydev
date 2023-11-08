package fr.findByDev.api.controllers.global;

import java.util.List;
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

import fr.findByDev.api.models.GitProject;
import fr.findByDev.api.services.utils.OAuthUtil;
import jakarta.servlet.http.HttpSession;

@Controller
public class GitLabAuthController {
    private final String clientId = "03c6edda281830e0b2b456bb31487af95738fb4672a695adb59a2f1f71f6b013";
    private final String clientSecret = "gloas-6208a617921de4ba9278a05d577882f4f48533229674d2405bf9d780fe78bf0a";
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
                String gitLabAccessToken = exchangeCodeForAccessToken(code, session);
                System.out.println("------------------------------ID de l'utilisateur GitLab : " + gitLabAccessToken);
                return "redirect:http://localhost:3000/profil/card";
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

    @GetMapping("/getgituserinfo")
    @CrossOrigin
    public ResponseEntity<Map<String, Object>> getGitLabUserInfo(@RequestParam("access_token") String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map<String, Object>> response = new RestTemplate().exchange(
                "https://gitlab.com/api/v4/user",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> userInfo = response.getBody();
            // Extraire l'ID de l'utilisateur GitLab
            idGit = userInfo.get("id");

            // Vous pouvez également extraire d'autres informations de l'utilisateur ici si
            // nécessaire

            // Ensuite, vous pouvez appeler la méthode pour récupérer les projets de
            // l'utilisateur
            // en utilisant l'ID de l'utilisateur

            // Construisez l'URL pour récupérer les projets de l'utilisateur en utilisant
            // l'ID
            String projectsUrl = "https://gitlab.com/api/v4/users/" + idGit + "/projects";

            // Appelez la méthode pour récupérer les projets en utilisant l'URL projectsUrl
            ResponseEntity<List<GitProject>> projectsResponse = getGitLabProjects(accessToken, projectsUrl);

            // Gérez la réponse des projets ici

            return response;
        } else {
            // Gérez les erreurs ici
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getgitprojects")
    private ResponseEntity<List<GitProject>> getGitLabProjects(String accessToken, String projectsUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        ResponseEntity<List<GitProject>> response = new RestTemplate().exchange(
            projectsUrl,
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<GitProject>>() {
            }
        );
        
        if (response.getStatusCode().is2xxSuccessful()) {
            List<GitProject> gitLabProjects = response.getBody();
            // Stockez ces projets dans votre base de données ou faites ce que vous devez en faire
            
            return response;
        } else {
            // Gérez les erreurs ici
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
