package fr.findByDev.api.models;


import java.util.Date;

import jakarta.persistence.*;

@Table(name = "conversation")
@Entity
public class Conversation  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_conversation")
    private Integer id;

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "archived")
    private Boolean archived;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Conversation() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }    
}
