import {
    getToken
} from "../data/Token";

const URL_API = 'http://localhost:8000/api/prefers';

//findByAll
export async function getAllPrefers() {
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

export async function createPrefer(userId, technologyIds) {

    const preferData = {
        idUser: userId,
        idTechnologys: technologyIds
    };

    const response = await fetch(`${URL_API}/create`, {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${getToken()}`,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(preferData)
    });

    if (response.ok) {
        const createdMatch = await response.json();

        return createdMatch;
    } else {

        throw new Error('Erreur lors de la création préférence user/techno');
    }
}