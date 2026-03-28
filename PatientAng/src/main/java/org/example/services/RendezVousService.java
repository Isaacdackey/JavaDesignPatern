package org.example.services;

import org.example.entity.RendezVous;
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