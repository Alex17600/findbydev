package fr.findByDev.api.models.associations;

import fr.findByDev.api.models.Preference;
import fr.findByDev.api.models.User;
import jakarta.persistence.*;

@Entity
@Table(name = "user_preference")
@IdClass(UserPreferenceId.class)
public class UserPreference {
    @Id
    @JoinColumn(name = "id_user")
    private Integer user;

    @Id
    @JoinColumn(name = "id_preference")
    private Integer preference;

    @ManyToOne
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    private User userEntity;

    @ManyToOne
    @JoinColumn(name = "id_preference", insertable = false, updatable = false)
    private Preference preferenceEntity;

    public UserPreference() {
    }

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

    public User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }

    public Preference getPreferenceEntity() {
        return preferenceEntity;
    }

    public void setPreferenceEntity(Preference preferenceEntity) {
        this.preferenceEntity = preferenceEntity;
    }

    
}
