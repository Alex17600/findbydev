import { getToken } from "../data/Token";

const URL_API = 'http://localhost:8000/api/users';

//findByAll
export async function getAllPhotos() {
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


// findPhotoById
export const findPhotoById = async (userId) => {
    try {
      const response = await fetch(`${URL_API}/${userId}/photo`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${getToken()}`,
          'Content-Type': 'application/json',
        },
      });
      if (!response.ok) {
        throw new Error('Erreur lors de la récupération de la photo');
      }
  
      const imageUrl = URL.createObjectURL(await response.blob());
  
      return imageUrl;
    } catch (error) {
      throw new Error('Erreur lors de la récupération de la photo : ' + error.message);
    }
};
  