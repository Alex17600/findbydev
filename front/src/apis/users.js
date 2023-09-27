import { getToken } from "../data/Token";

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


// findById
export const findById = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8000/users/${userId}`, {
        method: 'GET',
        headers: {
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

// Fonction downloadPhoto
export async function downloadPhoto(userId, image) {
  const formData = new FormData();
  formData.append("image", image);

  const response = await fetch(`${URL_API}/${userId}/upload-photo`, {
    method: 'PATCH',
    body: formData,
  });

  if (response.ok) {
    const body = await response.json();
    return Array.isArray(body) ? body : [body];
  } else {
    throw new Error('Erreur lors de la requête pour télécharger la photo');
  }
}
