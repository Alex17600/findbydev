package fr.findByDev.api.models.associations;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ConversationId implements Serializable{
    
    private Integer idUserSender;

    private Integer idUserReceiver;

    public ConversationId() {
    }

    public ConversationId(Integer idUserSender, Integer idUserReceiver) {
        this.idUserSender = idUserSender;
        this.idUserReceiver = idUserReceiver;
    }

    public Integer getIdUserSender() {
        return idUserSender;
    }

    public void setIdUserSender(Integer idUserSender) {
        this.idUserSender = idUserSender;
    }

    public Integer getIdUserReceiver() {
        return idUserReceiver;
    }

    public void setIdUserReceiver(Integer idUserReceiver) {
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
        ConversationId other = (ConversationId) obj;
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
