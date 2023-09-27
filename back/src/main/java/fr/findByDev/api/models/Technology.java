package fr.findByDev.api.models;

import jakarta.persistence.*;

@Table(name = "technology")
@Entity
public class Technology {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_technology")
    private Integer idTechnology;

    @Column(name = "name")
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    public Technology() {
    }

    public Integer getIdTechnology() {
        return idTechnology;
    }

    public void setIdTechnology(Integer idTechnology) {
        this.idTechnology = idTechnology;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
