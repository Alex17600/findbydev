import React from "react";
import style from "./Sidebar.module.scss";
import { VscAccount } from "react-icons/vsc";
import { FiSearch, FiSettings } from "react-icons/fi";
import { AiOutlineMessage } from "react-icons/ai";

const Sidebar = () => {
  return (
    <div className={style.sidebar}>
        <FiSearch size={32} style={{color: '#ee9c31'}}/>
        <FiSettings size={32} style={{color: '#ee9c31'}}/>
        <AiOutlineMessage size={32} style={{color: '#ee9c31'}}/>
        <VscAccount size={32} style={{color: '#ee9c31'}}/>
    </div>
  );
};

export default Sidebar;
