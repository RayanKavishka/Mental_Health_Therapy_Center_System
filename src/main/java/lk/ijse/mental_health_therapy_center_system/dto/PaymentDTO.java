package lk.ijse.mental_health_therapy_center_system.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDTO {
    private int id;
    private String name;
    private int patientId;
    private BigDecimal paidAmount;
    private LocalDate date;
    private BigDecimal pendingAmount;
    private String status;

    public PaymentDTO() {}

    public PaymentDTO(int id, String name, int patientId, BigDecimal paidAmount, LocalDate date, BigDecimal pendingAmount, String status) {
        this.id = id;
        this.name = name;
        this.patientId = patientId;
        this.paidAmount = paidAmount;
        this.date = date;
        this.pendingAmount = pendingAmount;
        this.status = status;
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

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(BigDecimal pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
