package lk.ijse.mental_health_therapy_center_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "therapist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "therapistCache")
public class Therapist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    private String availability = "Available";

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL)
    private List<TherapistProgram> therapistPrograms;

    @OneToMany(mappedBy = "therapist")
    private List<TherapySession> therapySessions;
}