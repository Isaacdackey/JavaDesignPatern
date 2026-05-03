package PatientApiV2.PatientApiV2.client.web;

import PatientApiV2.PatientApiV2.client.web.dto.RendezVousCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousResponseDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousUpdateRequestDto;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutRdv;
import PatientApiV2.PatientApiV2.patient.service.RendezVousService;
import PatientApiV2.PatientApiV2.shared.response.RestResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rendez-vous")
public class RendezVousControllerWeb {

    private final RendezVousService rendezVousService;

    public RendezVousControllerWeb(RendezVousService rendezVousService) {
        this.rendezVousService = rendezVousService;
    }

    @PostMapping
    public ResponseEntity<RestResponse<RendezVousResponseDto>> creerRendezVous(
            @Valid @RequestBody RendezVousCreateRequestDto requestDto) {
        RendezVousResponseDto nouveauRdv = rendezVousService.creerRendezVous(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.success("Rendez-vous créé avec succès", nouveauRdv));
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<RendezVousResponseDto>>> getAllRendezVous() {
        List<RendezVousResponseDto> rdvs = rendezVousService.getAllRendezVous();
        return ResponseEntity.ok(RestResponse.success("Liste des rendez-vous", rdvs));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<RestResponse<List<RendezVousResponseDto>>> getRendezVousByPatient(@PathVariable Long patientId) {
        List<RendezVousResponseDto> rdvs = rendezVousService.getRendezVousByPatient(patientId);
        return ResponseEntity.ok(RestResponse.success("Rendez-vous du patient", rdvs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<RendezVousResponseDto>> getRendezVousById(@PathVariable Long id) {
        RendezVousResponseDto rdv = rendezVousService.getRendezVousById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));
        return ResponseEntity.ok(RestResponse.success("Rendez-vous récupéré", rdv));
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<RestResponse<RendezVousResponseDto>> annulerRendezVous(@PathVariable Long id) {
        RendezVousResponseDto rdvAnnule = rendezVousService.annulerRendezVous(id);
        return ResponseEntity.ok(RestResponse.success("Rendez-vous annulé", rdvAnnule));
    }

    @PutMapping("/{id}/confirmer")
    public ResponseEntity<RestResponse<RendezVousResponseDto>> confirmerRendezVous(@PathVariable Long id) {
        RendezVousResponseDto rdvConfirme = rendezVousService.confirmerRendezVous(id);
        return ResponseEntity.ok(RestResponse.success("Rendez-vous confirmé", rdvConfirme));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<RendezVousResponseDto>> modifierRendezVous(
            @PathVariable Long id,
            @Valid @RequestBody RendezVousUpdateRequestDto updateDto) {
        RendezVousResponseDto rdvModifie = rendezVousService.modifierRendezVous(id, updateDto);
        return ResponseEntity.ok(RestResponse.success("Rendez-vous modifié", rdvModifie));
    }
}