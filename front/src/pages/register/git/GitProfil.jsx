import React, { useState } from "react";
import style from "./GitProfil.module.scss";
import { BiRightArrow } from "react-icons/bi";
import { checkGitLabUsername } from "../../../apis/git";

const GitProfil = () => {

  const [errorGit, setErrorGit] = useState("");
  const [succesGit, setSuccesGit] = useState("");
  const [gitProfile, setGitProfile] = useState("");

  const handleChange = (e, setter) => {
    setter(e.target.value);
  };

  //check git pseudo
  const handleGitLabUsernameCheck = async () => {
    if (gitProfile) {
      const response = await checkGitLabUsername(gitProfile);

      if (response) {
        setGitProfile(response);
        const clientIdGit =
          "8344cd957fa999554284610b479508c7de4f0f76f5d7f4c9fc6e6dbdd702a4a7";
          const randomState = Math.random().toString(36).substring(2, 15) + Math.random().toString(36).substring(2, 15);
          const gitLabAuthUrl = `https://gitlab.com/oauth/authorize?client_id=${clientIdGit}&redirect_uri=http://localhost:8080/websocket/login/oauth2/code/gitlab&response_type=code&scope=api+read_user&state=${randomState}`;
          

        window.location.href = gitLabAuthUrl;

        setSuccesGit("Le pseudo GitLab est valide.");
        setErrorGit("");
      } else {
        setErrorGit(
          "Le pseudo GitLab n'est pas valide. Veuillez corriger ou choisir un autre pseudo."
        );
        setSuccesGit("");
      }
    } else {
      setErrorGit("Veuillez entrer un pseudo GitLab.");
      setSuccesGit("");
    }
  };

  return (
    <div className={style.gitProfil}>
      <div className={style.contain}>
      <p>Vous allez être redirigé vers votre Git pour nous autoriser à accéder à vos derniers push (public).</p>
      <p>Ensuite, vous pourrez reprendre votre inscription.</p>
        {errorGit && <div className={style.errorText}>{errorGit}</div>}
        {succesGit && <div className={style.succesText}>{succesGit}</div>}
        <div className={style.gitlab}>
          <input
            type="text"
            placeholder="Votre pseudo Git"
            value={gitProfile}
            onChange={(e) => handleChange(e, setGitProfile)}
          />
          <span className={style.addon}>
            <BiRightArrow onClick={handleGitLabUsernameCheck} />
          </span>
        </div>
      </div>
    </div>
  );
};

export default GitProfil;
