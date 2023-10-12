import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import style from "./Notice.module.scss";
import jwtDecode from "jwt-decode";
import { getToken } from "../../../data/Token";
import { readMatches } from "../../../apis/users";
import { findPhotoById } from "../../../apis/photos";
import { RxCross2 } from "react-icons/rx";
import { AiTwotoneHeart } from "react-icons/ai";
import { updateMatch } from "../../../apis/match";
import FooterMobile from "../../../components/footer/FooterMobile";

const Notice = () => {
  const { userId } = useParams();
  const [userConnected, setUserConnected] = useState();
  const [matches, setMatches] = useState([]);
  const [userPhotos, setUserPhotos] = useState({});
  const [like, setLike] = useState(new Array(matches.length).fill(false));
  const [dislike, setDislike] = useState(new Array(matches.length).fill(false));

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
    async function fetchReadMatches() {
      try {
        if (userConnected && userConnected.idUser) {
          const matchesData = await readMatches(userConnected.idUser);
          setMatches(matchesData);
          setLike(new Array(matchesData.length).fill(false));
          setDislike(new Array(matchesData.length).fill(false));
        }
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des matchs non lus :",
          error
        );
      }
    }
    fetchReadMatches();
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

  const updateMatchStatus = async (match, newStatus, index) => {
    try {
      const data = {
        receiver: match.receiver.id,
        sender: match.sender.id,
        newStatus: newStatus,
      };

      await updateMatch(data);

      // Mettre à jour la liste des matchs visuellement
      if (newStatus === "VALIDE") {
        setLike((prevLikes) => {
          const newLikes = [...prevLikes];
          newLikes[index] = true;
          return newLikes;
        });
      } else {
        setDislike((prevDislikes) => {
          const newDislikes = [...prevDislikes];
          newDislikes[index] = true;
          return newDislikes;
        });
      }
    } catch (error) {
      console.error(
        "Erreur lors de la mise à jour du statut du match :",
        error
      );
    }
  };

  const handleAcceptMatch = (match, index) => {
    setLike((prevLikes) => {
      const newLikes = [...prevLikes];
      newLikes[index] = true;
      return newLikes;
    });

    updateMatchStatus(match, "VALIDE", index);
  };

  const handleRejectMatch = (match, index) => {
    setDislike((prevDislikes) => {
      const newDislikes = [...prevDislikes];
      newDislikes[index] = true;
      return newDislikes;
    });

    updateMatchStatus(match, "REFUSE", index);
  };

  const formatMatchDate = (dateString) => {
    const date = new Date(dateString);
    const formattedDate = `${date.toLocaleDateString()} ${date.toLocaleTimeString(
      [],
      { hour: "2-digit", minute: "2-digit" }
    )}`;
    return formattedDate;
  };

  return (
    <div className={style.notice}>
      {matches.map((match, index) => (
        <div
          key={index}
          className={`${style.match} ${
            like[index]
              ? `${style.like}`
              : dislike[index]
              ? `${style.dislike}`
              : ""
          }`}
        >
          <img src={userPhotos[match.sender.id]} alt={match.sender.pseudo} />
          <div className={style.infoMatch}>
            <p>{match.sender.pseudo}</p>
            <p>Match reçu le: {formatMatchDate(match.dateHour)}</p>
            <div className={style.buttons}>
              <RxCross2 onClick={() => handleRejectMatch(match, index)} />
              <AiTwotoneHeart
                color="red"
                onClick={() => handleAcceptMatch(match, index)}
              />
            </div>
          </div>
        </div>
      ))}
      <FooterMobile/>
    </div>
  );
};

export default Notice;
