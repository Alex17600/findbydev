import React, { useState, useEffect } from "react";
import { downloadPhoto } from "../../../apis/users";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import style from "./Photo.module.scss";
import { TfiClose } from "react-icons/tfi";
import { getToken } from "../../../data/Token";
import jwtDecode from "jwt-decode";

const Photo = () => {
  const userId = useParams();
  const [image, setImage] = useState(null);
  const iconColor = "#ffffff";
  const navigate = useNavigate();
  
  const token = getToken();
  const userConnected = token ? jwtDecode(token) : null;

  useEffect(() => {
    try {
      if( userConnected.idUser !== parseInt(userId.userId)) {
        navigate("/accueil")
      }
    } catch (error) {
      
    }
  }, []);

  const handlePhotoUpload = async (e) => {
    e.preventDefault();
    if (!image) {
      console.error("Aucune image sélectionnée.");
      return;
    }
    try {
      
      const response = await downloadPhoto(userId, image);
      console.log(userId, image);
      if (response.ok) {
        const imageUrl = URL.createObjectURL(image); 
        setImage(imageUrl);
        
        setTimeout(() => {
          navigate("/profil");
        }, 3000);
      } else {
        setTimeout(() => {
          navigate("/profil/card");
        }, 3000);
        console.error("Erreur lors du téléchargement de la photo.");
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
