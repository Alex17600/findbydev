import React from "react";
import style from "./Language.module.scss";

const Languages = () => {
  return (
    <div className={style.language}>
      <div className={style.blockLanguage}>
        <input type="text" />
        <input type="text" />
        <input type="text" />
        <input type="text" />
      </div>
    </div>
  );
};

export default Languages;
