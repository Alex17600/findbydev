package fr.findByDev.api.models;

import java.sql.Timestamp;

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
    @JoinColumn(name = "id_user_receiver")
    @MapsId("idUserReceiver")
    private User receiver;

    @ManyToOne
    @JsonView(View.Match.class)
    @JoinColumn(name = "id_user_sender")
    @MapsId("idUserSender")
    private User sender;

    @Column(name = "date_hour")
    private Timestamp dateHour;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private Status currentStatus;

    @Column(name = "is_read")
    private Boolean isRead;

    public Match() {
    }

    public MatchId getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(MatchId idMatch) {
        this.idMatch = idMatch;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Timestamp getDateHour() {
        return dateHour;
    }

    public void setDateHour(Timestamp dateHour) {
        this.dateHour = dateHour;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}
