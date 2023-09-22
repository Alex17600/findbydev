import { getToken } from "../data/Token";

const URL_API = 'http://localhost:8000/api/users';

//findByAll
export async function getAllUsers(page, valueInput) {
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

//create user
export async function createUser(userData) {
    
    const response = await fetch(`${URL_API}/create-user`, {  
        headers: {
            'Authorization': `Bearer ${getToken()}`,
            'Content-Type': 'application/json'
        },
        method: 'POST',
        body: JSON.stringify(userData)
    });
    if (response.ok) {
        console.log(userData);
        const body = await response.json();
        return Array.isArray(body) ? body : [body];

    } else {
        throw new Error('Error fetch create user');
    }
}