import React, { useEffect, useState } from "react";
import style from "./Message.module.scss";
import { useParams } from "react-router-dom";
import { getAllMessagesFromIdConversation } from "../../../apis/messages";
import FooterMobile from "../../../components/footer/FooterMobile"; 
import SockJS from "sockjs-client"
import Stomp from 'stompjs'

const Message = ({ userConnected }) => {
  const { idConversation } = useParams();
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  let stompClient;
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
  }, [idConversation]);

  const sendMessage = () => {
    const socket = new SockJS("http://localhost:8000/api/chat");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
    console.log("Connected " + frame);
    stompClient.subscribe("http://localhost:8000/api/topic", function (greeting) {
    console.log("hi" + JSON.parse(greeting.body).content);
    });
    });
    };

    const sendSomething = () => {
    stompClient.send("http://localhost:8080/send-message",{}, JSON.stringify({"name":"John"}));
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
