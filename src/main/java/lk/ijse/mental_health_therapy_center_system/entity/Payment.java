package lk.ijse.mental_health_therapy_center_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "payment")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "paymentCache")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Date date;

    private String status = "Pending";

    @OneToOne
    @JoinColumn(name = "patientProgramId")
    private PatientProgram patientProgram;

    @OneToOne
    @JoinColumn(name = "therapySessionId")
    private TherapySession therapySession;
}