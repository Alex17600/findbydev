package fr.findByDev.api.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Table(name = "message")
@Entity
public class Message {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_message")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "id_receiver")
    private User receiver;

    @Column(name = "contain", length = 1000)
    private String contain;

    @Column(name = "date_hour")
    private Timestamp dateHour;

    @ManyToOne
    @JoinColumn(name = "Id_conversation")
    private Conversation conversation;

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public Timestamp getDateHour() {
        return dateHour;
    }

    public void setDateHour(Timestamp dateHour) {
        this.dateHour = dateHour;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    
}
