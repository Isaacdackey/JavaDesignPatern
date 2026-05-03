package PatientApiV2.PatientApiV2.patient.service;

import PatientApiV2.PatientApiV2.client.web.dto.DemandeCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.DemandeResponseDto;
import PatientApiV2.PatientApiV2.client.web.mapper.DemandeMapper;
import PatientApiV2.PatientApiV2.patient.data.entity.Demande;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutDemande;
import PatientApiV2.PatientApiV2.patient.data.repository.DemandeRepository;
import PatientApiV2.PatientApiV2.patient.data.repository.PatientRepository;
import PatientApiV2.PatientApiV2.shared.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final DemandeMapper demandeMapper;

    public DemandeServiceImpl(DemandeRepository demandeRepository,
                              PatientRepository patientRepository,
                              DemandeMapper demandeMapper) {
        this.demandeRepository = demandeRepository;
        this.patientRepository = patientRepository;
        this.demandeMapper = demandeMapper;
    }

    @Override
    @Transactional
    public DemandeResponseDto creerDemande(DemandeCreateRequestDto requestDto) {
        Patient patient = patientRepository.findById(requestDto.patientId())
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID: " + requestDto.patientId()));

        Demande demande = demandeMapper.toEntity(requestDto, patient);
        Demande savedDemande = demandeRepository.save(demande);
        return demandeMapper.toDto(savedDemande);
    }

    @Override
    public List<DemandeResponseDto> getDemandesDuJour() {
        LocalDate aujourdHui = LocalDate.now();
        LocalDateTime debut = aujourdHui.atStartOfDay();
        LocalDateTime fin = aujourdHui.plusDays(1).atStartOfDay();

        return demandeRepository.findByDateDemandeBetween(debut, fin)
                .stream()
                .map(demandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandeResponseDto> getDemandesByPatientId(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new EntityNotFoundException("Patient non trouvé avec l'ID: " + patientId);
        }

        return demandeRepository.findByPatientId(patientId)
                .stream()
                .map(demandeMapper::toDto)
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
        return demandeMapper.toDto(updatedDemande);
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
        return demandeMapper.toDto(updatedDemande);
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
                .map(demandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandeResponseDto> getAllDemandes() {
        return demandeRepository.findAll()
                .stream()
                .map(demandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<DemandeResponseDto> getAllDemandesPaged(Pageable pageable) {
        return demandeRepository.findAll(pageable)
                .map(demandeMapper::toDto);
    }

    @Override
    public DemandeResponseDto getDemandeById(Long id) {
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Demande non trouvée avec l'ID: " + id));
        return demandeMapper.toDto(demande);
    }
}