import React, { useEffect, useState } from "react";
import style from "./Login.module.scss";
import { useNavigate } from "react-router-dom";
import { TfiClose } from "react-icons/tfi";
import { saveToken } from "../../data/Token";

const Login = () => {
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const navigate = useNavigate();

  useEffect(()=> {
    setMail("");
    setPassword("");
  }, [])

  const handleEmailChange = (event) => {
    setMail(event.target.value);
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
    };
    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  //Connexion
  const handleLogin = async (e) => {
    e.preventDefault();
  
    if (!mail || !password) {
      setError("Veuillez remplir tous les champs.");
      return;
    }
  
    try { 
      const response = await fetch("http://localhost:8000/api/login", {
        method: "POST",
        body: JSON.stringify({ mail, password }),
      });
  
      if (response.ok && response.status === 200) {
        const token = response.headers.get("Access_token");
          saveToken(token, true);
          navigate("/profil");
        
      } else {
        setError("Email ou mot de passe incorrect");
      }
    } catch (error) {
      // console.error(error);
    }
  };

  return (
    <form onSubmit={handleLogin}>
    <div className={style.login}>
      {windowWidth < 928 ? (
        <div className={style.blockLogin}>
          <div className={style.returnIcon}>
            <TfiClose onClick={() => navigate("/accueil")} />
          </div>
          <h1>Connectez-vous</h1>
          {error && <div className={style.errorText}>{error}</div>}
          <input type="email" placeholder="Email" name="email" autoComplete="email" onChange={handleEmailChange}/>
          <input type="password" placeholder="Mot de passe" name="password" autoComplete="current-password" onChange={handlePasswordChange}/>
          <div className={style.bas}>
              <p>
                Pas encore de compte?{" "}
                <span onClick={() => navigate("/register/informations")}>
                  Créez en-un ici
                </span>
              </p>
              <button >Confirmer</button>
            </div>
        </div>
        
      ) : (
        <div className={style.popup}>
          <div className={style.popupContent}>
            <div className={style.returnIcon}>
              <TfiClose onClick={() => navigate("/accueil")} />
            </div>
            <h1>Connectez-vous</h1>
            {error && <div className={style.errorText}>{error}</div>}
            <input type="email" placeholder="Email" name="email" autoComplete="email" onChange={handleEmailChange}/>
            <input type="password" placeholder="Mot de passe" name="password" autoComplete="current-password" onChange={handlePasswordChange}/>
            <div className={style.bas}>
              <p>
                Pas encore de compte?{" "}
                <span onClick={() => navigate("/register/informations")}>
                  Créez en-un ici
                </span>
              </p>
              <button>Confirmer</button>
            </div>
          </div>
        </div>
      )}
    </div>
    </form>
  );
};

export default Login;
