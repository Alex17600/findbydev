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
