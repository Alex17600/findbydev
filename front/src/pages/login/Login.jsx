import React, { useEffect, useState } from "react";
import style from "./Login.module.scss";
import { useNavigate } from "react-router-dom";
import { RiArrowGoBackFill } from "react-icons/ri";

const Login = () => {
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const navigate = useNavigate();

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
    };
    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);
  return (
    <div className={style.loginPhone}>
      {windowWidth < 928 ? (
        <div className={style.blockLogin}>
          <div className={style.returnIcon}>
            <RiArrowGoBackFill onClick={() => navigate("/accueil")} />
          </div>
          <h1>Connectez-vous</h1>
        </div>
      ) : (
        <p></p>
      )}
    </div>
  );
};

export default Login;
