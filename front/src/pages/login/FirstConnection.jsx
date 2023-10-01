import React, { useEffect, useState } from "react";
import style from "./FirstConnection.module.scss";
import { useNavigate } from "react-router-dom";
import { TfiClose } from "react-icons/tfi";
import { updatePassword } from "../../apis/users";


const FirstConnexion = ({ passwordTemporaly, setActiveAccount }) => {
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const [newPassword, setNewPassword] = useState("");
  const [repeatNewPassword, setRepeatNewPassword] = useState("");
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

  const handleNewPasswordChange = (event) => {
    setNewPassword(event.target.value);
  };

  const handleRepeatNewPasswordChange = (event) => {
    setRepeatNewPassword(event.target.value);
  };

  const validatePasswordRequirements = () => {
    let missingRequirements = [];

    if (!/(?=.*[a-z])/.test(newPassword)) {
      missingRequirements.push("une minuscule");
    }
    if (!/(?=.*[A-Z])/.test(newPassword)) {
      missingRequirements.push("une majuscule");
    }
    if (!/(?=.*\d)/.test(newPassword)) {
      missingRequirements.push("un chiffre");
    }
    if (newPassword.length < 8) {
      missingRequirements.push("au moins 8 caractères");
    }

    if (newPassword === passwordTemporaly) {
      missingRequirements.push("un mot de passe different du précédent");
    }

    if (missingRequirements.length > 0) {
      return `Le mot de passe doit contenir ${missingRequirements.join(", ")}.`;
    }

    return null;
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    setError("");

    const passwordError = validatePasswordRequirements();
    if (passwordError) {
      setError(passwordError);
      return;
    }

    try {
      if (newPassword !== repeatNewPassword) {
        setError("Les nouveaux mots de passe ne correspondent pas.");
        return;
      } else {
        const body = await updatePassword(newPassword);
        if (body) {
          setSuccess(true);
          event.target.children[0].disabled = true;
          setTimeout(() => {
            event.target.children[0].disabled = false;
            setActiveAccount(true);
            navigate("/login")
          }, 3000);
        }
      }
    } catch (error) {
      setError("Erreur de mise à jour du mot de passe");
    }
  };

  return (
    <form className={style.firstConnection} onSubmit={handleSubmit}>
        {windowWidth < 928 ? (
          <div className={style.blockLogin}>
            <div className={style.returnIcon}>
              <TfiClose onClick={() => navigate("/accueil")} />
            </div>
            <h1>Connectez-vous</h1>
            {error && <div className={style.errorText}>{error}</div>}
            {!error && !success && (
              <div className={style.info}>
                Minimum 8 caractères avec une majuscule, minuscule et un chiffre
              </div>
            )}
            {success && (
              <div className={style.successText}>
                <p>
                  Modification réussie ! Vous allez être redirigé vers l'écran
                  de connexion
                </p>
              </div>
            )}
            <input
              type="password"
              placeholder="Nouveau mot de passe"
              autoComplete="current-password"
              name="new-password"
              value={newPassword}
              onChange={handleNewPasswordChange}
            />
            <input
              type="password"
              placeholder="Repetez votre mot de passe"
              autoComplete="current-password"
              name="repeat-new-password"
              value={repeatNewPassword}
              onChange={handleRepeatNewPasswordChange}
            />
            <div className={style.bas}>
              <p>
                Pas encore de compte?{" "}
                <span onClick={() => navigate("/register/informations")}>
                  Créez en-un ici
                </span>
              </p>
              <button type="submit">Confirmer</button>
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
              {!error && !success && (
                <div className={style.info}>
                  Minimum 8 caractères avec une majuscule, minuscule et un
                  chiffre
                </div>
              )}
              {success && (
                <div className={style.successText}>
                  <p>
                    Modification réussie ! Vous allez être redirigé vers l'écran
                    de connexion
                  </p>
                </div>
              )}
              <input
                type="password"
                placeholder="Nouveau mot de passe"
                autoComplete="current-password"
                name="new-password"
                value={newPassword}
                onChange={handleNewPasswordChange}
              />
              <input
                type="password"
                placeholder="Repetez votre mot de passe"
                autoComplete="current-password"
                name="repeat-new-password"
                value={repeatNewPassword}
                onChange={handleRepeatNewPasswordChange}
              />
              <div className={style.bas}>
                <button type="submit">Confirmer</button>
              </div>
            </div>
          </div>
        )}
    </form>
  );
};

export default FirstConnexion;
