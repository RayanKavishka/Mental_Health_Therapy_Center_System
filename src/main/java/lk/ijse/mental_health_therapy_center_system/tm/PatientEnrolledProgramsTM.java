package lk.ijse.mental_health_therapy_center_system.tm;

public class PatientEnrolledProgramsTM {
    private int id;
    private int patientId;
    private String patientName;
    private Long enrolledPrograms;
    private String enrolledStatus;

    public PatientEnrolledProgramsTM() {
    }

    public PatientEnrolledProgramsTM(int id, int patientId, String patientName, Long enrolledPrograms, String enrolledStatus) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.enrolledPrograms = enrolledPrograms;
        this.enrolledStatus = enrolledStatus;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getEnrolledPrograms() {
        return enrolledPrograms;
    }

    public void setEnrolledPrograms(Long enrolledPrograms) {
        this.enrolledPrograms = enrolledPrograms;
    }

    public String getEnrolledStatus() {
        return enrolledStatus;
    }

    public void setEnrolledStatus(String enrolledStatus) {
        this.enrolledStatus = enrolledStatus;
    }
}
