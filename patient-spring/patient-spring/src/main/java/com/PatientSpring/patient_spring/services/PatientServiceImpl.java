package com.PatientSpring.patient_spring.services;

import com.PatientSpring.patient_spring.entity.Patient;
import com.PatientSpring.patient_spring.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void creerPatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public List<Patient> listerPatients() {
        return patientRepository.findAll();
    }
}