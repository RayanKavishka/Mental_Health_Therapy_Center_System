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
@Table(name = "user")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "userCache")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String userName;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}