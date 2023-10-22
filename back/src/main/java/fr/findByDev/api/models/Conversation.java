package fr.findByDev.api.models;


import java.util.Date;

import fr.findByDev.api.models.associations.ConversationId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Table(name = "conversation")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {

    @EmbeddedId
    private ConversationId idConversation;

    @ManyToOne
    @JoinColumn(name = "Id_user_sender") 
    @MapsId("idUserSender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "Id_user_receiver")
    @MapsId("idUserReceiver")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "id_message")
    private Message message;

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "archived")
    private Boolean archived;
}
