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

    @Column(name = "contain")
    private String contain;

    @Column(name = "date_hour")
    private Date dateHour;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_user_sender")
    private User userSender;

    @ManyToOne
    @JoinColumn(name = "id_user_receiver")
    private User userReceiver;

    @ManyToOne
    @JoinColumn(name = "id_conversation")
    private Conversation conversation;

    public Message() {
    }

    public Message(Integer idMessage, String contain, Date dateHour, User user, User userSender, User userReceiver,
            Conversation conversation) {
        this.idMessage = idMessage;
        this.contain = contain;
        this.dateHour = dateHour;
        this.user = user;
        this.userSender = userSender;
        this.userReceiver = userReceiver;
        this.conversation = conversation;
    }

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public User getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(User userReceiver) {
        this.userReceiver = userReceiver;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    
    
}
