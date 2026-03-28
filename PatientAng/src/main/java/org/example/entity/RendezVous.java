package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rendez_vous")
public class RendezVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_rdv", nullable = false)
    private LocalDate date;

    @Column(name = "heure_rdv", nullable = false)
    private LocalTime heure;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    @Enumerated(EnumType.STRING)
    private StatutRDV statut = StatutRDV.EN_ATTENTE;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "demande_id")
    private DemandeRDV demandeRDV;
}