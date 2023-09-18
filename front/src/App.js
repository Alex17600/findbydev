import { Fragment, Suspense } from "react";
import { Outlet } from "react-router-dom";
import HeaderAccueil from "./components/header/HeaderAccueil";

function App() {
  return (
    <Fragment>
      <HeaderAccueil />
      <Suspense>
        <Outlet />
      </Suspense>
    </Fragment>
  );
}

export default App;
