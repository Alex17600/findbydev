import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

let stompClient = null;

const connectToWebSocket = () => {
  const socket = new SockJS("http://localhost:8000/api/chat"); // Modifiez l'URL si nécessaire
  stompClient = Stomp.over(socket);
  stompClient.connect({}, function (frame) {
    console.log("Connected " + frame);
  });
};

export { connectToWebSocket, stompClient };

