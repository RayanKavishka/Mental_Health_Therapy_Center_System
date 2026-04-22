package lk.ijse.mental_health_therapy_center_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.engine.internal.Cascade;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "patient_program")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "patientProgramCache")
public class PatientProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "therapyProgramId")
    private TherapyProgram therapyProgram;

    @OneToOne(mappedBy = "patientProgram", cascade = CascadeType.ALL)
    private Payment payment;
}