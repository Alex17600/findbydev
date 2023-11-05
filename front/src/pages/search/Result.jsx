import React, { useEffect, useState } from "react";
import style from "./Result.module.scss";
import { useLocation, useNavigate } from "react-router-dom";
import dayjs from "dayjs";
import { findPhotoById } from "../../apis/photos";
import FooterMobile from "../../components/footer/FooterMobile";
import { IoReturnUpBack } from "react-icons/io5";
import { useSearchResults } from "../../context/SearchResultsContext";

const Result = () => {
  const location = useLocation();
  const { searchResults } = useSearchResults();
  const calculateAge = (birthday) => {
    const birthDate = dayjs(birthday);
    const currentDate = dayjs();
    const age = currentDate.diff(birthDate, "year");
    return age;
  };
  const [userPhotos, setUserPhotos] = useState({});
  const navigate = useNavigate();
  console.log(searchResults);

  useEffect(() => {
    // Fonction pour récupérer les photos
    async function fetchUserPhotos() {
      try {
        const photos = {};
        for (const user of searchResults) {
          // Récupérez la photo de l'utilisateur en utilisant user.id
          const photo = await findPhotoById(user.id);
          photos[user.id] = photo;
        }
        setUserPhotos(photos);
      } catch (error) {
        console.error("Erreur lors de la récupération des photos :", error);
      }
    }

    fetchUserPhotos();
  }, [searchResults]);

  const handleCardClick = (userId) => {
    
    navigate(`/profil/${userId}/user-details`, {
      state: { fromResults: true, searchResults },
    });
  };

  return (
    <div className={style.result}>
      {searchResults && searchResults.length > 0 ? (
        searchResults.map((result) => (
          <div
            key={result.id}
            className={style.resultCard}
            onClick={() => handleCardClick(result.id)}
          >
            {userPhotos[result.id] && (
              <img
                src={userPhotos[result.id]}
                alt={`Image de ${result.pseudo}`}
              />
            )}
            <div className={style.infos}>
              <h1>{result.pseudo}</h1>
              <p>{result.town}</p>
              {result.birthday && <p>Âge : {calculateAge(result.birthday)}</p>}
            </div>
          </div>
        ))
      ) : (
        <>
          <div className={style.warningMessage}>
            <IoReturnUpBack
              className={style.iconBack}
              onClick={() => navigate("/search")}
            />
            <p className={style.noresult}>
              Aucun profil ne correspond à votre recherche.
            </p>
          </div>
        </>
      )}
      <FooterMobile />
    </div>
  );
};

export default Result;
