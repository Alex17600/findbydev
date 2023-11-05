import {
    getToken
} from "../data/Token";
const API_URL = "http://localhost:8000/api/users";


export const searchUsers = async (criteria) => {
    const url = new URL(`${API_URL}/search`);

    for (const key in criteria) {
        if (criteria[key]) {
            url.searchParams.append(key, criteria[key]);
        }
    }

    try {
        const response = await fetch(url, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${getToken()}` 
            },
        });
        if (!response.ok) {
            throw new Error(`Erreur de recherche - ${response.status}`);
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Erreur de recherche :", error);
        throw error;
    }
};