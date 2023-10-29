import React, { useEffect, useState } from "react";
import style from "./Conversation.module.scss";
import { getConversationsForLoggedInUser } from "../../../apis/conversation";
import { findUserById } from "../../../apis/users";
import { findPhotoById } from "../../../apis/photos";
import FooterMobile from "../../../components/footer/FooterMobile";
import { useNavigate } from "react-router-dom";

const Conversation = ({ userConnected }) => {
  const [conversations, setConversations] = useState([]);
  const [userPhotos, setUserPhotos] = useState({});
  const [userMatches, setUserMatches] = useState({});
  const [otherUserId, setOtherUserId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAllConversation = async () => {
      try {
        const data = await getConversationsForLoggedInUser(
          userConnected.idUser
        );
        setConversations(data);
      } catch (error) {
        console.error(
          "Erreur lors de la récupération des conversations :",
          error
        );
      }
    };

    fetchAllConversation();
  }, [userConnected]);

  useEffect(() => {
    async function fetchUserPhotosAndMatches() {
      try {
        const photos = {};
        const matches = {};

        for (const conversation of conversations) {
          const receiver =
            conversation.userReceiver === userConnected.idUser
              ? conversation.userSender
              : conversation.userReceiver;

          // Récupérer la photo de l'autre utilisateur
          setOtherUserId(receiver)
          const photo = await findPhotoById(receiver);
          photos[receiver] = photo;

          // Récupérer les informations de l'autre utilisateur
          const match = await findUserById(receiver);
          matches[receiver] = match;
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
        {conversations.map((conversation) => (
          <div
            key={conversation.idConversation}
            className={style.fiche}
            onClick={() => handleConversationClick(conversation)}
          >
            {userPhotos[otherUserId] && (
              <img
                src={userPhotos[otherUserId]}
                alt={`personne liké`}
              />
            )}
            <p>
              {userMatches[otherUserId]
                ? userMatches[otherUserId].pseudo
                : "Utilisateur"}
            </p>
          </div>
        ))}
      </div>
    </div>
    <FooterMobile />
  </div>
  );
};

export default Conversation;
