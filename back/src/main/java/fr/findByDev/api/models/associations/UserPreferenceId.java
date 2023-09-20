package fr.findByDev.api.models.associations;

import java.io.Serializable;

public class UserPreferenceId implements Serializable{
    private Integer user;
    private Integer preference;
    public Integer getUser() {
        return user;
    }
    public void setUser(Integer user) {
        this.user = user;
    }
    public Integer getPreference() {
        return preference;
    }
    public void setPreference(Integer preference) {
        this.preference = preference;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((preference == null) ? 0 : preference.hashCode());
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
        UserPreferenceId other = (UserPreferenceId) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (preference == null) {
            if (other.preference != null)
                return false;
        } else if (!preference.equals(other.preference))
            return false;
        return true;
    }

    
}
