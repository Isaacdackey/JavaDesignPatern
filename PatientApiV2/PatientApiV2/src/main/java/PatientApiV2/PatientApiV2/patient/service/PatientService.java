package PatientApiV2.PatientApiV2.patient.service;

import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    public List<Patient> searchPatients(String nom);
    public Optional<Patient> getPatientById(Long id);
    public PatientCreateResponseDto addPatient(PatientCreateRequestDto patient);
}
