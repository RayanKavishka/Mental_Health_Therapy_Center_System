package lk.ijse.mental_health_therapy_center_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "therapySession")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "therapySessionCache")
public class TherapySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Date sessionDate;

    @Column(nullable = false)
    private String timePeriod;

    @Column(nullable = false)
    private String status = "Scheduled";

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "therapistId")
    private Therapist therapist;

    @ManyToOne
    @JoinColumn(name = "therapyProgramId")
    private TherapyProgram therapyProgram;

    @OneToOne(mappedBy = "therapySession", cascade = CascadeType.ALL)
    private Payment payment;
}