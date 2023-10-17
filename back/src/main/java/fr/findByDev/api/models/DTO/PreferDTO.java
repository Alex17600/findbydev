package fr.findByDev.api.models.DTO;

import java.util.List;

public class PreferDTO {
    private Integer idUser;
    private List<Integer> idTechnologys;

    public Integer getIdUser() {
        return idUser;
    }
    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
    public List<Integer> getIdTechnologys() {
        return idTechnologys;
    }
    public void setIdTechnologys(List<Integer> idTechnologys) {
        this.idTechnologys = idTechnologys;
    }
    
}
