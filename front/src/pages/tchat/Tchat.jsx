import React from 'react';
import { Outlet } from "react-router-dom";

const Tchat = () => {
    return (
        <div>
            <Outlet />
        </div>
    );
};

export default Tchat;