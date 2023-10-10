import {
  getToken
} from "../data/Token";

const API_URL = 'http://localhost:8000/api/matches';
const token = getToken();

export async function createMatch(matchData) {
  const response = await fetch(`${API_URL}/create`, {
    method: 'POST',
    headers: {
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(matchData),
  });

  if (response.ok) {
    const createdMatch = await response.json();

    return createdMatch;
  } else {

    throw new Error('Erreur lors de la création du match');
  }
}

export async function updateMatch(data) {
  try {
    const response = await fetch(`${API_URL}/update-status`, {
      headers: {
        'Authorization': `Bearer ${getToken()}`,
        'Content-Type': 'application/json'
      },
      method: 'PATCH',
      body: JSON.stringify(data)
    });

    if (response.ok) {
      const body = await response.json();
      return body;
    } else {
      throw new Error('Erreur lors de la mise à jour du match');
    }
  } catch (error) {
    throw new Error('Erreur lors de la mise à jour du match');
  }
}


