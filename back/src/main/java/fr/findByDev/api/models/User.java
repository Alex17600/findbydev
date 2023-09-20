package fr.findByDev.api.models;

import jakarta.persistence.*;
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_user")
    private Integer id;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Column(name = "town", length = 50, nullable = false)
    private String town;

    @Column(name = "birthday", length = 50, nullable = false)
    private String birthday;

    @Column(name = "mail", length = 75, nullable = false)
    private String mail;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "activeAccount", nullable = false)
    private boolean activeAccount;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "popularity_")
    private Integer popularity;

    @Column(name = "photo", length = 50, nullable = false)
    private byte[] photo;

    @Column(name = "gitProfile", length = 50)
    private String gitProfile;

    @ManyToOne
    @JoinColumn(name = "Id_genders", nullable = false)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "Id_roles", nullable = false)
    private Role role;

    public User() {
    }

    public User(Integer id, String lastname, String firstname, String town, String birthday, String mail,
            String password, boolean activeAccount, String description, Integer popularity, byte[] photo,
            String gitProfile, Gender gender, Role role) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.town = town;
        this.birthday = birthday;
        this.mail = mail;
        this.password = password;
        this.activeAccount = activeAccount;
        this.description = description;
        this.popularity = popularity;
        this.photo = photo;
        this.gitProfile = gitProfile;
        this.gender = gender;
        this.role = role;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(boolean activeAccount) {
        this.activeAccount = activeAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getGitProfile() {
        return gitProfile;
    }

    public void setGitProfile(String gitProfile) {
        this.gitProfile = gitProfile;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

