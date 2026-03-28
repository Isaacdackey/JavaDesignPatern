package org.example.repository;

import org.example.entity.Patient;

import java.util.List;

public interface PatientRepository {
    public Patient insert(Patient patient);
    public List<Patient> selectAll();

}
