import React from "react";
import { useNavigate } from "react-router-dom";
import style from "./Sidebar.module.scss";
import { VscAccount } from "react-icons/vsc";
import { FiSearch, FiSettings } from "react-icons/fi";
import { AiOutlineMessage } from "react-icons/ai";
import { BiLogOut } from "react-icons/bi";
import { clearToken } from "../../data/Token";

const iconColor = "#ee9c31";

const Sidebar = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    clearToken();
    navigate("accueil");
  };
  
  return (
    <div className={style.sidebar}>
        <FiSearch size={32} style={{ color: iconColor }}/>
        <FiSettings size={32} style={{ color: iconColor }}/>
        <AiOutlineMessage size={32} style={{ color: iconColor }}/>
        <VscAccount size={32} style={{ color: iconColor }}/>
        <div className={style.logoutIcon} onClick={handleLogout}>
          <BiLogOut size={32} style={{ color: iconColor }}/>
        </div>
    </div>
  );
};

export default Sidebar;
