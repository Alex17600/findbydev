import React, { useEffect, useState } from "react";
import style from "./Accueil.module.scss";
import { useNavigate } from "react-router-dom";


const Accueil = () => {
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
    <div className={style.accueilPhone}>
      {windowWidth < 928 ? (
        <>
          <p>Application de rencontre pré-compilée</p>
          <button className={style.block}>Qui sommes-nous?</button>
          <button className={style.block} onClick={()=> navigate("/register")}>Inscrivez-vous</button>
          <button className={style.block} onClick={()=> navigate("/login")}>Connectez-vous</button>
        </>
      ) : (
        <div className={style.blockRight}>
          <div className={style.textLarge}>
            <p>Bienvenue sur findBydev ! </p>
            <p>
              findBydev est bien plus qu'une simple application de rencontre.
            </p>
            <p>
              C'est l'endroit où les esprits logiques se rencontrent, où les
              passions pour le code se transforment en connexions authentiques
              et où les lignes de code se mêlent à de nouvelles amitiés,
              partenariats et peut-être même à l'amour.
            </p>
            <p>Ce que nous proposons : </p>
            <ul>
              <li>
                Rencontrez des développeurs partageant les mêmes intérêts que
                vous.
              </li>
              <li>
                Discutez de projets open-source, de langages de programmation et
                de nouvelles technologies.
              </li>
              <li>
                Partagez vos connaissances, vos expériences et vos passions avec
                une communauté axée sur le développement.
              </li>
              <li>
                Trouvez des correspondances basées sur des critères de
                développement spécifiques.
              </li>
              <li>
                Découvrez des profils Git et suivez les derniers commits de vos
                correspondances.
              </li>
            </ul>
            <p>
              Rejoignez la communauté findBydev dès aujourd'hui et découvrez une
              nouvelle dimension de la rencontre en ligne. Inscrivez-vous
              maintenant pour commencer à coder votre futur !
            </p>
          </div>
        </div>
      )}
    </div>
  );
};

export default Accueil;
