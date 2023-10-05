package fr.findByDev.api.models.associations;

import java.io.Serializable;

import jakarta.persistence.*;


@Embeddable
public class MatchId implements Serializable {


    private Integer idUserSender;

    private Integer idUserReceiver;

    public MatchId() {
    }

    public MatchId(Integer idUserSender, Integer idUserReceiver) {
        this.idUserSender = idUserSender;
        this.idUserReceiver = idUserReceiver;
    }


    public Integer getIdUserReceveir() {
        return idUserSender;
    }

    public void setIdUserReceveir(Integer idUserSender) {
        this.idUserSender = idUserSender;
    }

    public Integer getidUserReceiver() {
        return idUserReceiver;
    }

    public void setidUserReceiver(Integer idUserReceiver) {
        this.idUserReceiver = idUserReceiver;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idUserSender == null) ? 0 : idUserSender.hashCode());
        result = prime * result + ((idUserReceiver == null) ? 0 : idUserReceiver.hashCode());
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
        MatchId other = (MatchId) obj;
        if (idUserSender == null) {
            if (other.idUserSender != null)
                return false;
        } else if (!idUserSender.equals(other.idUserSender))
            return false;
        if (idUserReceiver == null) {
            if (other.idUserReceiver != null)
                return false;
        } else if (!idUserReceiver.equals(other.idUserReceiver))
            return false;
        return true;
    }

}
