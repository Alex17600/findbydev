package fr.findByDev.api.models;

import fr.findByDev.api.models.associations.RealiseId;
import jakarta.persistence.*;

@Entity
@Table(name = "realise")
public class Realise {
    
    @EmbeddedId
    private RealiseId idRealise;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @MapsId("idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_experience")
    @MapsId("idExperience")
    private Experience experience;

    public Realise() {
    }

    public RealiseId getIdRealise() {
        return idRealise;
    }

    public void setIdRealise(RealiseId idRealise) {
        this.idRealise = idRealise;
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
