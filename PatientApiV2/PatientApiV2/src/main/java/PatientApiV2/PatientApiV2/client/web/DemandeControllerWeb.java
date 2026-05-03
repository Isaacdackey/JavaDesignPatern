package PatientApiV2.PatientApiV2.client.web;

import PatientApiV2.PatientApiV2.client.web.dto.DemandeCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.DemandeResponseDto;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import PatientApiV2.PatientApiV2.patient.service.DemandeService;
import PatientApiV2.PatientApiV2.shared.response.PageResponse;
import PatientApiV2.PatientApiV2.shared.response.RestResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/demandes")
public class DemandeControllerWeb {

    private final DemandeService demandeService;

    public DemandeControllerWeb(DemandeService demandeService) {
        this.demandeService = demandeService;
    }


    @GetMapping
    public ResponseEntity<RestResponse<PageResponse<DemandeResponseDto>>> getAllDemandes(
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DemandeResponseDto> demandesPage = demandeService.getAllDemandesPaged(pageable);
        return ResponseEntity.ok(RestResponse.success("Liste des demandes récupérée", PageResponse.fromPage(demandesPage)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<DemandeResponseDto>> getDemandeById(@PathVariable Long id) {
        DemandeResponseDto demande = demandeService.getDemandeById(id);
        return ResponseEntity.ok(RestResponse.success("Demande récupérée", demande));
    }

    @PostMapping
    public ResponseEntity<RestResponse<DemandeResponseDto>> creerDemande(@Valid @RequestBody DemandeCreateRequestDto requestDto) {
        DemandeResponseDto demande = demandeService.creerDemande(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponse.success("Demande créée avec succès", demande));
    }

    @GetMapping("/aujourdhui")
    public ResponseEntity<RestResponse<List<DemandeResponseDto>>> getDemandesDuJour() {
        List<DemandeResponseDto> demandes = demandeService.getDemandesDuJour();
        return ResponseEntity.ok(RestResponse.success("Demandes du jour récupérées", demandes));
    }

    @PutMapping("/{id}/annuler")
    public ResponseEntity<RestResponse<DemandeResponseDto>> annulerDemande(@PathVariable Long id) {
        DemandeResponseDto demande = demandeService.annulerDemande(id);
        return ResponseEntity.ok(RestResponse.success("Demande annulée avec succès", demande));
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<RestResponse<DemandeResponseDto>> validerDemande(@PathVariable Long id) {
        DemandeResponseDto demande = demandeService.validerDemande(id);
        return ResponseEntity.ok(RestResponse.success("Demande validée avec succès", demande));
    }

    @GetMapping("/filtres")
    public ResponseEntity<RestResponse<List<DemandeResponseDto>>> getDemandesWithFilters(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) String statut) {

        StatutDemande statutEnum = null;
        if (statut != null) {
            try {
                statutEnum = StatutDemande.valueOf(statut.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest()
                        .body(RestResponse.error("Statut invalide. Valeurs acceptées: EN_ATTENTE, VALIDÉE, ANNULÉE"));
            }
        }

        List<DemandeResponseDto> demandes = demandeService.getDemandesWithFilters(date, patientId, statutEnum);
        return ResponseEntity.ok(RestResponse.success("Filtres appliqués avec succès", demandes));
    }
}