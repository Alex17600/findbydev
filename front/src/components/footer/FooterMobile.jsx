import React, { useEffect, useState } from "react";
import { useNavigate, useLocation, useParams  } from "react-router-dom";
import style from "./FooterMobile.module.scss";
import { FiSearch } from "react-icons/fi";
import { GrNotification } from "react-icons/gr";
import { AiOutlineMessage } from "react-icons/ai";
import { VscAccount } from "react-icons/vsc";
import { MdOutlineNotificationsActive } from "react-icons/md";
import { getToken } from "../../data/Token";
import jwtDecode from "jwt-decode";
import { unreadMatches } from "../../apis/users";

const FooterMobile = () => {
  const [userConnected, setUserConnected] = useState();
  const [newMatchFound, setNewMatchFound] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();
  const { userId } = useParams(); 


  useEffect(() => {
    try {
      const token = getToken();
      const decodedToken = jwtDecode(token);
      setUserConnected(decodedToken);
    } catch (error) {}
  }, []);

  //recuperation des matchs de l'user
  useEffect(() => {
    async function fetchUnreadMatches() {
      try {
        if (userConnected && userConnected.idUser) {
          const matches = await unreadMatches(userConnected.idUser);

          if (matches.length > 0) {
            setNewMatchFound(matches.length > 0 ? true : false);
          } else {
            setNewMatchFound(false);
          }
        }
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des matchs non lus :",
          error
        );
      }
    }
    fetchUnreadMatches();
  }, [userConnected]);
  

  const handleNotificationsClick = () => {
    const userId = userConnected.idUser;
    navigate(`/profil/${userId}/notice`);
  };

  const handleAccountClick = () => {
    const userId = userConnected.idUser;
    navigate(`/profil/${userId}/account`);
  };

  const handleMessageClick = () => {
    const userId = userConnected.idUser;
    navigate(`/tchat/${userId}/conversation`);
  }


  return (
    <div className={style.bottomIcon}>
      <FiSearch onClick={() => navigate("/search")}/>
      {newMatchFound ? (
        <MdOutlineNotificationsActive
        className={location.pathname.endsWith(`/profil/${userId}/notice`) ? style.disabled : style.newmatch}
          onClick={handleNotificationsClick}
        />
      ) : (
        <GrNotification 
        className={location.pathname.endsWith(`/profil/${userId}/notice`) ? style.disabled : null}
        onClick={handleNotificationsClick} />
      )}
      <AiOutlineMessage onClick={handleMessageClick}/>
      <VscAccount 
      className={location.pathname.endsWith(`/profil/${userId}/account`) ? style.disabled : null}
      onClick={handleAccountClick}/>
    </div>
  );
};

export default FooterMobile;
