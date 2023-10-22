import React, { useEffect, useState } from 'react';
import style from "./Message.module.scss";
import { getConversationsForLoggedInUser } from '../../../apis/conversation';

const Message = ({userConnected }) => {
    const [conversations, setConversations] = useState([]);

    useEffect(() => {
        const fetchAllConversation = async () => {

            try {
              const data = await getConversationsForLoggedInUser(userConnected.idUser);
                setConversations(data);
                console.log(conversations);
            } catch (error) {
              console.error(
                "Erreur lors de la récupération des conversations :",
                error
              );
            }
        };
    
        fetchAllConversation();
      }, []);

    return (
        <div className={style.message}>
            <div className={style.messageBox}>
                test
            </div>
        </div>
    );
};

export default Message;