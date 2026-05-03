package PatientApiV2.PatientApiV2.patient.data.entity;

import PatientApiV2.PatientApiV2.auth.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@PrimaryKeyJoinColumn(name = "users_id")

public class Patient extends User {

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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RendezVous> rendezVousList= new ArrayList<>();


    @PrePersist
    public void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

}
