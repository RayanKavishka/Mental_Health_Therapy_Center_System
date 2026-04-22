package lk.ijse.mental_health_therapy_center_system.dto;

import java.util.Date;

public class TherapySessionDTO {
    private int id;
    private Date sessionDate;
    private String timePeriod;
    private String status;

    private int patientId;
    private int therapistId;
    private int therapyProgramId;

    public TherapySessionDTO() {}

    public TherapySessionDTO(int id, Date sessionDate, String timePeriod, String status,
                             int patientId, int therapistId, int therapyProgramId) {
        this.id = id;
        this.sessionDate = sessionDate;
        this.timePeriod = timePeriod;
        this.status = status;
        this.patientId = patientId;
        this.therapistId = therapistId;
        this.therapyProgramId = therapyProgramId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
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
