import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import style from "./Account.module.scss";
import { findUserById } from "../../../apis/users";
import { clearToken } from "../../../data/Token";
import { BiLogOut } from "react-icons/bi";
import { FiSearch } from "react-icons/fi";
import { GrNotification } from "react-icons/gr";
import { AiOutlineMessage } from "react-icons/ai";
import { VscAccount } from "react-icons/vsc";
import { findPhotoById } from "../../../apis/photos";
import { getToken } from "../../../data/Token";
import jwtDecode from "jwt-decode";

const iconColor = "#ee9c31";
const Account = () => {
  const { userId } = useParams();
  const [user, setUser] = useState([]);
  const [photo, setPhoto] = useState();
  const [isEditing, setIsEditing] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchData() {
      try {
        const token = getToken();
        const decodedToken = jwtDecode(token);
        const tokenId = decodedToken.idUser;

        if (Number(tokenId) === Number(userId)) {
          const data = await findUserById(userId);
          const photoData = await findPhotoById(userId);
          setUser(data);
          setPhoto(photoData);
        } else {
          navigate("/accueil");
        }
      } catch (error) {
        console.error(
          "Erreur lors de la récupération de l'utilisateur :",
          error
        );
      }
    }
    fetchData();
    // eslint-disable-next-line
  }, []);

  const handleLogout = () => {
    clearToken();
    navigate("../accueil");
  };

  const toggleEditing = () => {
    setIsEditing(!isEditing);
  };

  return (
    <div className={style.account}>
      <div className={style.logoutIcon} onClick={handleLogout}>
        <BiLogOut size={32} style={{ color: iconColor }} />
      </div>
      <h1>Bienvenue sur votre compte {user.pseudo}</h1>
      <div className={style.blockPhoto}>
        <img src={photo} alt={user.pseudo} className={style.imgProfil} />
      </div>
      <div className={style.buttonContainer}>
        {!isEditing && (
          <button className={style.editButton} onClick={toggleEditing}>
            Modifier vos informations
          </button>
        )}
      </div>

      {isEditing && (
        <>
          <div className={style.infos}>
            <input
              type="text"
              defaultValue={user.pseudo}
              className={style.inputField}
            />
            <input
              type="text"
              defaultValue={user.lastName}
              className={style.inputField}
            />
            <input
              type="text"
              defaultValue={user.firstName}
              className={style.inputField}
            />
            <input
              type="text"
              defaultValue={user.town}
              className={style.inputField}
            />
            <input
              type="text"
              defaultValue={user.gitProfile}
              className={style.inputField}
            />
            <input
              type="email"
              defaultValue={user.mail}
              className={style.inputField}
            />
            <input
              type="date"
              defaultValue={user.birthday}
              className={style.inputField}
            />
          </div>
          <div className={style.editButtons}>
            <button className={style.editButton}>Modifier</button>
            <button
              className={style.cancelButton}
              onClick={() => {
                setIsEditing(false);
              }}
            >
              Annuler
            </button>
          </div>
        </>
      )}

      <div className={style.bottomIcon}>
        <FiSearch />
        <GrNotification />
        <AiOutlineMessage />
        <VscAccount className={style.disabled} />
      </div>
    </div>
  );
};

export default Account;
