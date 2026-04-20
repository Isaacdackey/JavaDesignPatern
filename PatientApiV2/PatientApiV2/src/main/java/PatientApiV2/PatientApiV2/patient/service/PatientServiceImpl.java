package PatientApiV2.PatientApiV2.patient.service;


import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateResponseDto;
import PatientApiV2.PatientApiV2.client.web.mapper.PatientCreateMapper;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.data.repository.PatientRepository;
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
        if (nom == null || nom.isEmpty()) {
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
    public PatientCreateResponseDto addPatient(PatientCreateRequestDto patient) {
        Patient patientEntity = PatientCreateMapper.toEntity(patient);
        patientRepository.save(patientEntity);
        return PatientCreateMapper.toDto(patientEntity);
    }


    private List<Patient> getAllPatientsByNom(String nom) {
        return patientRepository.searchPatientByNom(nom);
    }

}
