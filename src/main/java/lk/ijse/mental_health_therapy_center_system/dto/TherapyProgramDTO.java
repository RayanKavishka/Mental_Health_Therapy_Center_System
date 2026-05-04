package lk.ijse.mental_health_therapy_center_system.dto;

import java.math.BigDecimal;

public class TherapyProgramDTO {
    private int id;
    private String name;
    private String duration;
    private BigDecimal fee;
    private String status;

    public TherapyProgramDTO() {}

    public TherapyProgramDTO(int id, String name, String duration, BigDecimal fee, String status) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
        this.status = status;
    }

    public TherapyProgramDTO(int id, String name, String duration, BigDecimal fee) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.fee = fee;
    }

    public TherapyProgramDTO(String name, String duration, BigDecimal fee) {
        this.name = name;
        this.duration = duration;
        this.fee = fee;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
