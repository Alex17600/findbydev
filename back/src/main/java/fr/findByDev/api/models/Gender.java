package fr.findByDev.api.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import fr.findByDev.api.models.views.View;
import jakarta.persistence.*;


@Entity
@Table(name = "genders")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Gender.class)
    @Column(name = "id_gender")
    private Integer idGender;

    @JsonView({View.Gender.class})
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "gender")
    @JsonIgnore
    private List<User> users;

    public Gender() {
    }

    public Gender(Integer idGender, String name, List<User> users) {
        this.idGender = idGender;
        this.name = name;
        this.users = users;
    }

    public Gender(String name) {
        this.name = name;
    }

    public Integer getId() {
        return idGender;
    }

    public void setId(Integer idGender) {
        this.idGender = idGender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }  
}
