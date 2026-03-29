package com.PatientSpring.patient_spring.services;

import com.PatientSpring.patient_spring.entity.RendezVous;
import com.PatientSpring.patient_spring.entity.StatutRDV;
import com.PatientSpring.patient_spring.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RendezVousServiceImpl implements RendezVousService {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    @Override
    public void creerRendezVous(RendezVous rendezVous) {
        rendezVousRepository.save(rendezVous);
    }

    @Override
    public List<RendezVous> listerRendezVous() {
        return rendezVousRepository.findAll();
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
        RendezVous rdv = rendezVousRepository.findById(id).orElse(null);
        if (rdv != null) {
            rdv.setStatut(StatutRDV.ANNULE);
            rendezVousRepository.save(rdv);
        }
    }

    @Override
    public void confirmerRendezVous(Long id) {
        RendezVous rdv = rendezVousRepository.findById(id).orElse(null);
        if (rdv != null) {
            rdv.setStatut(StatutRDV.CONFIRME);
            rendezVousRepository.save(rdv);
        }
    }
}