import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import style from "./Account.module.scss";
import { findUserById, updateUser } from "../../../apis/users";
import { clearToken } from "../../../data/Token";
import { BiLogOut } from "react-icons/bi";
import { FiSearch } from "react-icons/fi";
import { GrNotification } from "react-icons/gr";
import { AiOutlineMessage } from "react-icons/ai";
import { VscAccount } from "react-icons/vsc";
import { findPhotoById } from "../../../apis/photos";

const iconColor = "#ee9c31";

const Account = ({ userConnected }) => {
  const { userId } = useParams();
  const [user, setUser] = useState([]);
  const [photo, setPhoto] = useState();
  const [isEditing, setIsEditing] = useState(false);
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [formValues, setFormValues] = useState({
    description: "",
    pseudo: "",
    lastName: "",
    firstName: "",
    town: "",
    github: "",
    mail: "",
    birthday: "",
  });
  const [updatePassword, setUpdatePassword] = useState(false);
  const [errorText, setErrorText] = useState("");
  const [successText, setSuccessText] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    async function fetchData() {
      try {
        if (Number(userConnected.idUser) === Number(userId)) {
          const data = await findUserById(userConnected.idUser);
          const photoData = await findPhotoById(userId);
          setUser(data);
          setPhoto(photoData);
          setFormValues({
            description: data.description,
            pseudo: data.pseudo,
            lastName: data.lastName,
            firstName: data.firstName,
            town: data.town,
            github: data.gitProfile,
            mail: data.mail,
            birthday: data.birthday,
          });
        } else {
          navigate("/accueil");
        }
      } catch (error) {
        console.error("Erreur lors de la récupération de l'utilisateur :", error);
      }
    }
    fetchData();
    // eslint-disable-next-line
  }, []);

  // Contraintes de mot de passe
  const validatePasswordRequirements = () => {
    let missingRequirements = [];

    if (!/(?=.*[a-z])/.test(newPassword)) {
      missingRequirements.push("une minuscule");
    }
    if (!/(?=.*[A-Z])/.test(newPassword)) {
      missingRequirements.push("une majuscule");
    }
    if (!/(?=.*\d)/.test(newPassword)) {
      missingRequirements.push("un chiffre");
    }
    if (!/[!@#$%^&*]/.test(newPassword)) {
      missingRequirements.push("un caractère spécial (!, @, #, $, %, ^, &, *)");
    }
    if (newPassword.length < 11) {
      missingRequirements.push("au moins 11 caractères");
    }

    if (newPassword === oldPassword) {
      missingRequirements.push("un mot de passe différent du précédent");
    }

    if (missingRequirements.length > 0) {
      return `Le mot de passe doit contenir ${missingRequirements.join(", ")}.`;
    }

    return null;
  };

  const handleLogout = () => {
    clearToken();
    navigate("/accueil");
  };

  const toggleEditing = () => {
    setIsEditing(!isEditing);
  };

  const updatedUserWithtoutPic = async () => {
    try {
      let updateData = {};

      if (newPassword && newPassword !== oldPassword) {
        const passwordRequirementsError = validatePasswordRequirements();
        if (passwordRequirementsError) {
          setErrorText(passwordRequirementsError);
          return;
        }

        updateData.password = newPassword;
        setUpdatePassword(true);
      }

      // Comparez les valeurs actuelles du formulaire avec celles d'origine
      for (const key in formValues) {
        if (formValues[key] !== user[key]) {
          updateData[key] = formValues[key];
        }
      }

      if (Object.keys(updateData).length === 0) {
        // Aucune donnée à mettre à jour
        setSuccessText("Aucune donnée à mettre à jour.");
        return;
      }

      await updateUser(userConnected.idUser, updateData);
      console.log(userId, updateData);
      setSuccessText("Mise à jour réussie!");
    } catch (error) {
      setErrorText("Erreur lors de la mise à jour.");
    }
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
            <div className={style.inputGroup}>
              <label>Description :</label>
              <textarea
                rows={10}
                cols={30}
                value={formValues.description}
                onChange={(e) =>
                  setFormValues({ ...formValues, description: e.target.value })
                }
                className={style.textareaField}
              />
            </div>
            <div className={style.inputGroup}>
              <label>Pseudo :</label>
              <input
                type="text"
                value={formValues.pseudo}
                onChange={(e) =>
                  setFormValues({ ...formValues, pseudo: e.target.value })
                }
                className={style.inputField}
              />
            </div>
            <div className={style.inputGroup}>
              <label>Nom :</label>
              <input
                type="text"
                value={formValues.lastName}
                onChange={(e) =>
                  setFormValues({ ...formValues, lastName: e.target.value })
                }
                className={style.inputField}
              />
            </div>
            <div className={style.inputGroup}>
              <label>Prénom :</label>
              <input
                type="text"
                value={formValues.firstName}
                onChange={(e) =>
                  setFormValues({ ...formValues, firstName: e.target.value })
                }
                className={style.inputField}
              />
            </div>
            <div className={style.inputGroup}>
              <label>Ville :</label>
              <input
                type="text"
                value={formValues.town}
                onChange={(e) =>
                  setFormValues({ ...formValues, town: e.target.value })
                }
                className={style.inputField}
              />
            </div>
            <div className={style.inputGroup}>
              <label>Profil GitHub :</label>
              <input
                type="text"
                value={formValues.github}
                onChange={(e) =>
                  setFormValues({ ...formValues, github: e.target.value })
                }
                className={style.inputField}
              />
            </div>
            <div className={style.inputGroup}>
              <label>Email :</label>
              <input
                type="email"
                value={formValues.mail}
                onChange={(e) =>
                  setFormValues({ ...formValues, mail: e.target.value })
                }
                className={style.inputField}
              />
            </div>
            <div className={style.inputGroup}>
              <label>Date de naissance :</label>
              <input
                type="date"
                value={formValues.birthday}
                onChange={(e) =>
                  setFormValues({ ...formValues, birthday: e.target.value })
                }
                className={style.inputField}
              />
            </div>
          </div>
          <div className={style.newPassword}>
            <label>Ancien mot de passe</label>
            <input
              type="password"
              placeholder="Ancien mot de passe"
              onChange={(e) => setOldPassword(e.target.value)}
              autoComplete="current-password"
            />
            <label>Nouveau mot de passe</label>
            <input
              type="password"
              placeholder="Nouveau mot de passe"
              onChange={(e) => setNewPassword(e.target.value)}
              autoComplete="current-password"
            />
          </div>
          <div className={style.editButtons}>
            <button className={style.editButton} onClick={updatedUserWithtoutPic}>
              Confirmer
            </button>
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
