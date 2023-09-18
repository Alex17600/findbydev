import { lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import App from "./App";

const Accueil = lazy(() => import("./pages/accueil/Accueil"));
const Register = lazy(() => import("./pages/inscription/Register"));
const Login = lazy(() => import("./pages/login/Login"));

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
      }
    ],
  },
]);
