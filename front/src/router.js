import { lazy } from "react";
import { Outlet, createBrowserRouter } from "react-router-dom";
import App from "./App";

const Accueil = lazy(() => import("./pages/accueil/Accueil"));

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
    ]
  },
]);
