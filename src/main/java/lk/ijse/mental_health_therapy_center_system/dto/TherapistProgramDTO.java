package lk.ijse.mental_health_therapy_center_system.dto;

public class TherapistProgramDTO {
    private int id;
    private int therapistId;
    private int therapyProgramId;

    public TherapistProgramDTO() {}

    public TherapistProgramDTO(int id, int therapistId, int therapyProgramId) {
        this.id = id;
        this.therapistId = therapistId;
        this.therapyProgramId = therapyProgramId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTherapistId() {
        return therapistId;
    }

    public void setTherapistId(int therapistId) {
        this.therapistId = therapistId;
    }

    public int getTherapyProgramId() {
        return therapyProgramId;
    }

    public void setTherapyProgramId(int therapyProgramId) {
        this.therapyProgramId = therapyProgramId;
    }
}
