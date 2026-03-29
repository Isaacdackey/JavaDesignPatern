package com.PatientSpring.patient_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "demandes_rdv")
public class DemandeRDV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Changez int en Long

    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    @ManyToOne
    @JoinColumn(name = "patients_id")
    private Patient patient;
}