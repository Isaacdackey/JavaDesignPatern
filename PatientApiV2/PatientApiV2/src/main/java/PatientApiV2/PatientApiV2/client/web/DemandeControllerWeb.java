package PatientApiV2.PatientApiV2.client.web;

import PatientApiV2.PatientApiV2.client.web.dto.DemandeCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.DemandeResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import PatientApiV2.PatientApiV2.patient.service.DemandeService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeControllerWeb {

    private final DemandeService demandeService;

    public DemandeControllerWeb(DemandeService demandeService) {
        this.demandeService = demandeService;
    }


    @PostMapping
    public ResponseEntity<DemandeResponseDto> creerDemande(@Valid @RequestBody DemandeCreateRequestDto requestDto) {
        return new ResponseEntity<>(demandeService.creerDemande(requestDto), HttpStatus.CREATED);
    }


    @GetMapping("/aujourdhui")
    public ResponseEntity<List<DemandeResponseDto>> getDemandesDuJour() {
        return ResponseEntity.ok(demandeService.getDemandesDuJour());
    }


    @PutMapping("/{id}/annuler")
    public ResponseEntity<DemandeResponseDto> annulerDemande(@PathVariable Long id) {
        return ResponseEntity.ok(demandeService.annulerDemande(id));
    }


    @PutMapping("/{id}/valider")
    public ResponseEntity<DemandeResponseDto> validerDemande(@PathVariable Long id) {
        return ResponseEntity.ok(demandeService.validerDemande(id));
    }


    @GetMapping("/filtres")
    public ResponseEntity<List<DemandeResponseDto>> getDemandesWithFilters(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String statut) {

        StatutDemande statutEnum = null;
        if (statut != null) {
            try {
                statutEnum = StatutDemande.valueOf(statut.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.ok(demandeService.getDemandesWithFilters(date, patientId, statutEnum));
    }
}