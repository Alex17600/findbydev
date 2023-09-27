package fr.findByDev.api.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;

import fr.findByDev.api.models.associations.MatchId;
import fr.findByDev.api.models.enums.Status;
import fr.findByDev.api.models.views.View;
import jakarta.persistence.*;

@Entity
@Table(name = "_match_")
public class Match {

    @EmbeddedId
    private MatchId idMatch;

    @ManyToOne
    @JsonView(View.Match.class)
    @JoinColumn(name = "Id_user_receiver")
    @MapsId("idUserReceiver")
    private User userReceiver;

    @ManyToOne
    @JsonView(View.Match.class)
    @JoinColumn(name = "Id_user_sender")
    @MapsId("idUserSender")
    private User userSender;


    @Column(name = "date_hour")
    private Date dateHour;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private Status currentStatus;

    public Match() {
    }

    public MatchId getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(MatchId idMatch) {
        this.idMatch = idMatch;
    }

    public User getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(User userReceiver) {
        this.userReceiver = userReceiver;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public Date getDateHour() {
        return dateHour;
    }

    public void setDateHour(Date dateHour) {
        this.dateHour = dateHour;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }


}
