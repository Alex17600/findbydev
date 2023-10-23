import React from "react";
import style from "./Extension.module.scss";
import SettingsNavbar from "./settings/SettingsNavbar";
import SearchNavbar from "./search/SearchNavbar";
import MessageNavbar from "./message/MessageNavbar";
import AccountNavbar from "./account/AccountNavbar";
import { IoCloseSharp } from "react-icons/io5";

const Extension = ({ selectedIcon, setExtensionVisible, userConnected  }) => {


  const handleCloseExtension = () => {
    setExtensionVisible(false);
  };

  return (
    <div className={style.extension}>
      <IoCloseSharp size={32} style={{ color: "#FFFFFF" }} onClick={handleCloseExtension}/>
      {selectedIcon === "search" && <SearchNavbar userConnected={userConnected}/>}
      {selectedIcon === "settings" && <SettingsNavbar userConnected={userConnected}/>}
      {selectedIcon === "message" && <MessageNavbar userConnected={userConnected}/>}
      {selectedIcon === "account" && <AccountNavbar userConnected={userConnected}/>}
    </div>
  );
};

export default Extension;
