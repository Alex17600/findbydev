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

//obtenir la acces_token recu de git et qui contien tout les infos public de l'user
export async function getAccessToken() {
  try {
    const response = await fetch('http://localhost:8000/api/getaccesstoken', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const accessToken = await response.text();
      return accessToken;
    } else {
      throw new Error('Erreur lors de la récupération de l\'access_token');
    }
  } catch (error) {
    console.error('Une erreur s\'est produite :', error);
  }
}


export async function getuserInfosWithAccesToken(accessToken) {
  try {
    const response = await fetch(`http://localhost:8000/api/getgituserinfo?access_token=${accessToken}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      throw new Error('Erreur lors de la récupération des informations de l\'utilisateur');
    }
  } catch (error) {
    console.error('Une erreur s\'est produite :', error);
  }
}

export async function getLastProjectsFromIdGit(idGit) {
  try {
    const response = await fetch(`http://localhost:8000/api/getgitprojects?id_git=${idGit}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      throw new Error('Erreur lors de la récupération des projets de l\'utilisateur');
    }
  } catch (error) {
    console.error('Une erreur s\'est produite :', error);
  }
}

export async function getLastPushFromLastProject(idProject) {
  try {
    const response = await fetch(`http://localhost:8000/api/getlastpushoflastproject?id_git=${idProject}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
      }
    });
    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      throw new Error('Erreur lors de la récupération du dernier push');
    }
  } catch (error) {
    console.error('Une erreur s\'est produite :', error);
  }
}