package fr.findByDev.api.models;


import java.sql.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "conversation")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
