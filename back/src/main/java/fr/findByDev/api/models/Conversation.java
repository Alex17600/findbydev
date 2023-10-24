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

    @Column(name = "user1")
    private Integer user1;

    @Column(name = "user2")
    private Integer user2;

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

    public Integer getUser1() {
        return user1;
    }

    public void setUser1(Integer user1) {
        this.user1 = user1;
    }

    public Integer getUser2() {
        return user2;
    }

    public void setUser2(Integer user2) {
        this.user2 = user2;
    }

    
}
