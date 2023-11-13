import {
  getToken
} from "../data/Token";
const token = getToken();

const URL_API = 'http://localhost:8000/api/users';

//findByAll
export async function getAllUsers() {
  const response = await fetch(`${URL_API}`, {
    headers: {
      'Authorization': `Bearer ${getToken()}`,
      'Content-Type': 'application/json'
    },
  });
  if (response.ok) {
    const body = await response.json();
    return Array.isArray(body) ? body : [body];

  } else {
    throw new Error('Error fetch Get users');
  }
}

//findByAll
export async function getAllUsersForCheckMailExist() {
  const response = await fetch(`${URL_API}/chekMailExist`, {
    headers: {
      'Content-Type': 'application/json'
    },
  });
  if (response.ok) {
    const body = await response.json();
    return Array.isArray(body) ? body : [body];

  } else {
    throw new Error('Error fetch Get users');
  }
}


// findById
export const findUserById = async (userId) => {
  try {
    const response = await fetch(`${URL_API}/${userId}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Erreur de la récupération de l\'utilisateur');
    }

    const userData = await response.json();
    return userData;

  } catch (error) {
    throw new Error('Erreur de la récupération de l\'utilisateur : ' + error.message);
  }
};


//create user
export async function createUser(jsonData) {
  const response = await fetch(`${URL_API}/create`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(jsonData),
  });
  if (response.ok) {
    const body = await response.json();
    return Array.isArray(body) ? body : [body];

  } else {
    throw new Error('Error fetch create user');
  }
}

//modification mot de passe temporaire
export async function updatePassword(newPassword) {
  try {
    const response = await fetch(`${URL_API}/reset-password`, {
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
      },
      method: 'PATCH',
      body: newPassword
    });

    if (response.ok) {
      const body = await response.json();
      return body;
    } else {
      throw new Error('Error update password user');
    }
  } catch (error) {
    throw new Error('Error update password user');
  }
}

// Fonction downloadPhoto
export async function downloadPhoto(userId, image) {
  const formData = new FormData();
  formData.append("image", image);

  const response = await fetch(`${URL_API}/${userId}/upload-photo`, {
    method: 'PATCH',
    body: formData,
  });

  if (response.status === 200) {
    return true;
  } else {
    throw new Error('Erreur lors de la requête pour télécharger la photo');
  }
}

// Fonction pour récupérer les matchs non lus et en attente de l'utilisateur
export async function unreadMatches(userId) {
  try {
    const token = getToken();
    const response = await fetch(`${URL_API}/${userId}/unread-matches`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    });

    if (response.ok) {
      const data = await response.json();
      return Array.isArray(data) ? data : [data];
    } else {
      throw new Error('Erreur lors de la récupération des matchs non lus.');
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des matchs non lus :', error);
    throw error;
  }
}

// Fonction pour récupérer les matchs lus ou pas et en attente de l'utilisateur
export async function readMatches(userId) {
  try {
    
    const response = await fetch(`${URL_API}/${userId}/read-matches`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    });

    if (response.ok) {
      const data = await response.json();
      return Array.isArray(data) ? data : [data];
    } else {
      throw new Error('Erreur lors de la récupération des matchs non lus.');
    }
  } catch (error) {
    console.error('Erreur lors de la récupération des matchs non lus :', error);
    throw error;
  }
}

export async function updateUser(userId, updateData) {
  // const formData = new FormData();
  // formData.append("image", image);
  const response = await fetch(`${URL_API}/${userId}`, {
    method: 'PATCH',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updateData),
  });

  if (response.ok) {
    const body = await response.json();
    return Array.isArray(body) ? body : [body];
  } else {
    throw new Error('Erreur lors de la requête pour télécharger la photo');
  }
}

//mise à jour de sa photo de profil sur le compte
export async function updateProfileImageWithAuth(userId, image) {
  const formData = new FormData();
  formData.append("image", image);
  const token = getToken();
  const response = await fetch(`${URL_API}/${userId}/update-photo`, {
    method: 'PATCH',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
    body: formData,
  });

  if (response.ok) {
    const body = await response.json();
    return Array.isArray(body) ? body : [body];
  } else {
    throw new Error('Erreur lors de la requête pour télécharger la photo');
  }
}

//mise à jour des infos git
export async function setGitInfos(gitInfo) {
  try {
    const response = await fetch(`${URL_API}/gitconfirm`, {
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
      },
      method: 'PATCH',
      body: JSON.stringify(gitInfo)
    });

    if (response.ok) {
      const body = await response.json();
      return body;
    } else {
      throw new Error('Error patch git user');
    }
  } catch (error) {
    throw new Error('Error patch git user');
  }
}