import React, { useState } from "react";
import style from "./GitProfil.module.scss";

const GitProfil = () => {
  const handleGitLabLogin = () => {
    window.location.href = 'http://localhost:8000/api/login/oauth2/authorization/gitlab';
  };

  return (
    <div className={style.gitProfil}>
      <div className={style.contain}>
        <p>
          Vous allez être redirigé vers votre Git pour nous autoriser à accéder
          à vos derniers push (public).
        </p>
        <p>Ensuite, vous pourrez reprendre votre inscription.</p>
        <div className={style.gitlab}>
          <button onClick={handleGitLabLogin}>Se connecter à Git</button>
        </div>
      </div>
    </div>
  );
};

export default GitProfil;
