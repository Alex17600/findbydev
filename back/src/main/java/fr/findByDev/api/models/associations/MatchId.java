package fr.findByDev.api.models.associations;

import java.io.Serializable;

import jakarta.persistence.*;


@Embeddable
public class MatchId implements Serializable {
    private Integer idUserReceiver;
    private Integer idUserSender;

    public MatchId() {
    }

    public MatchId(Integer idUserReceiver, Integer idUserSender) {
        this.idUserReceiver = idUserReceiver;
        this.idUserSender = idUserSender;
    }


    public Integer getIdUserReceveir() {
        return idUserReceiver;
    }

    public void setIdUserReceveir(Integer idUserReceiver) {
        this.idUserReceiver = idUserReceiver;
    }

    public Integer getIdUserSender() {
        return idUserSender;
    }

    public void setIdUserSender(Integer idUserSender) {
        this.idUserSender = idUserSender;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idUserReceiver == null) ? 0 : idUserReceiver.hashCode());
        result = prime * result + ((idUserSender == null) ? 0 : idUserSender.hashCode());
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
        if (idUserReceiver == null) {
            if (other.idUserReceiver != null)
                return false;
        } else if (!idUserReceiver.equals(other.idUserReceiver))
            return false;
        if (idUserSender == null) {
            if (other.idUserSender != null)
                return false;
        } else if (!idUserSender.equals(other.idUserSender))
            return false;
        return true;
    }

}
