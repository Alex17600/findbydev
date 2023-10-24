import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

let stompClient = null;

const connectToWebSocket = () => {
    const socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    
    stompClient.connect({}, (frame) => {
        // La connexion WebSocket est établie avec succès
        console.log('Connected to WebSocket: ' + frame);

        // Ici, vous pouvez vous abonner à des topics WebSocket
        // Par exemple :
        stompClient.subscribe('/topic/queue', (message) => {
            // Traitez le message reçu depuis le topic
            console.log('Received message:', JSON.parse(message.body));
        });
    });
};

export { connectToWebSocket };

