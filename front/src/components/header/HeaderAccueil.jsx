import { useNavigate } from "react-router-dom";
import style from "./HeaderAccueil.module.scss";
import { useEffect, useState } from 'react';

const HeaderAccueil = () => {
    const navigate = useNavigate();
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);

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
    <div className={style.headerAccueil}>
      {windowWidth < 928 ? (
        <pre className={style.headerPhone} onClick={()=> navigate("/accueil")}>findByDev&lt;♥/&gt;</pre>
      ) : (
        <div className={style.headerLarge}>
          <pre className={style.navbar} onClick={()=> navigate("/accueil")}>findByDev&lt;♥/&gt;</pre>
          <p>Application de rencontre pré-compilée</p>
          <p className={style.navbar} onClick={()=> navigate("/register/informations")}>S'inscrire</p>
          <p className={style.navbar} onClick={()=> navigate("/login")}>Se connecter</p>
        </div>
      )}
    </div>
  );
};

export default HeaderAccueil;
