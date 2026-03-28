package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "demandes_rdv")

public class DemandeRDV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private Specialite specialite;

    //Many to one ( Demande -> Patient
    @ManyToOne
    @JoinColumn(name = "patients_id")
    Patient patient;


}
