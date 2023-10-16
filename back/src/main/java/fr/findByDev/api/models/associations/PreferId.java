package fr.findByDev.api.models.associations;

import java.io.Serializable;

import jakarta.persistence.*;

@Embeddable
public class PreferId implements Serializable {
    private Integer idUser;
    private Integer idTechnology;
    
    public PreferId() {
    }

    public PreferId(Integer idUser, Integer idTechnology) {
        this.idUser = idUser;
        this.idTechnology = idTechnology;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdTechnology() {
        return idTechnology;
    }

    public void setIdTechnology(Integer idTechnology) {
        this.idTechnology = idTechnology;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
        result = prime * result + ((idTechnology == null) ? 0 : idTechnology.hashCode());
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
        PreferId other = (PreferId) obj;
        if (idUser == null) {
            if (other.idUser != null)
                return false;
        } else if (!idUser.equals(other.idUser))
            return false;
        if (idTechnology == null) {
            if (other.idTechnology != null)
                return false;
        } else if (!idTechnology.equals(other.idTechnology))
            return false;
        return true;
    }   
}
