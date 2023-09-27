package fr.findByDev.api.models;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "message")
@Entity
public class Message {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Integer idMessage;

    @ManyToOne
    @JoinColumn(name = "id_sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "id_receiver")
    private User receiver;

    @Column(name = "contain", length = 1000)
    private String contain;

    @Column(name = "date_hour")
    private Date dateHour;

    @ManyToOne
    @JoinColumn(name = "id_conversation")
    private Conversation conversation;

    public Message() {
    }

    public Integer getId() {
        return idMessage;
    }

    public void setId(Integer idMessage) {
        this.idMessage = idMessage;
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

    public Date getDateHour() {
        return dateHour;
    }

    public void setDateHour(Date dateHour) {
        this.dateHour = dateHour;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
