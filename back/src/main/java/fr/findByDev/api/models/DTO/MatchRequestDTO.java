package fr.findByDev.api.models.DTO;

public class MatchRequestDTO {
    private Integer userSender;
    private Integer userReceiver;
    
    public Integer getUserSender() {
        return userSender;
    }
    public void setUserSender(Integer userSender) {
        this.userSender = userSender;
    }
    public Integer getUserReceiver() {
        return userReceiver;
    }
    public void setUserReceiver(Integer userReceiver) {
        this.userReceiver = userReceiver;
    }   
}
