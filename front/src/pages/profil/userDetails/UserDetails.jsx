import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import style from "./UserDetails.module.scss";
import { findUserById } from "../../../apis/users";
import { findPhotoById } from "../../../apis/photos";
import FooterMobile from "../../../components/footer/FooterMobile";

const UserDetails = () => {
  const { userIdSelected } = useParams();
  const [userData, setUserData] = useState(null);
  const [photo, setPhoto] = useState({});
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const fetchUserSelected = async () => {
      if (userIdSelected) {
        try {
          const data = await findUserById(userIdSelected);
          const photoUrl = await findPhotoById(userIdSelected);
          setPhoto(photoUrl);
          setUserData(data);
        } catch (error) {
          console.error(
            "Erreur lors de la récupération des données de l'utilisateur :",
            error
          );
        }
      }
    };

    fetchUserSelected();
  }, [userIdSelected]);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <>
      <div className={style.userProfile}>
        <div className={style.cardProfile}>
          <div className={style.photo}>
            <img src={photo} alt={userData?.pseudo} onClick={openModal}/>
          </div>
          <div className={style.infos}>
            <h1>{userData?.pseudo}</h1>
            <div className={style.town}>
              <p>Ville() =&gt;</p>
              <p>{userData?.town}</p>
            </div>
            <div className={style.description}>
              <p>Description() =&gt; </p>
              <p>{userData?.description}</p>
            </div>
            {/* <div className={userData?.}>

            </div> */}
          </div>
        </div>
        {isModalOpen && (
        <div className={style.modalOverlay} onClick={closeModal}>
          <div className={style.modal}>
            <img src={photo} alt={userData?.pseudo} />
            <button onClick={closeModal}>Fermer</button>
          </div>
        </div>
      )}
      </div>
      <FooterMobile />

    </>
  );
};

export default UserDetails;
