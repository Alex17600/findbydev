import { getToken } from "../data/Token";

const URL_API = 'http://localhost:8000/api/technologies';

//findByAll
export async function getAllTechnologies() {
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

//findPhotobyId
export async function getIconTechnologie(id) {
    const response = await fetch(`${URL_API}/${id}/icon`, {
        headers: {
            'Authorization': `Bearer ${getToken()}`,
        },
    });
    
    if (response.ok) {

        const blob = await response.blob();
        const url = URL.createObjectURL(blob);
        return url;
        
    } else {
        throw new Error('Error fetch Get users');
    }
}