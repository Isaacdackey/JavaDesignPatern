package PatientApi.PatientApi.patient.service;


import PatientApi.PatientApi.patient.data.entity.Patient;
import PatientApi.PatientApi.patient.data.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> searchPatients(String nom) {
        if (nom.isEmpty()) {
            return patientRepository.findAll();
        }
        return patientRepository.searchPatientByNom(nom);
    }

    private List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }


    private List<Patient> getAllPatientsByNom(String nom) {
        return patientRepository.searchPatientByNom(nom);
    }

}
