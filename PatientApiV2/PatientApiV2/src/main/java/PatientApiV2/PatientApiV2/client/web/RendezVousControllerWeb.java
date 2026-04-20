package PatientApiV2.PatientApiV2.client.web;

import PatientApiV2.PatientApiV2.client.web.dto.RendezVousCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousResponseDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousUpdateRequestDto;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutRdv;
import PatientApiV2.PatientApiV2.patient.service.RendezVousService;
import jakarta.validation.Valid;
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
    public ResponseEntity<RendezVousResponseDto> creerRendezVous(
            @Valid @RequestBody RendezVousCreateRequestDto requestDto) {
        RendezVousResponseDto nouveauRdv = rendezVousService.creerRendezVous(requestDto);
        return new ResponseEntity<>(nouveauRdv, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RendezVousResponseDto>> getAllRendezVous() {
        return new ResponseEntity<>(rendezVousService.getAllRendezVous(), HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<RendezVousResponseDto>> getRendezVousByPatient(@PathVariable Long patientId) {
        return new ResponseEntity<>(rendezVousService.getRendezVousByPatient(patientId), HttpStatus.OK);
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<RendezVousResponseDto>> getRendezVousByStatut(@PathVariable String statut) {
        try {
            StatutRdv statutEnum = StatutRdv.valueOf(statut.toUpperCase());
            return new ResponseEntity<>(rendezVousService.getRendezVousByStatut(statutEnum), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/patient/{patientId}/statut/{statut}")
    public ResponseEntity<List<RendezVousResponseDto>> getRendezVousByPatientAndStatut(
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
    public ResponseEntity<RendezVousResponseDto> getRendezVousById(@PathVariable Long id) {
        return rendezVousService.getRendezVousById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<RendezVousResponseDto> annulerRendezVous(@PathVariable Long id) {
        try {
            RendezVousResponseDto rdvAnnule = rendezVousService.annulerRendezVous(id);
            return ResponseEntity.ok(rdvAnnule);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/confirmer")
    public ResponseEntity<RendezVousResponseDto> confirmerRendezVous(@PathVariable Long id) {
        try {
            RendezVousResponseDto rdvConfirme = rendezVousService.confirmerRendezVous(id);
            return ResponseEntity.ok(rdvConfirme);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RendezVousResponseDto> modifierRendezVous(
            @PathVariable Long id,
            @Valid @RequestBody RendezVousUpdateRequestDto updateDto) {
        try {
            RendezVousResponseDto rdvModifie = rendezVousService.modifierRendezVous(id, updateDto);
            return ResponseEntity.ok(rdvModifie);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}