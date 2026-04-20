package PatientApiV2.PatientApiV2.patient.service;

import PatientApiV2.PatientApiV2.client.web.dto.DemandeCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.DemandeResponseDto;
import PatientApiV2.PatientApiV2.client.web.mapper.DemandeMapper;
import PatientApiV2.PatientApiV2.patient.data.entity.Demande;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import PatientApiV2.PatientApiV2.patient.data.repository.DemandeRepository;
import PatientApiV2.PatientApiV2.patient.data.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandeServiceImpl implements DemandeService {

    private final DemandeRepository demandeRepository;
    private final PatientRepository patientRepository;

    public DemandeServiceImpl(DemandeRepository demandeRepository,
                              PatientRepository patientRepository) {
        this.demandeRepository = demandeRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public DemandeResponseDto creerDemande(DemandeCreateRequestDto requestDto) {
        Patient patient = patientRepository.findById(requestDto.patientId())
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID: " + requestDto.patientId()));

        Demande demande = DemandeMapper.toEntity(requestDto, patient);
        Demande savedDemande = demandeRepository.save(demande);
        return DemandeMapper.toDto(savedDemande);
    }

    @Override
    public List<DemandeResponseDto> getDemandesDuJour() {
        LocalDate aujourdHui = LocalDate.now();
        LocalDateTime debut = aujourdHui.atStartOfDay();
        LocalDateTime fin = aujourdHui.plusDays(1).atStartOfDay();


        return demandeRepository.findByDateDemandeBetween(debut, fin)
                .stream()
                .map(DemandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DemandeResponseDto annulerDemande(Long id) {
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée avec l'ID: " + id));

        if (demande.getStatut() == StatutDemande.ANNULÉE) {
            throw new RuntimeException("Cette demande est déjà annulée");
        }

        if (demande.getStatut() == StatutDemande.VALIDÉE) {
            throw new RuntimeException("Impossible d'annuler une demande déjà validée");
        }

        demande.setStatut(StatutDemande.ANNULÉE);
        Demande updatedDemande = demandeRepository.save(demande);
        return DemandeMapper.toDto(updatedDemande);
    }

    @Override
    @Transactional
    public DemandeResponseDto validerDemande(Long id) {
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée avec l'ID: " + id));

        if (demande.getStatut() == StatutDemande.VALIDÉE) {
            throw new RuntimeException("Cette demande est déjà validée");
        }

        if (demande.getStatut() == StatutDemande.ANNULÉE) {
            throw new RuntimeException("Impossible de valider une demande annulée");
        }

        demande.setStatut(StatutDemande.VALIDÉE);
        Demande updatedDemande = demandeRepository.save(demande);
        return DemandeMapper.toDto(updatedDemande);
    }

    @Override
    public List<DemandeResponseDto> getDemandesWithFilters(LocalDate date, Long patientId, StatutDemande statut) {
        List<Demande> demandes;

        LocalDateTime debut = date != null ? date.atStartOfDay() : null;
        LocalDateTime fin = date != null ? date.plusDays(1).atStartOfDay() : null;

        if (date != null && patientId != null && statut != null) {
            demandes = demandeRepository.findByPatientIdAndDateDemandeBetweenAndStatut(patientId, debut, fin, statut);
        } else if (date != null && patientId != null) {
            demandes = demandeRepository.findByPatientIdAndDateDemandeBetween(patientId, debut, fin);
        } else if (date != null && statut != null) {
            demandes = demandeRepository.findByDateDemandeBetweenAndStatut(debut, fin, statut);
        } else if (patientId != null && statut != null) {
            demandes = demandeRepository.findByPatientIdAndStatut(patientId, statut);
        } else if (date != null) {
            demandes = demandeRepository.findByDateDemandeBetween(debut, fin);
        } else if (patientId != null) {
            demandes = demandeRepository.findByPatientId(patientId);
        } else if (statut != null) {
            demandes = demandeRepository.findByStatut(statut);
        } else {
            demandes = demandeRepository.findAll();
        }

        return demandes.stream()
                .map(DemandeMapper::toDto)
                .collect(Collectors.toList());
    }
}