package lk.ijse.mental_health_therapy_center_system.dto;

import java.util.Date;

public class PaymentDTO {
    private int id;
    private String name;
    private double amount;
    private Date date;
    private String status;

    private int patientProgramId;
    private int therapySessionId;

    public PaymentDTO() {}

    public PaymentDTO(int id, String name, double amount, Date date, String status,
                      int patientProgramId, int therapySessionId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.patientProgramId = patientProgramId;
        this.therapySessionId = therapySessionId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPatientProgramId() {
        return patientProgramId;
    }

    public void setPatientProgramId(int patientProgramId) {
        this.patientProgramId = patientProgramId;
    }

    public int getTherapySessionId() {
        return therapySessionId;
    }

    public void setTherapySessionId(int therapySessionId) {
        this.therapySessionId = therapySessionId;
    }
}
