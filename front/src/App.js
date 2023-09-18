import { Fragment, Suspense } from "react";
import { Outlet } from "react-router-dom";
import Accueil from "./pages/accueil/Accueil";
import HeaderAccueil from "./components/header/HeaderAccueil";

function App() {
  return (
    <Fragment>
      <Suspense>
        <HeaderAccueil />
        <Accueil />
        <Outlet />
      </Suspense>
    </Fragment>
  );
}

export default App;
