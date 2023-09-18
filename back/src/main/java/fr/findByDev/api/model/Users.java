package fr.findByDev.api.model;

import jakarta.persistence.*;

@Table(name = "user")
@Entity

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "town")
    private String town;

    @Column(name= "birthday")
    private String birthday;

    @Column(name = "gender", columnDefinition = "gender_enum")
    private String gender;

    @Column(name ="mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "activeAccount")
    private Boolean activeAccount;

    @Column(name = "description")
    private String description;

    @Column(name = "popularity")
    private Integer popularity;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "gitProfile")
    private String gitProfile;
}
