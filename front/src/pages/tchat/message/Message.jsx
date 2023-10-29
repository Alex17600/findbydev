import React, { useEffect, useState } from "react";
import style from "./Message.module.scss";
import { useParams } from "react-router-dom";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { getToken } from "../../../data/Token";
import FooterMobile from "../../../components/footer/FooterMobile";
import { getConversationById } from "../../../apis/conversation";
import { getAllMessagesFromIdConversation } from "../../../apis/messages";
import { findUserById } from "../../../apis/users";
import { createMessage } from "../../../apis/messages";

const Message = ({ userConnected }) => {
  const { idConversation } = useParams();
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [nouveauMessage, setNouveauMessage] = useState("");
  const [receiverId, setReceiverId] = useState(null);
  const [senderId, setSenderId] = useState(null);
  const [senderPseudo, setSenderPseudo] = useState("");
  

  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/websocket/ws", null, {
      headers: {
        Authorization: `Bearer ${getToken()}`,
      },
    });
    const client = Stomp.over(socket);

    client.connect({}, () => {
      client.subscribe("/topic/messages", (nouveauMessage) => {
        const messageReçu = JSON.parse(nouveauMessage.body);
        setMessages((prevMessages) => [...prevMessages, messageReçu]);
      });
    });

    setStompClient(client);
    return () => {
      client.disconnect();
    };
  }, []);

  useEffect(() => {
    async function fetchConversation() {
      try {
        
        const conversation = await getConversationById(idConversation);

        const userSender = conversation[0].userSender;
        const userReceiver = conversation[0].userReceiver;
        if (userConnected.idUser === userSender) {
          setReceiverId(userReceiver);
          setSenderId(userSender);
 
        } else if (userConnected.idUser === userReceiver) {
          setReceiverId(userSender);
          setSenderId(userReceiver)
        }
      } catch (error) {
        console.error(
          "Erreur lors de la récupération de la conversation",
          error
        );
      }
    }
    fetchConversation();
  }, [idConversation, userConnected.idUser]);

  //charge les messages déjà existant d'un conversation
  useEffect(() => {
    async function fetchMessages() {
      try {
        const messages = await getAllMessagesFromIdConversation(idConversation);
        setMessages(messages);

      } catch (error) {
        console.error(
          "Erreur lors de la récupération des messages de la conversation",
          error
        );
      }
    }
    fetchMessages();
  }, [idConversation]);

  //recup du pseudo du sender pour l'envoyer au receiver
  useEffect(() => {
    async function fetchSenderPseudo() {
      try {
        const senderResponse = await findUserById(userConnected.idUser);
        const senderPseudo = senderResponse.pseudo;
        setSenderPseudo(senderPseudo);
      } catch (error) {
        console.error("Erreur lors de la récupération du pseudo du sender", error);
      }
    }
    fetchSenderPseudo();
  }, [userConnected.idUser]);
  

  const sendMessage = (messageData) => {
    stompClient.send(
      "/app/sendMessage",
      {},
      JSON.stringify(messageData),
      () => {
        setNouveauMessage("");
      }
    );
  };

  const handleSendMessage = async () => {
    if (stompClient && stompClient.connected) {
      try {

        const messageData = {
          contain: nouveauMessage,
          userSender: senderId,
          userReceiver: receiverId,
          pseudo: senderPseudo,
          conversationId: idConversation,
        };

        const data = {
          contain: nouveauMessage,
          userSender: userConnected.idUser,
          userReceiver: receiverId,
          conversation: parseInt(idConversation)
        };

        sendMessage(messageData);
        //save en bdd
        await createMessage(data);

        setNouveauMessage("");
      } catch (error) {
        console.error(
          "Erreur lors de la récupération du pseudo du sender",
          error
        );
      }
    }
  };

  return (
    <div className={style.message}>
      <div className={style["message-bubbles"]}>
        {messages.map((message, index) => (
          <div
            key={index}
            className={`${style["message-bubble"]} ${
              (message.userSender.id === userConnected.idUser) || (message.userSender === userConnected.idUser)
                ? style.sender
                : style.receiver
            }`}
          > 
            <p>{message.pseudo}</p>
            <p>{message.contain}</p>
          </div>
        ))}
      </div>
      <div className={style["message-input"]}>
        <input
          type="text"
          value={nouveauMessage}
          onChange={(e) => setNouveauMessage(e.target.value)}
          placeholder="Saisissez votre message"
        />
        <button onClick={handleSendMessage}>Envoyer</button>
      </div>
      <FooterMobile />
    </div>
  );
};

export default Message;
