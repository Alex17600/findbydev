package fr.findByDev.api.models.associations;

import fr.findByDev.api.models.Match;
import fr.findByDev.api.models.User;
import jakarta.persistence.*;
@Entity
@Table(name = "participate")
@IdClass(ParticipateId.class)
public class Participate {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_matche")
    private Match match;

    public Participate() {
    }

    public Participate(User user, Match match) {
        this.user = user;
        this.match = match;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
