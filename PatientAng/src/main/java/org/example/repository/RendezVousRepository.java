package org.example.repository;

import org.example.entity.RendezVous;

import java.time.LocalDate;
import java.util.List;

public interface RendezVousRepository {
    RendezVous insert(RendezVous rendezVous);
    List<RendezVous> selectAll();
    RendezVous findById(Long id);
    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByDate(LocalDate date);
    RendezVous update(RendezVous rendezVous);
    void delete(Long id);
}