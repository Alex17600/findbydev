package fr.findByDev.api.models;

import fr.findByDev.api.models.associations.ConcerneId;
import jakarta.persistence.*;

@Entity
@Table(name = "concerne")
public class Concerne {
    
    @EmbeddedId
    private ConcerneId idConcerne;

    @ManyToOne
    @JoinColumn(name = "id_experience")
    @MapsId("idExperience")
    private Experience experience;

    @ManyToOne
    @JoinColumn(name = "id_technology")
    @MapsId("idTechnology")
    private Technology technology;

    public Concerne() {
    }

    public ConcerneId getIdConcerne() {
        return idConcerne;
    }

    public void setIdConcerne(ConcerneId idConcerne) {
        this.idConcerne = idConcerne;
    }


    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public Experience getExperience() {
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = experience;
    }    
}
