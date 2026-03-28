package org.example.services;

import org.example.entity.Patient;

import java.util.List;

public interface PatientService {
    public void creerPatient(Patient patient);
    public List<Patient > listerPatients();
}
