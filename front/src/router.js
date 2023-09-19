import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import App from "./App";


const Accueil = lazy(() => import("./pages/accueil/Accueil"));
const Register = lazy(() => import("./pages/inscription/Register"));
const Login = lazy(() => import("./pages/login/Login"));
const ProfilMobile = lazy(() => import("./pages/profil/ProfilMobile"));
const Profil = lazy(() => import("./pages/profil/Profil"));

const isMobileView = window.innerWidth < 928;

export const router = createBrowserRouter([
  {
    path: "/",
    element: (
    <App />
    ),
    children: [
      {
        path: "accueil",
        element: <Accueil />,
      },
      {
        path: "register",
        element: <Register />,
      },
      {
        path: "login",
        element: <Login />,
      },
      {
        path: "profil",
        element: isMobileView ? <ProfilMobile /> : <Profil />,
      },
    ],
  },
]);
