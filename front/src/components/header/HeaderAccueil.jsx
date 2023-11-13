import { useLocation, useNavigate } from "react-router-dom";
import style from "./HeaderAccueil.module.scss";
import { useEffect, useState } from "react";
import { getToken } from "../../data/Token";
import jwtDecode from "jwt-decode";
import { findPhotoById } from "../../apis/photos";
import { findUserById } from "../../apis/users";

const HeaderAccueil = () => {
  const token = getToken();
  const navigate = useNavigate();
  const location = useLocation();
  const [pseudo, setPseudo] = useState("");
  const [popularity, setPopularity] = useState("");
  const [userPhoto, setUserPhoto] = useState(null);
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);
  const isProfileCardPage = location.pathname === "/profil/card";

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
    };

    const fetchUserPhoto = async () => {
      try {
        if (token && isProfileCardPage) {
          const userConnected = jwtDecode(token);
          const userPhotoUrl = await findPhotoById(userConnected.idUser);
          const data = await findUserById(userConnected.idUser);
          setPseudo(data.pseudo);
          setPopularity(data.popularity);
          setUserPhoto(userPhotoUrl);
        }
      } catch (error) {
        console.error(
          "Erreur lors de la récupération de la photo de l'utilisateur :",
          error
        );
      }
    };

    fetchUserPhoto();

    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  return (
    <div className={style.headerAccueil}>
      {windowWidth < 928 ? (
        <pre
          className={style.headerPhone}
          onClick={() => navigate(token ? "/profil/card" : "/accueil")}
        >
          findByDev&lt;♥/&gt;
        </pre>
      ) : (
        <div className={style.headerLarge}>
          <pre className={style.navbar} onClick={() => navigate("/accueil")}>
            findByDev&lt;♥/&gt;
          </pre>
          {isProfileCardPage ? (
            token && (
              <div className={style.blocUserInfos}>
                <img
                  className={style.profileImage}
                  src={userPhoto}
                  alt="Profile"
                />
                <div className={style.infos}>
                  <p>{pseudo}</p>
                  <p>Popularité: {popularity}%</p>
                </div>
              </div>
            )
          ) : (
            <>
              <p>Application de rencontre pré-compilée</p>
              {!token && (
                <p
                  className={style.navbar}
                  onClick={() => navigate("/register/informations")}
                >
                  S'inscrire
                </p>
              )}
              {!token && (
                <p className={style.navbar} onClick={() => navigate("/login")}>
                  Se connecter
                </p>
              )}
            </>
          )}
        </div>
      )}
    </div>
  );
};

export default HeaderAccueil;
