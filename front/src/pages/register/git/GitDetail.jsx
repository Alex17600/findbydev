import React, { useEffect, useState } from "react";
import style from "./GitDetail.module.scss";
import {
  getAccessToken,
  getuserInfosWithAccesToken,
  getLastProjectsFromIdGit,
  getLastPushFromLastProject,
} from "../../../apis/git";
import { setGitInfos } from "../../../apis/users";
import dayjs from "dayjs";
import "dayjs/locale/fr";
import { getToken } from "../../../data/Token";
import jwtDecode from "jwt-decode";
import { useNavigate } from "react-router-dom";

const GitDetail = () => {
  // eslint-disable-next-line
  const [accessToken, setAccessToken] = useState("");
  const [pseudoGit, setPseudoGit] = useState("");
  // eslint-disable-next-line
  const [idGit, setIdGit] = useState();
  const [projects, setProjects] = useState([]);
  const [push, setPush] = useState("");
  const [success, setSuccess] = useState(false);
  const token = getToken();
  const userId = jwtDecode(token).idUser;
  const navigate = useNavigate();

  useEffect(() => {
    async function fetchData() {
      try {
        const response = await getAccessToken();
        setAccessToken(response);

        if (response) {
          const userInfo = await getuserInfosWithAccesToken(response);
          setPseudoGit(userInfo.username);
          setIdGit(userInfo.id);

          const projects = await getLastProjectsFromIdGit(userInfo.id);
          setProjects(projects);

          if (projects) {
            const data = await getLastPushFromLastProject(projects[0].id);
            setPush(data[0]);
          }
        }
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des informations git user :",
          error
        );
      }
    }

    fetchData();
  }, []);

  const handlePostGitInfo = async () => {
    try {
      const gitInfo = {
        pseudoGit: pseudoGit,
        idGit: idGit,
        userId: userId
      };

      await setGitInfos(gitInfo);
      setSuccess(true);

      setTimeout(() => {
        navigate("/profil/card");
      }, 3000);

    } catch (error) {
      console.error("Erreur lors de la confirmation des informations Git :", error);
    }
  };

  dayjs.locale("fr");

  return (
    <div className={style.gitdetail}>
      <div className={style.recapBlock}>
        <h1>Veuillez confirmer vos informations:</h1>
        {success && (
            <div className={style.successText}>
              <p>
              Informations Git confirmées avec succès. Redirection dans 3 secondes...
              </p>
            </div>
          )}
        <div className={style}>
          <p>Votre pseudo Git: {pseudoGit}</p>
          <div className={style.projects}>
            <p>Vos projets: (public)</p>
            <ul>
              {projects.map((project, index) => (
                <li key={index}>{project.name}</li>
              ))}
            </ul>
          </div>
          <p>Titre du dernier Push: {push.title}</p>
          <p>Daté du: {dayjs(push.created_at).format("DD MMMM YYYY HH:mm")}</p>
          <div className={style.bottom}>
            <button onClick={handlePostGitInfo}>Confirmer</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default GitDetail;
