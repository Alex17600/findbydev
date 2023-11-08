package fr.findByDev.api.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import fr.findByDev.api.models.views.View;
import jakarta.persistence.*;
@Entity
@Table(name = "_user_")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    @JsonView(View.Match.class)
    @Column(name = "pseudo")
    private String pseudo;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "firstname" )
    private String firstName;

    @Column(name = "town")
    private String town;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "mail")
    private String mail;

    @Column(name = "password")
    private String password;

    @Column(name = "active_account")
    private boolean activeAccount;

    @Column(name = "description")
    private String description;

    @Column(name = "popularity")
    private Integer popularity;

    @Column(name = "photo")
    private String photo;

    @Column(name = "git_profile")
    private String gitProfile;

    @Column(name = "type")
    private String type;
    
    @ManyToOne
    @JoinColumn(name = "id_gender")
    private Gender gender;

    @OneToMany(mappedBy = "user")
    private List<GitProject> gitProjets;


    public User() {
    }

    public User(Integer idUser, String pseudo, String lastName, String firstName, String town, Date birthday,
            String mail, String password, boolean activeAccount, String description, Integer popularity, String photo,
            String gitProfile, String type, Gender gender, List<GitProject> gitProjets) {
        this.idUser = idUser;
        this.pseudo = pseudo;
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
        this.gitProjets = gitProjets;
    }





    public Integer getId() {
        return idUser;
    }

    public void setId(Integer idUser) {
        this.idUser = idUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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

    public List<GitProject> getGitProjets() {
        return gitProjets;
    }

    public void setGitProjets(List<GitProject> gitProjets) {
        this.gitProjets = gitProjets;
    }

    
}

