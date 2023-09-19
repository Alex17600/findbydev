package fr.findByDev.api.models;

import jakarta.persistence.*;

@Table(name = "user")
@Entity

public class User {
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

    public User() {
    }

    public User(Integer idUser, String lastName, String firstName, String town, String birthday, String gender,
            String mail, String password, Boolean activeAccount, String description, Integer popularity, byte[] photo,
            String gitProfile) {
        this.idUser = idUser;
        this.lastName = lastName;
        this.firstName = firstName;
        this.town = town;
        this.birthday = birthday;
        this.gender = gender;
        this.mail = mail;
        this.password = password;
        this.activeAccount = activeAccount;
        this.description = description;
        this.popularity = popularity;
        this.photo = photo;
        this.gitProfile = gitProfile;
    }



    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public Boolean getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Boolean activeAccount) {
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
}
