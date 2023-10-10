import { useState, useMemo, useRef, useEffect } from "react";
import style from "./Notice.module.scss";
import { getToken } from "../../../data/Token";
import jwtDecode from "jwt-decode";

const Notice = () => {
    const [users, setUsers] = useState([]);
    const [userConnected, setUserConnected] = useState();

    useEffect(() => {
        const token = getToken();
        const decodedToken = jwtDecode(token);
        setUserConnected(decodedToken);
    }, [])

    console.log(userConnected);

    return (
        <div className={style.notice}>
            test
        </div>
    );
};

export default Notice;