package PatientApi.PatientApi.patient.service;

import PatientApi.PatientApi.patient.data.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    public List<Patient> searchPatients(String nom);
    public Optional<Patient> getPatientById(Long id);
    public Patient addPatient(Patient patient);
}
