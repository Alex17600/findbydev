package fr.findByDev.api.models;

import fr.findByDev.api.models.associations.LikeId;
import jakarta.persistence.*;

@Entity
@Table(name = "_like_")
public class Like {
    
    @EmbeddedId
    private LikeId idLike;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @MapsId("idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_experience")
    @MapsId("idExperience")
    private Experience experience;

    public Like() {
    }

    public LikeId getIdLike() {
        return idLike;
    }

    public void setIdLike(LikeId idLike) {
        this.idLike = idLike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }    
}
