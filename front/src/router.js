import { lazy } from "react";
import { createBrowserRouter, Outlet } from "react-router-dom";
import App from "./App";
import FilterAuth from "./FilterAuth";
import { getToken } from "./data/Token";
import jwtDecode from "jwt-decode";
import { SearchResultsProvider } from "./context/SearchResultsContext";

const Accueil = lazy(() => import("./pages/home/Accueil"));
const Register = lazy(() => import("./pages/register/Register"));
const Informations = lazy(() => import("./pages/register/infos/Informations"));
const Language = lazy(() => import("./pages/register/languages/Language"));
const Photo = lazy(() => import("./pages/register/photos/Photo"));
const Login = lazy(() => import("./pages/login/Login"));
const Profil = lazy(() => import("./pages/profil/Profil"));
const Card = lazy(() => import("./pages/profil/card/Card"));
const CardMobile = lazy(() => import("./pages/profil/card/CardMobile"));
const Account = lazy(() => import("./pages/profil/account/Account"));
const Notice = lazy(() => import("./pages/profil/notice/Notice"));
const Tchat = lazy(() => import("./pages/tchat/Tchat"));
const Conversation = lazy(() => import("./pages/tchat/conversation/Conversation"));
const Message = lazy(() => import("./pages/tchat/message/Message"));
const UserDetails = lazy(() => import("./pages/profil/userDetails/UserDetails"));
const GitProfil = lazy(() => import("./pages/register/git/GitProfil"));
const Rgpd = lazy(() => import("./components/rgpd/Rgpd"));
const Search = lazy(() => import("./pages/search/Search"));
const Results = lazy(() => import("./pages/search/Result"));



const token = getToken();
const userConnected = token ? jwtDecode(token) : null;


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
        path: "rgpd",
        element: <Rgpd />,
      },
      {
        path: "register",
        element: (
          <Register>
            <Outlet userConnected={userConnected}/>
          </Register>
        ),
        children: [
          {
            path: "informations",
            element: <Informations />,
          },
          {
            path: "git",
            element: <GitProfil />,
          },
          {
            path: ":userId/language",
            element: <Language />,
          },
          {
            path: ":userId/photo",
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
            element: <Account/>
          },
          {
            path: ":userId/notice",
            element: <Notice />
          },
          {
            path: ":userId/user-details",
            element: 
            <SearchResultsProvider>
              <UserDetails />
            </SearchResultsProvider>
          }
        ]
      },
      {
        path: "tchat",
        element: (
          <Tchat>
            <Outlet />
          </Tchat>
        ),
        children: [
          {
            path: ":userId/conversation",
            element: <Conversation/>
          },
          {
            path: ":idConversation/message",
            element: <Message/>
          }
        ],
      },
      {
        path: "search",
        element:
        <SearchResultsProvider>
           <Search />
        </SearchResultsProvider> 
      }, 
      {
        path: "results",
        element: 
        <SearchResultsProvider>
          <Results />
        </SearchResultsProvider>
      }
    ],
  },
]);
