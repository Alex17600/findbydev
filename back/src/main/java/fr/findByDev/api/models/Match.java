package fr.findByDev.api.models;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Table(name = "matches")
@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matche")
    private Integer idMatch;

    @Column(name = "dateHour")
    private Timestamp dateHour;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2")
    private User user2;

    public Match() {
    }

    public Integer getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(Integer idMatch) {
        this.idMatch = idMatch;
    }

    public Timestamp getDateHour() {
        return dateHour;
    }

    public void setDateHour(Timestamp dateHour) {
        this.dateHour = dateHour;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
