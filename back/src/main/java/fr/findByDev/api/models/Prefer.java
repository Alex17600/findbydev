package fr.findByDev.api.models;

import fr.findByDev.api.models.associations.PreferId;
import jakarta.persistence.*;

@Entity
@Table(name = "prefer")
public class Prefer {
    
    @EmbeddedId
    private PreferId idPrefer;

    @ManyToOne
    @JoinColumn(name = "id_user")
    @MapsId("idUser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_technology")
    @MapsId("idTechnology")
    private Technology technology;

    public Prefer() {
    }

    public PreferId getIdPrefer() {
        return idPrefer;
    }

    public void setIdPrefer(PreferId idPrefer) {
        this.idPrefer = idPrefer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    
}
