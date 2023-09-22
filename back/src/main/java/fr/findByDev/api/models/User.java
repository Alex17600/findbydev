package fr.findByDev.api.models;

import jakarta.persistence.*;
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_user")
    private Integer idUser;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastName;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstName;

    @Column(name = "town", length = 50, nullable = false)
    private String town;

    @Column(name = "birthday", length = 50, nullable = false)
    private String birthday;

    @Column(name = "mail", length = 75, nullable = false)
    private String mail;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "activeaccount", nullable = false)
    private boolean activeAccount;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "popularity")
    private Integer popularity;

    @Column(name = "photo", length = 50, nullable = false)
    private byte[] photo;

    @Column(name = "gitprofile", length = 50)
    private String gitProfile;

    @ManyToOne
    @JoinColumn(name = "id_gender", nullable = false)
    private Gender gender;

    @Column(name = "type", length = 50)
    private String type;

    public User() {
    }

    public User(Integer idUser, String lastName, String firstName, String town, String birthday, String mail,
            String password, boolean activeAccount, String description, Integer popularity, byte[] photo,
            String gitProfile, String type, Gender gender) {
        this.idUser = idUser;
        this.lastName = lastName;
        this.firstName = firstName;
        this.town = town;
        this.birthday = birthday;
        this.mail = mail;
        this.password = password;
        this.activeAccount = activeAccount;
        this.description = description;
        this.popularity = popularity;
        this.photo = photo;
        this.gitProfile = gitProfile;
        this.type = type;
        this.gender = gender;
    }




    public Integer getId() {
        return idUser;
    }

    public void setId(Integer idUser) {
        this.idUser = idUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

