import React, { useRef, useState } from "react";
import style from "./ProfilMobile.module.scss";
import TinderCard from "react-tinder-card";
import Photo from "../../assets/background/téléchargement.jpg";
import { FcLike } from "react-icons/fc";
import { CgDebug } from "react-icons/cg";
import { FcUndo } from "react-icons/fc";
import { VscAccount } from "react-icons/vsc";
import { FiSearch, FiSettings } from "react-icons/fi";
import { AiOutlineMessage } from "react-icons/ai";

const ProfilMobile = () => {
  const tinderCardRef = useRef();
  const [disliked, setDisliked] = useState(false);

  const onSwipe = (direction) => {
    if (direction === "right") {
      console.log("J'aime");
    } else if (direction === "left") {
      console.log("Je n'aime pas");
    }
    console.log("You swiped: " + direction);
  };

  const onCardLeftScreen = (myIdentifier) => {
    console.log(myIdentifier + " left the screen");
  };

  const handleSwipeRight = () => {
    if (tinderCardRef.current) {
      tinderCardRef.current.swipe("right");
    }
  };

  const handleSwipeLeft = () => {
    if (tinderCardRef.current) {
      tinderCardRef.current.swipe("left");
      setDisliked(true);
    }
  };

  //TODO, à terminer Annuler le "dislike"
  const handleUndoDislike = () => {
    setDisliked(false);
  };

  return (
    <div className={style.profilMobile}>
      <p className={style.pseudo}>Pseudo</p>
      <TinderCard
        ref={tinderCardRef}
        onSwipe={onSwipe}
        onCardLeftScreen={() => onCardLeftScreen("fooBar")}
        preventSwipe={["up", "down"]}
      >
        <div className={style.card}>
          <img src={Photo} alt="test" />
          <div className={style.iconOverlay}>
            {disliked && (
              <div className={style.undoDislikeIcon}>
                <FcUndo onClick={handleUndoDislike} />
              </div>
            )}
            <div
              className={style.disLikeIcon}
              onClick={() => handleSwipeLeft()}
            >
              <CgDebug />
            </div>
            <div className={style.likeIcon} onClick={() => handleSwipeRight()}>
              <FcLike />
            </div>
          </div>
        </div>
      </TinderCard>

      <div className={style.bottomIcon}>
        <FiSearch />
        <FiSettings />
        <AiOutlineMessage />
        <VscAccount />
      </div>
    </div>
  );
};

export default ProfilMobile;