package PatientApiV2.PatientApiV2.client.web;

import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.service.PatientService;
import PatientApiV2.PatientApiV2.shared.response.RestResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientControllerWeb {

    private final PatientService patientService;

    public PatientControllerWeb(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<Patient>>> getAllPatients(
            @RequestParam(defaultValue = "") String nom) {
        List<Patient> patients = patientService.searchPatients(nom);
        return ResponseEntity.ok(RestResponse.success("Liste des patients récupérée", patients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<Patient>> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));
        return ResponseEntity.ok(RestResponse.success("Patient récupéré", patient));
    }

    @PostMapping
    public ResponseEntity<RestResponse<PatientCreateResponseDto>> addPatient(
            @Valid @RequestBody PatientCreateRequestDto patient) {
        PatientCreateResponseDto createdPatient = patientService.addPatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.success("Patient créé avec succès", createdPatient));
    }
}