import React, { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import style from "./UserDetails.module.scss";
import { findUserById } from "../../../apis/users";
import { findPhotoById } from "../../../apis/photos";
import FooterMobile from "../../../components/footer/FooterMobile";
import { getIconTechnologie } from "../../../apis/technology";
import { getAllTechnologysByUserPrefers } from "../../../apis/prefers";
import { IoReturnUpBack } from "react-icons/io5";
import { useSearchResults  } from "../../../context/SearchResultsContext";
import dayjs from "dayjs";

const UserDetails = () => {
  const { userId } = useParams();
  const { searchResults } = useSearchResults();
  const [userData, setUserData] = useState(null);
  const [photo, setPhoto] = useState({});
  const [technologys, setTechnologys] = useState({});
  const [prefers, setPrefers] = useState({});
  const [isModalOpen, setIsModalOpen] = useState(false);

  console.log(searchResults);


  useEffect(() => {
    const fetchUserSelected = async () => {
      if (userId) {
        try {
          const data = await findUserById(userId);
          const photoUrl = await findPhotoById(userId);
          const preferData = await getAllTechnologysByUserPrefers(userId);

          setPhoto(photoUrl);
          setUserData(data);

          setPrefers(preferData);
          
          const technologyIcons = [];
          for (const prefer of preferData) {
            const icon = await getIconTechnologie(
              prefer.technology.idTechnology
            );
            technologyIcons.push({ technology: prefer.technology, icon });
          }
          setTechnologys(technologyIcons);
        } catch (error) {
          console.error(
            "Erreur lors de la récupération des données de l'utilisateur :",
            error
          );
        }
      }
    };

    fetchUserSelected();
  }, [userId]);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const calculateAge = (birthday) => {
    const birthDate = dayjs(birthday);
    const currentDate = dayjs();
    const age = currentDate.diff(birthDate, "year");
    return age;
  };

  return (
    <>
      <div className={style.userProfile}>
        <div className={style.cardProfile}>
          <div className={style.photo}>
            <img src={photo} alt={userData?.pseudo} onClick={openModal} />
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
            <div className={style.technos}>
              <h2>Technologies :</h2>
              <div className={style.technologiesContainer}>
                {technologys.length > 0 ? (
                  technologys.map((tech, index) => (
                    <div key={index} className={style.technology}>
                      <img
                        src={tech.icon}
                        alt={tech.technology.name}
                        style={{ width: "60px", height: "auto" }}
                      />
                      <p>{tech.technology.name}</p>
                    </div>
                  ))
                ) : (
                  <p>Aucune technologie trouvée.</p>
                )}
              </div>
            </div>
            <div className={style.age}>
              <p>Âge :</p>
              {userData && userData.birthday && (
                <p>{calculateAge(userData.birthday)} ans</p>
              )}
            </div>
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
