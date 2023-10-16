import React, { useState, useEffect } from "react";
import style from "./Language.module.scss";
import {
  getAllTechnologies,
  getIconTechnologie,
} from "../../../apis/technology";
import { getToken } from "../../../data/Token";
import jwtDecode from "jwt-decode";

const Languages = () => {
  const [languages, setLanguages] = useState([]);
  // const [isAdmin, setIsAdmin] = useState(false);

  useEffect(() => {
    async function fetchData() {
      try {
        const token = getToken();
        const decodedToken = jwtDecode(token);
        const role = decodedToken.roles;

        // if(role.includes('ROLE_ADMIN')) {
        //   setIsAdmin(true);
        // };

        const fetchTechnos = await getAllTechnologies();
        setLanguages(fetchTechnos);

        const iconPromises = fetchTechnos.map((technology) =>
          getIconTechnologie(technology.idTechnology)
        );

        // Attendre toutes les promesses pour obtenir les icônes
        const icons = await Promise.all(iconPromises);

        // Mettre à jour les icônes dans le state
        setLanguages((prevLanguages) =>
          prevLanguages.map((language, index) => ({
            ...language,
            icon: icons[index], // Ajoutez l'icône à chaque technologie
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
  }, []);

  return (
    <div className={style.language}>
      <div className={style.blockLanguage}>
        <h1>Choisissez vos langages et frameworks préférés</h1>
        <p>Étape 2/3</p>
        <div className={style.languagesIcons}>
          {languages.map((language, index) => (
            <div className={style.nameIcon} key={index}>
              <p>{language.name}</p>
              <img
                src={language.icon}
                alt={language.name}
                className={style.iconLanguage}
              />
            </div>
          ))}
        </div>
        {/* {isAdmin && (
          <div className={style.addLogo}>
            <input type="text" />
            <input type="file" />
          </div>
        )} */}
      </div>
    </div>
  );
};

export default Languages;
