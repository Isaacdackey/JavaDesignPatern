package com.PatientSpring.patient_spring.services;

import com.PatientSpring.patient_spring.entity.RendezVous;

import java.time.LocalDate;
import java.util.List;

public interface RendezVousService {
    void creerRendezVous(RendezVous rendezVous);
    List<RendezVous> listerRendezVous();
    List<RendezVous> listerRendezVousParPatient(Long patientId);
    List<RendezVous> listerRendezVousParDate(LocalDate date);
    void annulerRendezVous(Long id);
    void confirmerRendezVous(Long id);
}