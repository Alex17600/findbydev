import React, { useEffect, useState } from "react";
import style from "./Card.module.scss";
import { getAllUsers } from "../../../apis/users";
import { findPhotoById } from "../../../apis/photos";

const Profil = () => {
  const [users, setUsers] = useState([]);
  const [userPhotos, setUserPhotos] = useState({});

  useEffect(() => {
    async function fetchData() {
      try {
        const data = await getAllUsers();
        setUsers(data);
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des utilisateurs :",
          error
        );
      }
    }

    fetchData();
  }, []);

  useEffect(() => {
    async function fetchPhotos() {
      const photoData = {};
      for (const user of users) {
        try {
          const photoUrl = await findPhotoById(user.id);
          photoData[user.id] = photoUrl;
        } catch (error) {
          console.error("Erreur lors de la récupération de la photo :", error);
        }
      }
      setUserPhotos(photoData);
    }

    fetchPhotos();
  }, [users]);
  console.log(users)

  return (
    <div className={style.profil}>
      <div className={style.block}>
        {users.map((user) => (
          <div key={user.id} className={style.user}>
            {userPhotos[user.id] && (
              <img src={userPhotos[user.id]} alt={user.name} />
            )}
            <p>{user.pseudo}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Profil;
