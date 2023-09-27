import React, { useState } from "react";
import { downloadPhoto } from "../../../apis/users";
import { useLocation, useNavigate } from "react-router-dom";
import style from "./Photo.module.scss";
import { TfiClose } from "react-icons/tfi";

const Photo = () => {
  const location = useLocation();
  const userId = new URLSearchParams(location.search).get("userId");
  const [image, setImage] = useState(null);
  const iconColor = "#ffffff";
  const navigate = useNavigate();

  const handlePhotoUpload = async (e) => {
    e.preventDefault();

    if (!image) {
      console.error("Aucune image sélectionnée.");
      return;
    }

    try {
      const response = await downloadPhoto(userId, image);

      if (response.ok) {
        const imageUrl = URL.createObjectURL(image);
        setImage(imageUrl);
      } else {
        console.error(
          "Erreur lors du téléchargement de la photo :",
          response.status
        );
      }
    } catch (error) {
      console.error("Erreur lors du téléchargement de la photo :", error);
    }
  };

  const handleImageChange = (e) => {
    const photoFile = e.target.files[0];
    setImage(photoFile);
  };

  const handleClose = () => {
    navigate("/accueil")
  }

  return (
    <div className={style.photo}>
      <div className={style.blockPhoto}>
        <div className={style.close}  onClick={handleClose}>
          <TfiClose size={32} style={{ color: iconColor }} />
        </div>
        <h1>Ajouter une photo à votre profil</h1>
        <div className={style.resultPic}>
          {image && <img src={URL.createObjectURL(image)} alt={image.name} />}
        </div>
        <div className={style.inputButton}>
          <input type="file" onChange={handleImageChange} name="image" />
          <button onClick={handlePhotoUpload}>Envoyer</button>
        </div>
      </div>
    </div>
  );
};

export default Photo;
