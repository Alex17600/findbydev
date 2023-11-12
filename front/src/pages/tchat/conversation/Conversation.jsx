import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import style from "./Conversation.module.scss";
import { getConversationsForLoggedInUser } from "../../../apis/conversation";
import { findPhotoById } from "../../../apis/photos";
import FooterMobile from "../../../components/footer/FooterMobile";
import { useNavigate } from "react-router-dom";
import { getToken } from "../../../data/Token";
import jwtDecode from "jwt-decode";
import { findUserById } from "../../../apis/users";

const Conversation = () => {
  const { userId } = useParams();
  const [conversations, setConversations] = useState([]);
  const [userPhotos, setUserPhotos] = useState({});
  const [userPseudos, setUserPseudos] = useState({});
  const navigate = useNavigate();
  const token = getToken();
  const userConnected = token ? jwtDecode(token) : null;

  useEffect(() => {
    async function fetchData() {
      if (userConnected && userConnected.idUser === Number(userId)) {
        try {
          const data = await getConversationsForLoggedInUser(userId);
          setConversations(data);

          const photos = {};
          const pseudos = {};

          for (const conversation of data) {
            const receiver =
              conversation.userReceiver === userConnected.idUser
                ? conversation.userSender
                : conversation.userReceiver;
            const photo = await findPhotoById(receiver);
            photos[receiver] = photo;

            const user = await findUserById(receiver);

            pseudos[receiver] = user.pseudo;
          }

          setUserPhotos(photos);
          setUserPseudos(pseudos);
        } catch (error) {
          console.error("Erreur lors de la récupération des données :", error);
        }
      } else {
        navigate("/accueil");
      }
    }

    fetchData();
  }, []);

  function handleConversationClick(conversation) {
    navigate(`../${conversation.idConversation}/message`);
  }

  return (
    <div className={style.conversation}>
    <div className={style.conversationBox}>
      <div className={style.conversations}>
        {conversations.length === 0 ? (
          <p className={style.notice}>Vous n'avez pas de conversation pour le moment.</p>
        ) : (
          conversations.map((conversation, index) => {
            const otherUserId =
              conversation.userReceiver === userConnected.idUser
                ? conversation.userSender
                : conversation.userReceiver;
            const photo = userPhotos[otherUserId];
            const pseudo = userPseudos[otherUserId];

            return (
              <div
                key={index}
                className={style.fiche}
                onClick={() => handleConversationClick(conversation)}
              >
                {photo && <img src={photo} alt={`personne liké`} />}
                <div className={style.pseudo}>
                  {pseudo && <p>{pseudo}</p>}
                </div>
              </div>
            );
          })
        )}
      </div>
    </div>
      <FooterMobile />
    </div>
  );
};

export default Conversation;
