package fr.findByDev.api.models.associations;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class LikeId implements Serializable {
    private Integer idUser;
    private Integer idExperience;
    
    public LikeId() {
    }

    public LikeId(Integer idUser, Integer idExperience) {
        this.idUser = idUser;
        this.idExperience = idExperience;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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
        result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
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
        LikeId other = (LikeId) obj;
        if (idUser == null) {
            if (other.idUser != null)
                return false;
        } else if (!idUser.equals(other.idUser))
            return false;
        if (idExperience == null) {
            if (other.idExperience != null)
                return false;
        } else if (!idExperience.equals(other.idExperience))
            return false;
        return true;
    }

    
}
