package PatientApi.PatientApi.client.web;

import PatientApi.PatientApi.patient.data.entity.Patient;
import PatientApi.PatientApi.patient.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.addPatient(patient), HttpStatus.CREATED);
    }
}
