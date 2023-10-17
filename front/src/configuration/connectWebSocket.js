import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

const connectWebSocket = () => {
    const socket = new SockJS('/websocket'); 
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        console.log('Connecté à WebSocket');

        // Associez le nom d'utilisateur (le pseudo du sender) à sa session WebSocket
        stompClient.subscribe(`/user/${senderPseudo}/queue/notifications`, (message) => {
            // Traitez les notifications ici
            console.log('Notification reçue:', message);
        });
    });

    return stompClient;
};

export default connectWebSocket;