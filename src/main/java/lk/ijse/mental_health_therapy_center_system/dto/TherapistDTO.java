package lk.ijse.mental_health_therapy_center_system.dto;

public class TherapistDTO {
    private int id;
    private String name;
    private String email;
    private String availability;
    private String status;

    public TherapistDTO() {}

    public TherapistDTO(int id, String name, String email, String availability, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.availability = availability;
        this.status = status;
    }

    public TherapistDTO(int id, String name, String email, String availability) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.availability = availability;
    }

    public TherapistDTO(String name, String email, String availability) {
        this.name = name;
        this.email = email;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
