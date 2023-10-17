import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';

const connectWebSocket = (senderPseudo, onMessageReceived, onError) => {
    const socket = new SockJS("/app/notice");
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {
        console.log('Connecté à WebSocket');

        // Associez le nom d'utilisateur (le pseudo du sender) à sa session WebSocket
        stompClient.subscribe(`/app/notice/${senderPseudo}/queue/notifications`, (message) => {
            // Traitez les notifications ici
            console.log('Notification reçue:', message);

            if (onMessageReceived) {
                onMessageReceived(JSON.parse(message.body));
            }
        });
    }, (error) => {
        console.error('Erreur de connexion à WebSocket :', error);
        if (onError) {
            onError(error);
        }
    });

    // Gestion de la déconnexion
    stompClient.ws.onclose = () => {
        console.log('Déconnexion de WebSocket');
       
    };

    return stompClient;
};

export default connectWebSocket;