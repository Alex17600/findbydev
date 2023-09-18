import style from "./HeaderAccueil.module.scss";
import { useEffect, useState } from 'react';

const HeaderAccueil = () => {

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
        <pre className={style.headerPhone}>findByDev&lt;♥/&gt;</pre>
      ) : (
        <div className={style.headerLarge}>
          <pre>findByDev&lt;♥/&gt;</pre>
          <p>Application de rencontre pré-compilée</p>
          <p>S'inscrire</p>
          <p>Se connecter</p>
        </div>
      )}
    </div>
  );
};

export default HeaderAccueil;
