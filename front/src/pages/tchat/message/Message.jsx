import React, { useEffect, useState } from "react";
import style from "./Message.module.scss";
import { useParams } from "react-router-dom";
import { getAllMessagesFromIdConversation } from "../../../apis/messages";
import FooterMobile from "../../../components/footer/FooterMobile";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { getToken } from "../../../data/Token";

const Message = ({ userConnected }) => {
  const { idConversation } = useParams();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const [websocketMessages, setWebsocketMessages] = useState([]);
  const [stompClient, setStompClient] = useState(null);

  useEffect(() => {
    // Fonction pour se connecter au WebSocket
    const connectToWebSocket = () => {
      const socket = new SockJS("http://localhost:8080/websocket/chat", null, {
        headers: {
          Authorization: `Bearer ${getToken()}`,
        },
      });
      const client = Stomp.over(socket);
      client.connect({}, function (frame) {
        console.log("Connected " + frame);
        setStompClient(client); // Stockez le client dans l'état
      });
    };

    // Lancer la connexion WebSocket lorsque le composant est monté
    connectToWebSocket();
  }, []);

  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const messages = await getAllMessagesFromIdConversation(idConversation);
        setMessages(messages);
      } catch (error) {
        console.error("Erreur lors de la récupération des messages :", error);
      }
    };

    fetchMessages();

    if (stompClient) {
      stompClient.subscribe("/topic/chat", function (message) {
        const newMessage = JSON.parse(message.body).contain;
        setWebsocketMessages((prevMessages) => [...prevMessages, newMessage]);
      });
    }
  }, [idConversation]);

  const sendMessage = () => {
    if (stompClient && stompClient.connected) {
      // Assurez-vous que la connexion est établie
      const messageData = {
        contain: newMessage,
        conversation: {
          idConversation: idConversation,
        },
        userSender: {
          id: messages[0].userSender.id,
        },
        userReceiver: {
          id: messages[0].userReceiver.id,
        },
      };

      stompClient.send(
        `/tchat/${idConversation}`,
        {},
        JSON.stringify(messageData.contain)
      );
      // Mettre à jour localement le tableau de messages avec le nouveau message
      const newMessageObject = {
        userSender: {
          pseudo: userConnected.pseudo,
        },
        contain: newMessage,
      };

      setMessages([...messages, newMessageObject]);
      setNewMessage("");
    } else {
      console.error("La connexion WebSocket n'est pas établie.");
    }
  };

  return (
    <div className={style.message}>
      {messages.map((message) => (
        <div
          key={message.idMessage}
          className={`${style["message-bubble"]} ${
            message.userSender.id === userConnected.idUser
              ? style.sender
              : style.receiver
          }`}
        >
          <p>{message.userSender.pseudo}</p>
          <p>{message.contain}</p>
        </div>
      ))}
      {newMessage && (
        <div className={`${style["message-bubble"]} ${style.sender}`}>
          <p>{userConnected.pseudo}</p>
          <p>{newMessage}</p>
        </div>
      )}
      <div className={style["message-input"]}>
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Saisissez votre message"
        />
        <button onClick={sendMessage}>Envoyer</button>
      </div>
      <FooterMobile />
    </div>
  );
};

export default Message;
