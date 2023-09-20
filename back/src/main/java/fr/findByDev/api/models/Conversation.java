package fr.findByDev.api.models;

import jakarta.persistence.*;

@Table(name = "conversation")
@Entity
public class Conversation  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_conversation")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Id_user")
    private User user;

    public Conversation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
