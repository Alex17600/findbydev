import {
    getToken
} from "../data/Token";

// Fonction pour vérifier si le pseudo GitLab existe
export const checkGitLabUsername = async (username) => {
    try {
      const response = await fetch(
        `https://gitlab.com/api/v4/users?username=${username}`
      );
  
      if (response.ok) {
        const data = await response.json();
        if (Array.isArray(data) && data.length === 0) {
          return false;
        }
        return data[0].id;
      }
  
      return false;
    } catch (error) {
      // En cas d'erreur de requête
      console.error(error);
      return false;
    }
  };