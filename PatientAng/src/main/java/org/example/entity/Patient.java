package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;
    @Column (name = "nom_prenom")
    private String nomComplet;
    @Column (unique = true)
    private String tel;
    @Column (unique = true , nullable = false)
    private String numero;

    //One to Many ( Patient -> plusieurs demandes )
    @OneToMany(mappedBy = "patient")
    List<DemandeRDV> demandesRDV;

}
