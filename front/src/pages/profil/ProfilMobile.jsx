import React, { useState, useMemo, useRef, useEffect } from "react";
import TinderCard from "react-tinder-card";
import { getAllUsers } from "../../apis/users";
import style from "./ProfilMobile.module.scss";
import { FcLike } from "react-icons/fc";
import { CgDebug } from "react-icons/cg";
import { FcUndo } from "react-icons/fc";
import { VscAccount } from "react-icons/vsc";
import { FiSearch } from "react-icons/fi";
import { AiOutlineMessage } from "react-icons/ai";
import { GrNotification } from "react-icons/gr";
import { findPhotoById } from "../../apis/photos";
import { createMatch } from "../../apis/match";
import { getToken } from "../../data/Token";
import jwtDecode from "jwt-decode";

const ProfilMobile = () => {
  const [photo, setPhoto] = useState({});
  const [users, setUsers] = useState([]);
  const [userConnected, setUserConnected] = useState();
  const [currentIndex, setCurrentIndex] = useState(0);
  const [hasNewMatch, setHasNewMatch] = useState(false);
  // eslint-disable-next-line
  const [lastDirection, setLastDirection] = useState();
  const currentIndexRef = useRef(currentIndex);
  // eslint-disable-next-line
  const [disableUndo, setDisableUndo] = useState(true);

  useEffect(() => {
    async function fetchData() {
      try {
        const data = await getAllUsers();
        setUsers(data);
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des utilisateurs :",
          error
        );
      }
    }
    const token = getToken();
    const decodedToken = jwtDecode(token);
    setUserConnected(decodedToken);
    fetchData();
  }, []);

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

  const outOfFrame = (pseudo, idx) => {
    // console.log(`${pseudo} (${idx}) left the screen!`, currentIndexRef.current);
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

      try {
        // Envoyer les ID des utilisateurs au backend pour créer un match
        const createdMatch = await createMatch(matchData);

        console.log("Match créé avec succès : ", createdMatch);
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

  return (
    <div className={style.profilMobile}>
      <div>
        <p className={style.pseudo}>
          {" "}
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
      </div>

      <div className={style.bottomIcon}>
        <FiSearch />
        <GrNotification className={style.notifs}/>
        <AiOutlineMessage />
        <VscAccount />
      </div>
    </div>
  );
};

export default ProfilMobile;
