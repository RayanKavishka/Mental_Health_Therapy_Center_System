package lk.ijse.mental_health_therapy_center_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "therapist_program")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "therapistProgramCache")
public class TherapistProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "therapistId")
    private Therapist therapist;

    @ManyToOne
    @JoinColumn(name = "therapyProgramId")
    private TherapyProgram therapyProgram;
}