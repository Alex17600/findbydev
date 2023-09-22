import React, { useEffect, useCallback } from 'react';
import jwtDecode from "jwt-decode";
import { useLocation, useNavigate } from "react-router-dom";
import { clearToken, getToken } from './data/Token';

const FilterAuth = ({ children }) => {
  const token = getToken()
  const navigate = useNavigate();
  const location = useLocation();
  

  const handleNavigation = useCallback(() => {

      if (!token) {
      navigate("/accueil");
    } else {
      const tokenDecode = jwtDecode(token);
      const roles = tokenDecode.roles;
      const dateExpiration = new Date(tokenDecode.exp * 1000)


      if (dateExpiration < new Date()) {
        clearToken();
        navigate("/login");
      }
      if (roles[0] !== 'ROLE_ADMIN' && roles[0] !== 'ROLE_USER') {
        navigate("/login");
      } else if (location.pathname === "/login" || location.pathname === "/") {
        navigate("/accueil");
      }
    }
// eslint-disable-next-line
  }, [navigate, token]);

  useEffect(() => {
    handleNavigation();
  }, [handleNavigation]);

  return (
    <>
      {children}
    </>
  );
};

export default FilterAuth;