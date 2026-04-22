package lk.ijse.mental_health_therapy_center_system.dto;

public class TherapistDTO {
    private int id;
    private String name;
    private String availability;

    public TherapistDTO() {}

    public TherapistDTO(int id, String name, String availability) {
        this.id = id;
        this.name = name;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
