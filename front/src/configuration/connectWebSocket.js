import SockJS from 'sockjs-client'; // Pour la gestion WebSocket via SockJS
import Stomp from 'stompjs'; // Pour la gestion du protocole STOMP sur WebSocket


const connectWebSocket = (senderId) => {

    const socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);
  
    stompClient.connect({}, () => {

      const notificationTopic = `/topic/notifications/user/${senderId}`; 
      stompClient.subscribe(notificationTopic, (message) => {
        const notification = JSON.parse(message.body); 
        console.log('Notification reçue :', notification);
      });
    });
  };
  
  export default connectWebSocket;
  