package fr.findByDev.api.models.enums;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Status {
    VALIDE,
    EN_ATTENTE,
    REFUSE
}
