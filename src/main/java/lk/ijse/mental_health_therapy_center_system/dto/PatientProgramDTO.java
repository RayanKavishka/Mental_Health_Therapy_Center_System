package lk.ijse.mental_health_therapy_center_system.dto;

public class PatientProgramDTO {
    private int id;
    private int patientId;
    private int therapyProgramId;

    public PatientProgramDTO() {}

    public PatientProgramDTO(int id, int patientId, int therapyProgramId) {
        this.id = id;
        this.patientId = patientId;
        this.therapyProgramId = therapyProgramId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getTherapyProgramId() {
        return therapyProgramId;
    }

    public void setTherapyProgramId(int therapyProgramId) {
        this.therapyProgramId = therapyProgramId;
    }
}
