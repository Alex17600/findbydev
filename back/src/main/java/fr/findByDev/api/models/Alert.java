package fr.findByDev.api.models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "alert")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_alert")
    private Integer idAlert;

    @Column(name = "id_reported_user")
    private Integer idReportedUser;

    @Column(name = "reason")
    private String reason;

    @Column(name = "date_alert")
    private Timestamp dateAlert;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Alert() {
    }

    public Integer getId() {
        return idAlert;
    }

    public void setId(Integer idAlert) {
        this.idAlert = idAlert;
    }

    public Integer getReportedUserId() {
        return idReportedUser;
    }

    public void setReportedUserId(Integer idReportedUser) {
        this.idReportedUser = idReportedUser;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getDateAlert() {
        return dateAlert;
    }

    public void setDateAlert(Timestamp dateAlert) {
        this.dateAlert = dateAlert;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
