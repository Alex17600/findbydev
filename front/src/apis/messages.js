import {
    getToken
} from "../data/Token";

const URL_API = 'http://localhost:8000/api/messages';


export async function getAllMessagesFromIdConversation(idConversation) {
    const response = await fetch(`${URL_API}/${idConversation}/conversation`, {
        headers: {
            'Authorization': `Bearer ${getToken()}`,
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