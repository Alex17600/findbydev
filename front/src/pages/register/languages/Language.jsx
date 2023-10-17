import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import style from "./Language.module.scss";
import {
  getAllTechnologies,
  getIconTechnologie,
} from "../../../apis/technology";

import { createPrefer } from "../../../apis/prefers";

const Languages = ({ userConnected }) => {
  const [languages, setLanguages] = useState([]);
  const [selectedLanguages, setSelectedLanguages] = useState([]);
  const [idUser, setIdUser] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchData() {
      try {

        setIdUser(userConnected.idUser);

        const fetchTechnos = await getAllTechnologies();
        setLanguages(fetchTechnos);

        const iconPromises = fetchTechnos.map((technology) =>
          getIconTechnologie(technology.idTechnology)
        );

        const icons = await Promise.all(iconPromises);

        // Mettre à jour les icônes dans le state
        setLanguages((prevLanguages) =>
          prevLanguages.map((language, index) => ({
            ...language,
            icon: icons[index],
          }))
        );
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des technologies:",
          error
        );
      }
    }
    fetchData();
    // eslint-disable-next-line
  }, []);

  const handleLanguageClick = (language) => {
    if (selectedLanguages.includes(language)) {
      setSelectedLanguages((prevSelected) =>
        prevSelected.filter((selected) => selected !== language)
      );
    } else {
      setSelectedLanguages((prevSelected) => [...prevSelected, language]);
    }
  };

  const handleSavePreferences = async () => {
    const userId = idUser;

    const technologyIds = selectedLanguages.map(
      (language) => language.idTechnology
    );
    try {
      await createPrefer(userId, technologyIds);
      navigate(`../photo?userId=${idUser}`)
    } catch (error) {
      console.error("Erreur lors de la création de préférences:", error);
    }
  };


  return (
    <div className={style.language}>
      <div className={style.blockLanguage}>
        <h1>Choisissez vos langages et frameworks préférés</h1>
        <div className={style.languagesIcons}>
          {languages.map((language, index) => (
            <div
              className={`${style.nameIcon} ${
                selectedLanguages.includes(language) ? style.selected : ""
              }`}
              key={index}
              onClick={() => handleLanguageClick(language)}
            >
              <p>{language.name}</p>
              <img
                src={language.icon}
                alt={language.name}
                className={style.iconLanguage}
              />
            </div>
          ))}
        </div>
        {selectedLanguages.length > 0 && ( 
        <div>
          <h2>Votre sélection</h2>
          <div className={style.selectedLanguages}>
            {selectedLanguages.map((language, index) => (
              <div className={style.selectedLanguage} key={index}>
                <p>{language.name}</p>
                <img
                  src={language.icon}
                  alt={language.name}
                  className={style.selectedIcon}
                />
              </div>
            ))}
          </div>
          <div className={style.buttonContainer}>
            <button onClick={handleSavePreferences}>Étape suivante</button>
          </div>
        </div>
      )}
      </div>
    </div>
  );
};

export default Languages;
