package org.example.services;

import org.example.entity.Patient;
import org.example.repository.PatientRepository;

import java.util.List;

public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void creerPatient(Patient patient) {
        patientRepository.insert(patient);
    }

    @Override
    public List<Patient > listerPatients() {
        return patientRepository.selectAll();
    }
}
