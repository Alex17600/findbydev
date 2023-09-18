import React, { useEffect, useState } from "react";
import style from "./Register.module.scss";
import { RiArrowGoBackFill } from "react-icons/ri";
import { useNavigate } from "react-router-dom";

const Register = () => {
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
    <div className={style.register}>
      {windowWidth < 928 ? (
        <div className={style.blockRegister}>
          <div className={style.returnIcon}>
            <RiArrowGoBackFill onClick={() => navigate("/accueil")} />
          </div>
          <p>Inscription</p>
          <input type="text" placeholder="Nom" />
          <input type="text" placeholder="Prénom" />
          <input type="text" placeholder="Ville" />
          <input type="text" placeholder="Email" />
          <input type="text" placeholder="Mot de passe" />
          <input type="text" placeholder="Repetez le mot de passe" />
          <input type="date" />
          {/* TODO: À placer le reCaptcha */}
          <label htmlFor="photo">Choisir une photo</label>
          <input type="file" name="photo" />
          <textarea
            rows="10"
            cols="33"
            type="text"
            placeholder="Décrivez-vous en quelques mots..."
          />
          <input type="text" placeholder="Votre lien Git" />

          <div className={style.checkbox}>
            <input type="checkbox" name="conditions" />
            <label htmlFor="condition">
              J’accepte les conditions d’utilisation et la politique du site
            </label>
          </div>
          <div className={style.bas}>
              <button>Envoyer</button>
            </div>
        </div>
      ) : (
        <div className={style.registerLarge}>
          <div className={style.blockRegisterLarge}>
            <h1>Inscription</h1>
            <div className={style.sections}>
              <div className={style.leftSection}>
                <input type="text" placeholder="Nom" />
                <input type="text" placeholder="Prénom" />
                <input type="text" placeholder="Ville" />
                <input type="text" placeholder="Email" />
                <input type="text" placeholder="Mot de passe" />
                <input type="text" placeholder="Repetez le mot de passe" />
              </div>
              <div className={style.rightSection}>
                <input type="date" />
                <label htmlFor="photo">Choisir une photo</label>

                <input type="file" name="photo" />
                <textarea
                  rows="5"
                  cols="33"
                  type="text"
                  placeholder="Décrivez-vous en quelques mots..."
                />
                <input type="text" placeholder="Votre lien Git" />
                <div className={style.checkbox}>
                  <input type="checkbox" name="conditions" />
                  <label htmlFor="condition">
                    J’accepte les conditions d’utilisation et la politique du
                    site
                  </label>
                </div>
              </div>
            </div>
            <div className={style.bas}>
              <button>Envoyer</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Register;
