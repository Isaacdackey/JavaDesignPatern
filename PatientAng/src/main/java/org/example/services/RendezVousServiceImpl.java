package org.example.services;

import org.example.entity.RendezVous;
import org.example.entity.StatutRDV;
import org.example.repository.RendezVousRepository;
import java.time.LocalDate;
import java.util.List;

public class RendezVousServiceImpl implements RendezVousService {

    private RendezVousRepository rendezVousRepository;

    public RendezVousServiceImpl(RendezVousRepository rendezVousRepository) {
        this.rendezVousRepository = rendezVousRepository;
    }

    @Override
    public void creerRendezVous(RendezVous rendezVous) {
        rendezVousRepository.insert(rendezVous);
    }

    @Override
    public List<RendezVous> listerRendezVous() {
        return rendezVousRepository.selectAll();
    }

    @Override
    public List<RendezVous> listerRendezVousParPatient(Long patientId) {
        return rendezVousRepository.findByPatientId(patientId);
    }

    @Override
    public List<RendezVous> listerRendezVousParDate(LocalDate date) {
        return rendezVousRepository.findByDate(date);
    }

    @Override
    public void annulerRendezVous(Long id) {
        RendezVous rdv = rendezVousRepository.findById(id);
        if (rdv != null) {
            rdv.setStatut(StatutRDV.ANNULE);
            rendezVousRepository.update(rdv);
        }
    }

    @Override
    public void confirmerRendezVous(Long id) {
        RendezVous rdv = rendezVousRepository.findById(id);
        if (rdv != null) {
            rdv.setStatut(StatutRDV.CONFIRME);
            rendezVousRepository.update(rdv);
        }
    }
}