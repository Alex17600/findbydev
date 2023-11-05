import { Fragment, Suspense, useEffect, useState } from "react";
import { Outlet, useLocation } from "react-router-dom";
import HeaderAccueil from "./components/header/HeaderAccueil";
import Sidebar from "./components/sidebar/Sidebar";

function App() {
  const location = useLocation();
  const [windowWidth, setWindowWidth] = useState(window.innerWidth);

  useEffect(() => {
    const handleResize = () => {
      setWindowWidth(window.innerWidth);
    };
    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

 
  const excludedPages = ["/accueil", "/register/informations", "/login", "/rgpd", "/"];

  return (
    <Fragment>
      <HeaderAccueil />
      {windowWidth > 928 && !excludedPages.includes(location.pathname) && (
        <Sidebar />
      )}
      <Suspense>
        <Outlet />
      </Suspense>
    </Fragment>
  );
}

export default App;
