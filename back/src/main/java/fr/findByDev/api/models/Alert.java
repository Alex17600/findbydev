package fr.findByDev.api.models;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "alerts")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_alert")
    private Integer id;

    @Column(name = "id_reported_user")
    private Integer reportedUserId;

    @Column(name = "reason", length = 100, nullable = false)
    private String reason;

    @Column(name = "date_alert")
    private Timestamp dateAlert;

    @ManyToOne
    @JoinColumn(name = "Id_user")
    private User user;

    public Alert() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Integer reportedUserId) {
        this.reportedUserId = reportedUserId;
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
