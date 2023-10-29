package fr.findByDev.api.models;


import java.sql.Date;

import jakarta.persistence.*;

@Table(name = "conversation")
@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "id_conversation")
    private Integer idConversation;

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "archived")
    private Boolean archived;

    @Column(name = "user_sender")
    private Integer userSender;

    @Column(name = "user_receiver")
    private Integer userReceiver;

    public Conversation() {
    }

    public Integer getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(Integer idConversation) {
        this.idConversation = idConversation;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public Integer getUserSender() {
        return userSender;
    }

    public void setUserSender(Integer userSender) {
        this.userSender = userSender;
    }

    public Integer getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(Integer userReceiver) {
        this.userReceiver = userReceiver;
    }


}
