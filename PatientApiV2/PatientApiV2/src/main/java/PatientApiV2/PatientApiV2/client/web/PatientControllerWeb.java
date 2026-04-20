package PatientApiV2.PatientApiV2.client.web;

import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.PatientCreateResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PatientControllerWeb {

    private final PatientService patientService;

    public PatientControllerWeb(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatients(@RequestParam(defaultValue = "") String nom) {
        return new ResponseEntity<>(patientService.searchPatients(nom), HttpStatus.OK);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.getPatientById(id).orElse(null), HttpStatus.OK);
    }

    @PostMapping("/patients")
    public ResponseEntity<PatientCreateResponseDto> createPatient(@Valid @RequestBody PatientCreateRequestDto patient) {
        return new ResponseEntity<>(patientService.addPatient(patient), HttpStatus.CREATED);
    }
}
