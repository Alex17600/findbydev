package fr.findByDev.api.models.enums;

public enum Status {
    VALIDE("VALIDE"),
    EN_ATTENTE("EN_ATTENTE"),
    REFUSE("REFUSE");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Valeur non valide pour Status: " + value);
    }
}
