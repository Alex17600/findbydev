import {
    getToken
} from "../data/Token";

const URL_API = 'http://localhost:8000/api/conversations';

//findByAll
export async function getAllConversations() {
    const response = await fetch(`${URL_API}`, {
        headers: {
            'Content-Type': 'application/json'
        },
    });
    if (response.ok) {
        const body = await response.json();
        return Array.isArray(body) ? body : [body];

    } else {
        throw new Error('Error fetch Get conversations');
    }
}

//findConversationbyIdUse
export async function getConversationById(idConversation) {
    const response = await fetch(`${URL_API}/${idConversation}`, {
        headers: {
            'Authorization': `Bearer ${getToken()}`,
        },
    });
    
    if (response.ok) {
        const body = await response.json();
        return Array.isArray(body) ? body : [body]
    } else {
        throw Error('Erreur lors de la récupération de la conversation');
    }
}

//findConversationbyIdUse
export async function getConversationsForLoggedInUser(userId) {
    const response = await fetch(`${URL_API}/search/${userId}`, {
        headers: {
            'Authorization': `Bearer ${getToken()}`,
        },
    });
    
    if (response.ok) {
        const body = await response.json();
        return Array.isArray(body) ? body : [body]
    } else {
        throw Error('Erreur lors de la récupération des conversations');
    }
}