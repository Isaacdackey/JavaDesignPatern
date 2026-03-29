package com.PatientSpring.patient_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Changez long en Long

    @Column(name = "nom_prenom")
    private String nomComplet;

    @Column(unique = true)
    private String tel;

    @Column(unique = true, nullable = false)
    private String numero;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    List<DemandeRDV> demandesRDV;
}