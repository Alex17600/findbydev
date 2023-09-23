import { getToken } from "../data/Token";

const URL_API = 'http://localhost:8000/api/genders';

//findByAll
export async function getAllGenders() {
    const response = await fetch(`${URL_API}`, {
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (response.ok) {
        const body = await response.json();
        return Array.isArray(body) ? body : [body];

    } else {
        throw new Error('Error fetch Get Genders');
    }
}