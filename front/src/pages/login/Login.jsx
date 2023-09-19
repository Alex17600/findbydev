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
    <div className={style.login}>
      {windowWidth < 928 ? (
        <div className={style.blockLogin}>
          <div className={style.returnIcon}>
            <RiArrowGoBackFill onClick={() => navigate("/accueil")} />
          </div>
          <h1>Connectez-vous</h1>
          <input type="text" placeholder="Email" />
          <input type="text" placeholder="Mot de passe" />
        </div>
      ) : (
        <div className={style.popup}>
          <div className={style.popupContent}>
            <div className={style.returnIcon}>
              <RiArrowGoBackFill onClick={() => navigate("/accueil")} />
            </div>
            <h1>Connectez-vous</h1>
            <input type="text" placeholder="Email" />
            <input type="text" placeholder="Mot de passe" />
            <div className={style.bas}>
              <p>
                Pas encore de compte?{" "}
                <span onClick={() => navigate("/register")}>
                  Créez en-un ici
                </span>
              </p>
              <button>Confirmer</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Login;
