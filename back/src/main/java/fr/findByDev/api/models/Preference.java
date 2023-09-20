package fr.findByDev.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "preference")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_preference")
    private Long id;

    @Column(name = "langage", length = 50, nullable = false)
    private String language;

    @Column(name = "framework", length = 50)
    private String framework;

    @Column(name = "experience", length = 50)
    private String experience;

    public Preference() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    
}
