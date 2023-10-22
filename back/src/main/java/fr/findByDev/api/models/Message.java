package fr.findByDev.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name = "message")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Integer idMessage;

    @Column(name = "contain", length = 1000)
    private String contain;

    @Column(name = "date_hour")
    private Date dateHour;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
