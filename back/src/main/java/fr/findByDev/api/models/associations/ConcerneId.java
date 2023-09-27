package fr.findByDev.api.models.associations;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class ConcerneId implements Serializable {
    private Integer idTechnology;
    private Integer idExperience;
    
    public ConcerneId() {
    }

    public ConcerneId(Integer idTechnology, Integer idExperience) {
        this.idTechnology = idTechnology;
        this.idExperience = idExperience;
    }

    public Integer getIdTechnology() {
        return idTechnology;
    }

    public void setIdTechnology(Integer idTechnology) {
        this.idTechnology = idTechnology;
    }

    public Integer getIdExperience() {
        return idExperience;
    }

    public void setIdExperience(Integer idExperience) {
        this.idExperience = idExperience;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idTechnology == null) ? 0 : idTechnology.hashCode());
        result = prime * result + ((idExperience == null) ? 0 : idExperience.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ConcerneId other = (ConcerneId) obj;
        if (idTechnology == null) {
            if (other.idTechnology != null)
                return false;
        } else if (!idTechnology.equals(other.idTechnology))
            return false;
        if (idExperience == null) {
            if (other.idExperience != null)
                return false;
        } else if (!idExperience.equals(other.idExperience))
            return false;
        return true;
    }

    
    

}
