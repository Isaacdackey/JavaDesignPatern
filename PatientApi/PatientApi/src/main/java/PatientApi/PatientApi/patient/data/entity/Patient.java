package PatientApi.PatientApi.patient.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "patients")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(unique = true , updatable = false)
    private String numero;
    private String nom;
    private String prenom;
    private String adresse;
    @Column(unique = true)
    private String telephone;
    @Column(nullable = false ,columnDefinition = "TEXT")
    private String antecedents;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

}
