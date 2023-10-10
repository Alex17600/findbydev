import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import style from "./Notice.module.scss";
import jwtDecode from "jwt-decode";
import { getToken } from "../../../data/Token";
import { unreadMatches } from "../../../apis/users";
import { findPhotoById } from "../../../apis/photos";
import { RxCross2 } from "react-icons/rx";
import { AiTwotoneHeart } from "react-icons/ai";
import { FiSearch } from "react-icons/fi";
import { GrNotification } from "react-icons/gr";
import { AiOutlineMessage } from "react-icons/ai";
import { VscAccount } from "react-icons/vsc";
import { updateMatch } from "../../../apis/match";

const Notice = () => {
  const { userId } = useParams();
  const [userConnected, setUserConnected] = useState();
  const [matches, setMatches] = useState([]);
  const [userPhotos, setUserPhotos] = useState({});
  const navigate = useNavigate();

  const handleAccountClick = () => {
    const userId = userConnected.idUser;
    navigate(`../${userId}/account`);
  };

  useEffect(() => {
    const token = getToken();
    const decodedToken = jwtDecode(token);

    if (decodedToken.idUser.toString() === userId) {
      setUserConnected(decodedToken);
    } else {
      navigate("/accueil");
    }
  }, [userId, navigate]);

  // Récupération des matchs de l'utilisateur
  useEffect(() => {
    async function fetchUnreadMatches() {
      try {
        if (userConnected && userConnected.idUser) {
          const matchesData = await unreadMatches(userConnected.idUser);
          setMatches(matchesData);
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

  useEffect(() => {
    async function fetchUserPhotos() {
      try {
        const photos = {};
        for (const match of matches) {
          const photo = await findPhotoById(match.sender.id);
          photos[match.sender.id] = photo;
        }
        setUserPhotos(photos);
      } catch (error) {
        console.error("Erreur lors de la récupération des photos :", error);
      }
    }
    fetchUserPhotos();
  }, [matches]);

//mettre à jour le match
const updateMatchStatus = async (match, newStatus) => {
  try {
    const data = {
      receiver: match.receiver.id,
      sender: match.sender.id,
      newStatus: newStatus
    };

    console.log(data);

    const updatedMatch = await updateMatch(data);
    

    setMatches((prevMatches) =>
      prevMatches.map((prevMatch) =>
        prevMatch.idMatch === updatedMatch.idMatch ? updatedMatch : prevMatch
      )
    );
  } catch (error) {
    console.error("Erreur lors de la mise à jour du statut du match :", error);
  }
};


const handleAcceptMatch = (match) => {
  updateMatchStatus(match, "VALIDE");
};

const handleRejectMatch = (match) => {
  updateMatchStatus(match, "REFUSE");
};


  const formatMatchDate = (dateString) => {
    const date = new Date(dateString);
    const formattedDate = `${date.toLocaleDateString()} ${date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}`;
    return formattedDate;
  };

  return (
    <div className={style.notice}>
      {matches.map((match, index) => (
        <div key={index} className={style.match}>
          <img src={userPhotos[match.sender.id]} alt={match.sender.pseudo} />
          <div className={style.infoMatch}>
            <p>{match.sender.pseudo}</p>
            <p>Match reçu le: {formatMatchDate(match.dateHour)}</p>
            <div className={style.buttons}>
              <RxCross2 onClick={() => handleRejectMatch(match)}/>
              <AiTwotoneHeart color="red" onClick={() => handleAcceptMatch(match)}/>
            </div>
          </div>
        </div>
      ))}
      <div className={style.bottomIcon}>
        <FiSearch />
        <GrNotification />
        <AiOutlineMessage />
        <VscAccount onClick={handleAccountClick}/>
      </div>
    </div>
  );
};

export default Notice;
