import React, { useState, useMemo, useRef, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import TinderCard from "react-tinder-card";
import { getAllUsers, unreadMatches } from "../../../apis/users";
import style from "./ProfilMobile.module.scss";
import { FcLike } from "react-icons/fc";
import { CgDebug } from "react-icons/cg";
import { FcUndo } from "react-icons/fc";
import { VscAccount } from "react-icons/vsc";
import { FiSearch } from "react-icons/fi";
import { AiOutlineMessage } from "react-icons/ai";
import { GrNotification } from "react-icons/gr";
import { MdOutlineNotificationsActive } from "react-icons/md";
import { IoIosReturnLeft } from "react-icons/io"
import { findPhotoById } from "../../../apis/photos";
import { createMatch } from "../../../apis/match";
import { getToken } from "../../../data/Token";
import jwtDecode from "jwt-decode";

const ProfilMobile = () => {
  const navigate = useNavigate();
  const [photo, setPhoto] = useState({});
  const [users, setUsers] = useState([]);
  const [userConnected, setUserConnected] = useState();
  const [currentIndex, setCurrentIndex] = useState(0);
  // eslint-disable-next-line
  const [lastDirection, setLastDirection] = useState();
  const [reachedEndOfList, setReachedEndOfList] = useState(false);
  const currentIndexRef = useRef(currentIndex);
  // eslint-disable-next-line
  const [disableUndo, setDisableUndo] = useState(true);
  const [newMatchFound, setNewMatchFound] = useState(true);


  const handleAccountClick = () => {
    const userId = userConnected.idUser;
    navigate(`/profil/${userId}/account`);
  };

  const handleNotificationsClick = () => {
    const userId = userConnected.idUser;
    navigate(`/profil/${userId}/notices`);
  };
  

  //recuperation des users
  useEffect(() => {
    const token = getToken();
    const decodedToken = jwtDecode(token);
    setUserConnected(decodedToken);

    async function fetchData() {
      try {
        const data = await getAllUsers();
        // Exclure l'utilisateur connecté de la liste
        const filteredUsers = data.filter(
          (user) => user.id !== decodedToken.idUser
        );
        console.log(filteredUsers);
        setUsers(filteredUsers);
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des utilisateurs :",
          error
        );
      }
    }

    fetchData();
  }, []);

  //recuperation des photos associées aux users
  useEffect(() => {
    async function fetchPhotos() {
      const photoData = {};
      for (const user of users) {
        try {
          const photoUrl = await findPhotoById(user.id);
          photoData[user.id] = photoUrl;
        } catch (error) {
          console.error("Erreur lors de la récupération de la photo :", error);
        }
      }
      setPhoto(photoData);
    }
    fetchPhotos();
  }, [users]);

  const childRefs = useMemo(
    () =>
      Array(users.length)
        .fill(0)
        .map((i) => React.createRef()),
    [users.length]
  );

  const updateCurrentIndex = (val) => {
    setCurrentIndex(val);
    currentIndexRef.current = val;
  };

  const canGoBack = currentIndex < users.length - 1;
  const canSwipe = currentIndex >= 0;

  const swiped = (direction, id, index) => {
    setLastDirection(direction);
    updateCurrentIndex(index);
    if (index >= 1) {
      setDisableUndo(false);
    }
  };

  /**
   * Gestionnaire d'événements appelé lorsque la carte Tinder quitte l'écran.
   * @param {string} pseudo - Le nom d'utilisateur de la carte qui quitte l'écran.
   * @param {number} idx - L'index de la carte dans la liste des cartes d'utilisateurs.
   */
  const outOfFrame = (pseudo, idx) => {
    if (currentIndexRef.current > idx) {
      childRefs[currentIndexRef.current].current.restoreCard();
    }
  };

  const swipe = async (dir) => {
    if (canSwipe && currentIndex < users.length) {
      const matchData = {
        userSender: userConnected.idUser,
        userReceiver: users[currentIndex].id,
      };
      //vérif fin de liste
      if (currentIndex === users.length - 1) {
        setReachedEndOfList(true);
      }

      try {
        await createMatch(matchData);
      } catch (error) {
        console.error("Erreur lors de la création du match :", error);
      }
      await childRefs[currentIndex].current.swipe(dir);
      updateCurrentIndex(currentIndex + 1);
    }
  };

  const goBack = async () => {
    if (!canGoBack) return;
    const newIndex = currentIndex - 1;
    updateCurrentIndex(newIndex);
    await childRefs[newIndex].current.restoreCard();
  };

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


  // Fonction pour réinitialiser la liste des cartes Tinder
  const resetCardList = () => {
    // Mettez à jour currentIndex et réinitialisez reachedEndOfList
    setCurrentIndex(0);
    setReachedEndOfList(false);
    // Réinitialisez la liste des cartes Tinder
    childRefs.forEach((ref, index) => {
      if (ref.current) {
        ref.current.restoreCard();
      }
    });
  };

  return (
    <div className={style.profilMobile}>
      <div>
        <p className={style.pseudo}>
          {users[currentIndex] && users[currentIndex].pseudo}
        </p>
        {users.map((user, index) => (
          <TinderCard
            ref={childRefs[index]}
            key={user.id}
            onSwipe={(dir) => swiped(dir, user.id, index)}
            onCardLeftScreen={() => outOfFrame(user.pseudo, index)}
          >
            <div
              className={`${style.card} ${
                currentIndex === index ? "" : style.hidden
              }`}
            >
              <img src={photo[user.id]} alt={user.pseudo} />
              <div className={style.iconOverlay}>
                <div
                  className={`${style.undoDislikeIcon} ${
                    currentIndex === 0 ? style.disabled : ""
                  }`}
                  onClick={() => goBack()}
                >
                  <FcUndo />
                </div>
                <div
                  className={style.disLikeIcon}
                  onClick={() => swipe("left")}
                >
                  <CgDebug />
                </div>
                <div className={style.likeIcon} onClick={() => swipe("right")}>
                  <FcLike />
                </div>
              </div>
            </div>
          </TinderCard>
        ))}
        {/* Composant spécial lorsque la fin de la liste est atteinte */}
        {reachedEndOfList && (
          <div className={style.endOfListCard}>
            <div className={style.endOfListMessage}>
              Il n'y a plus d'autres profils pour le moment.
            </div>
            <div className={style.scrollToStartIcon} onClick={resetCardList}>
              <IoIosReturnLeft />
            </div>
          </div>
        )}
      </div>
      <div className={style.bottomIcon}>
        <FiSearch />
        {newMatchFound ? (
          <MdOutlineNotificationsActive className={style.newmatch} onClick={handleNotificationsClick}/>
        ) : (
          <GrNotification />
        )}
        <AiOutlineMessage />
        <VscAccount onClick={handleAccountClick} />
      </div>

    </div>
  );
};

export default ProfilMobile;
