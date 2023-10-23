import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import style from "./Sidebar.module.scss";
import { VscAccount } from "react-icons/vsc";
import { FiSearch, FiSettings } from "react-icons/fi";
import { AiOutlineMessage } from "react-icons/ai";
import { BiLogOut } from "react-icons/bi";
import { clearToken, getToken } from "../../data/Token";
import Extension from "./components/Extension";
import jwtDecode from "jwt-decode";

const iconColor = "#ee9c31";

const Sidebar = () => {
  const navigate = useNavigate();
  const token = getToken();
  const userConnected = token ? jwtDecode(token) : null;
  const [selectedIcon, setSelectedIcon] = useState(null);
  const [isExtensionVisible, setExtensionVisible] = useState(false);

  const handleLogout = () => {
    clearToken();
    navigate("accueil");
  };

  const handleIconClick = (icon) => {
    setExtensionVisible(true);
    setSelectedIcon(icon);
  };

  return (
    <div className={style.sidebar}>
      <FiSearch
        size={32}
        style={{ color: iconColor }}
        onClick={() => handleIconClick("search")}
      />
      <FiSettings
        size={32}
        style={{ color: iconColor }}
        onClick={() => handleIconClick("settings")}
      />
      <AiOutlineMessage
        size={32}
        style={{ color: iconColor }}
        onClick={() => handleIconClick("message")}
      />
      <VscAccount
        size={32}
        style={{ color: iconColor }}
        onClick={() => handleIconClick("account")}
      />
      <div className={style.logoutIcon} onClick={handleLogout}>
        <BiLogOut size={32} style={{ color: iconColor }} />
      </div>
      {isExtensionVisible && (
        <Extension
          selectedIcon={selectedIcon}
          setExtensionVisible={setExtensionVisible}
          userConnected={userConnected}
        />
      )}
    </div>
  );
};

export default Sidebar;
