import Cookies from "js-cookie";

export const getToken = () => {
  return localStorage.getItem('token') ? localStorage.getItem('token') : sessionStorage.getItem('token');
}

export const saveToken = (token, lasting) => {
  if (lasting) {
    localStorage.setItem('token', token);
  } else {
    sessionStorage.setItem('token', token);
  }
}

export const clearToken = () => {
  localStorage.removeItem('token');
  sessionStorage.removeItem('token');
}


export const getCookieToken = () => {
  return Cookies.get('token');
}

// Méthode pour sauvegarder le token dans un cookie
export const saveCookieToken = (token, lasting) => {
  Cookies.set('token', token, { expires: lasting ? 365 : null });
}

// Méthode pour effacer le cookie du token
export const clearCookieToken = () => {
  Cookies.remove('token');
}
