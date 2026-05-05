package lk.ijse.mental_health_therapy_center_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "therapist")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "therapistCache")
public class Therapist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 200, nullable = false)
    private String email;

    @Column(nullable = false)
    private String availability;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "therapist", cascade = CascadeType.ALL)
    private List<TherapistProgram> therapistPrograms = new ArrayList<>();

    @OneToMany(mappedBy = "therapist")
    private List<TherapySession> therapySessions = new ArrayList<>();
}