import React, { useEffect, useState } from "react";
import style from "./Conversation.module.scss";
import { getConversationsForLoggedInUser } from "../../../apis/conversation";
import { findPhotoById } from "../../../apis/photos";
import { findUserById } from "../../../apis/users";
import { getAllMessagesFromIdConversation } from "../../../apis/messages";
import FooterMobile from "../../../components/footer/FooterMobile";
import { useNavigate } from "react-router-dom";

const Conversation = ({ userConnected }) => {
  const [conversations, setConversations] = useState([]);
  const [userPhotos, setUserPhotos] = useState({});
  const [userMatches, setUserMatches] = useState({});
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
          // Trouver l'ID de l'utilisateur qui n'est pas l'utilisateur connecté
          const otherUserId =
            conversation.user1 === userConnected.idUser
              ? conversation.user2
              : conversation.user1;
          const photo = await findPhotoById(otherUserId);
          photos[otherUserId] = photo;
          const match = await findUserById(otherUserId);
          matches[otherUserId] = match;
        }
        setUserPhotos(photos);
        setUserMatches(matches);
      } catch (error) {
        console.error("Erreur lors de la récupération des photos :", error);
      }
    }
    fetchUserPhotosAndMatches();
    // eslint-disable-next-line
  }, [conversations]);


  function handleConversationClick(conversation) {
    navigate(`../${conversation.idConversation}/message`)
  }


  return (
    <div className={style.conversation}>
      <div className={style.conversationBox}>
        <div className={style.conversations}>
          {conversations.map((conversation) => (
            <div key={conversation.idConversation} className={style.fiche}  onClick={() => handleConversationClick(conversation)}>
              {userPhotos[conversation.user2] && (
                <img
                  src={userPhotos[conversation.user2]}
                  alt={`personne liké`}
                />
              )}
              <p>{userMatches[conversation.user2] ? userMatches[conversation.user2].pseudo : "Utilisateur"}</p>
            </div>
          ))}
        </div>
      </div>
      <FooterMobile />
    </div>
  );
};

export default Conversation;

