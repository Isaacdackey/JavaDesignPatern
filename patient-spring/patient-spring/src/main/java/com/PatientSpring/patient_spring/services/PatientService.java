package com.PatientSpring.patient_spring.services;

import com.PatientSpring.patient_spring.entity.Patient;

import java.util.List;

public interface PatientService {
    public void creerPatient(Patient patient);
    public List<Patient > listerPatients();
}
