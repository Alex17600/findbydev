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

//findConversationbyId
export async function getConversationsForLoggedInUser(idReceiver, idSender) {
    const response = await fetch(`${URL_API}/${idReceiver}/${idSender}`, {
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