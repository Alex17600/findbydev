import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { getToken } from "../data/Token";
let stompClient = null;

const connectToWebSocket = () => {
  const socket = new SockJS("http://localhost:8080/websocket/chat", null, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  });
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    console.log("Connected " + frame);
  });
};

const sendMessageToWebSocket = (data) => {
  console.log(data);
  if (!stompClient) {
    connectToWebSocket();
  }

  stompClient.send(
    "/app/chat",
    {},
    JSON.stringify(data)
  );
};

export { connectToWebSocket, sendMessageToWebSocket  };

