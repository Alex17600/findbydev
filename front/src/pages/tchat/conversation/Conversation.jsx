import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import style from "./Conversation.module.scss";
import { getConversationsForLoggedInUser } from "../../../apis/conversation";
import { findUserById } from "../../../apis/users";
import { findPhotoById } from "../../../apis/photos";
import FooterMobile from "../../../components/footer/FooterMobile";
import { useNavigate } from "react-router-dom";

const Conversation = ({ userConnected }) => {
  const { userId } = useParams();
  const [conversations, setConversations] = useState([]);
  const [userPhotos, setUserPhotos] = useState({});
  const [userMatches, setUserMatches] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    if (userConnected.idUser !== parseInt(userId)) {
      navigate("/accueil"); // Redirigez vers la page d'accueil ou une autre page d'erreur
    }
  
    const fetchAllConversation = async () => {
      try {
        const data = await getConversationsForLoggedInUser(userId);
        setConversations(data);
      } catch (error) {
        console.error("Erreur lors de la récupération des conversations :", error);
      }
    };

    fetchAllConversation();
  }, [userId]);

  useEffect(() => {
    async function fetchUserPhotosAndMatches() {
      try {
        const photos = {};
        const matches = {};

        for (const conversation of conversations) {
          const receiver =
            conversation.userReceiver === userId
              ? conversation.userSender
              : conversation.userReceiver;

          // Vérifiez si la conversation a déjà été ajoutée à la liste
          if (!photos[receiver]) {
            // Récupérer la photo de l'autre utilisateur
            const photo = await findPhotoById(receiver);
            photos[receiver] = photo;
          }

          if (!matches[receiver]) {
            // Récupérer les informations de l'autre utilisateur
            const match = await findUserById(receiver);
            matches[receiver] = match;
          }
        }

        setUserPhotos(photos);
        setUserMatches(matches);
      } catch (error) {
        console.error("Erreur lors de la récupération des photos :", error);
      }
    }

    if (conversations.length > 0) {
      fetchUserPhotosAndMatches();
    }
  }, [conversations, userConnected]);

  function handleConversationClick(conversation) {
    navigate(`../${conversation.idConversation}/message`);
  }

  return (
    <div className={style.conversation}>
      <div className={style.conversationBox}>
        <div className={style.conversations}>
          {conversations.map((conversation) => {
            const receiver =
              conversation.userReceiver === userConnected.idUser
                ? conversation.userSender
                : conversation.userReceiver;

            return (
              <div
                key={conversation.idConversation}
                className={style.fiche}
                onClick={() => handleConversationClick(conversation)}
              >
                {userPhotos[receiver] && (
                  <img
                    src={userPhotos[receiver]}
                    alt={`personne liké`}
                  />
                )}
                <p>
                  {userMatches[receiver]
                    ? userMatches[receiver].pseudo
                    : "Utilisateur"}
                </p>
              </div>
            );
          })}
        </div>
      </div>
      <FooterMobile />
    </div>
  );
};

export default Conversation;
