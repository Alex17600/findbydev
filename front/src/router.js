import { lazy } from "react";
import { createBrowserRouter, Outlet } from "react-router-dom";
import App from "./App";
import FilterAuth from "./FilterAuth";
import { getToken } from "./data/Token";
import jwtDecode from "jwt-decode";


const Accueil = lazy(() => import("./pages/home/Accueil"));
const Register = lazy(() => import("./pages/register/Register"));
const Login = lazy(() => import("./pages/login/Login"));
const Profil = lazy(() => import("./pages/profil/Profil"));
const Card = lazy(() => import("./pages/profil/card/Card"));
const CardMobile = lazy(() => import("./pages/profil/card/CardMobile"));
const Account = lazy(() => import("./pages/profil/account/Account"));
const Notice = lazy(() => import("./pages/profil/notice/Notice"));
const Photo = lazy(() => import("./pages/register/photos/Photo"));
const UserDetails = lazy(() => import("./pages/profil/userDetails/UserDetails"));
const Informations = lazy(() => import("./pages/register/infos/Informations"));

const token = getToken();
const decodedToken = jwtDecode(token);
const userConnected = decodedToken;


const isMobileView = window.innerWidth < 928;

export const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <FilterAuth>
        <App />
      </FilterAuth>
    ),
    children: [
      {
        path: "accueil",
        element: <Accueil />,
      },
      {
        path: "register",
        element: (
          <Register>
            <Outlet />
          </Register>
        ),
        children: [
          {
            path: "informations",
            element: <Informations />,
          },
          {
            path: "photo",
            element: <Photo />,
          },
        ],
      },
      {
        path: "login",
        element: <Login />,
      },

      {
        path: "profil",
        element: (
          <Profil>
            <Outlet userConnected={userConnected}/>
          </Profil>
        ),
        children: [
          {
            path: "card",
            element: isMobileView ? <CardMobile /> : <Card />,
          },
          {
            path: ":userId/account",
            element: <Account  userConnected={userConnected}/>
          },
          {
            path: ":userId/notice",
            element: <Notice />
          },
          {
            path: ":userIdSelected/user-details",
            element: <UserDetails />
          }
        ]
      }
    ],
  },
]);
