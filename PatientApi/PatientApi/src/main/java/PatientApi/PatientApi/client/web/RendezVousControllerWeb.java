package PatientApi.PatientApi.client.web;

import PatientApi.PatientApi.patient.data.entity.RendezVous;
import PatientApi.PatientApi.patient.data.entity.StatutRdv;
import PatientApi.PatientApi.patient.service.RendezVousService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rendez-vous")
public class RendezVousControllerWeb {

    private final RendezVousService rendezVousService;

    public RendezVousControllerWeb(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }


    @PostMapping
    public ResponseEntity<RendezVous> creerRendezVous(@RequestBody RendezVous rendezVous) {
        RendezVous nouveauRdv = rendezVousService.creerRendezVous(rendezVous);
        return new ResponseEntity<>(nouveauRdv, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<RendezVous>> getAllRendezVous() {
        return new ResponseEntity<>(rendezVousService.getAllRendezVous(), HttpStatus.OK);
    }


    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<RendezVous>> getRendezVousByPatient(@PathVariable Long patientId) {
        return new ResponseEntity<>(rendezVousService.getRendezVousByPatient(patientId), HttpStatus.OK);
    }


    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<RendezVous>> getRendezVousByStatut(@PathVariable String statut) {
        try {
            StatutRdv statutEnum = StatutRdv.valueOf(statut.toUpperCase());
            return new ResponseEntity<>(rendezVousService.getRendezVousByStatut(statutEnum), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/patient/{patientId}/statut/{statut}")
    public ResponseEntity<List<RendezVous>> getRendezVousByPatientAndStatut(
            @PathVariable Long patientId,
            @PathVariable String statut) {
        try {
            StatutRdv statutEnum = StatutRdv.valueOf(statut.toUpperCase());
            return new ResponseEntity<>(
                    rendezVousService.getRendezVousByPatientAndStatut(patientId, statutEnum),
                    HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<RendezVous> getRendezVousById(@PathVariable Long id) {
        return rendezVousService.getRendezVousById(id)
                .map(rendezVous -> new ResponseEntity<>(rendezVous, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}/annuler")
    public ResponseEntity<RendezVous> annulerRendezVous(@PathVariable Long id) {
        return new ResponseEntity<>(rendezVousService.annulerRendezVous(id), HttpStatus.OK);
    }


    @PutMapping("/{id}/confirmer")
    public ResponseEntity<RendezVous> confirmerRendezVous(@PathVariable Long id) {
        return new ResponseEntity<>(rendezVousService.confirmerRendezVous(id), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<RendezVous> modifierRendezVous(
            @PathVariable Long id,
            @RequestBody RendezVous rendezVous) {
        return new ResponseEntity<>(rendezVousService.modifierRendezVous(id, rendezVous), HttpStatus.OK);
    }
}