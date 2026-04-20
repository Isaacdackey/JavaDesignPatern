package PatientApiV2.PatientApiV2.patient.service;

import PatientApiV2.PatientApiV2.client.web.dto.RendezVousCreateRequestDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousResponseDto;
import PatientApiV2.PatientApiV2.client.web.dto.RendezVousUpdateRequestDto;
import PatientApiV2.PatientApiV2.client.web.mapper.RendezVousMapper;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.data.entity.RendezVous;
import PatientApiV2.PatientApiV2.patient.data.entity.StatutRdv;
import PatientApiV2.PatientApiV2.patient.data.repository.PatientRepository;
import PatientApiV2.PatientApiV2.patient.data.repository.RendezVousRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RendezVousServiceImpl implements RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;

    public RendezVousServiceImpl(RendezVousRepository rendezVousRepository,
                                 PatientRepository patientRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    @Transactional
    public RendezVousResponseDto creerRendezVous(RendezVousCreateRequestDto requestDto) {
        Patient patient = patientRepository.findById(requestDto.patientId())
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec l'ID: " + requestDto.patientId()));


        LocalDateTime debut = requestDto.dateRendezVous().minusMinutes(30);
        LocalDateTime fin = requestDto.dateRendezVous().plusMinutes(30);
        List<RendezVous> conflits = rendezVousRepository.findConflitsRendezVous(
                patient.getId(), debut, fin, StatutRdv.ANNULÉ);

        if (!conflits.isEmpty()) {
            throw new RuntimeException("Conflit de rendez-vous pour ce patient. Un rendez-vous existe déjà dans cette plage horaire.");
        }


        RendezVous rendezVous = RendezVousMapper.toEntity(requestDto, patient);


        RendezVous savedRdv = rendezVousRepository.save(rendezVous);


        return RendezVousMapper.toDto(savedRdv);
    }

    @Override
    public Optional<RendezVousResponseDto> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id)
                .map(RendezVousMapper::toDto);
    }

    @Override
    public List<RendezVousResponseDto> getRendezVousByPatient(Long patientId) {
        return rendezVousRepository.findByPatientId(patientId)
                .stream()
                .map(RendezVousMapper::toDto)
                .toList();
    }

    @Override
    public List<RendezVousResponseDto> getAllRendezVous() {
        return rendezVousRepository.findAll()
                .stream()
                .map(RendezVousMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public RendezVousResponseDto annulerRendezVous(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID: " + id));

        if (rendezVous.getStatut() == StatutRdv.ANNULÉ) {
            throw new RuntimeException("Ce rendez-vous est déjà annulé");
        }

        if (rendezVous.getDateRendezVous().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Impossible d'annuler un rendez-vous passé");
        }

        rendezVous.setStatut(StatutRdv.ANNULÉ);
        RendezVous updatedRdv = rendezVousRepository.save(rendezVous);
        return RendezVousMapper.toDto(updatedRdv);
    }

    @Override
    @Transactional
    public RendezVousResponseDto confirmerRendezVous(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID: " + id));

        if (rendezVous.getStatut() == StatutRdv.CONFIRMÉ) {
            throw new RuntimeException("Ce rendez-vous est déjà confirmé");
        }

        if (rendezVous.getStatut() == StatutRdv.ANNULÉ) {
            throw new RuntimeException("Impossible de confirmer un rendez-vous annulé");
        }

        rendezVous.setStatut(StatutRdv.CONFIRMÉ);
        RendezVous updatedRdv = rendezVousRepository.save(rendezVous);
        return RendezVousMapper.toDto(updatedRdv);
    }

    @Override
    @Transactional
    public RendezVousResponseDto modifierRendezVous(Long id, RendezVousUpdateRequestDto updateDto) {

        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé avec l'ID: " + id));

        if (rendezVous.getStatut() == StatutRdv.ANNULÉ) {
            throw new RuntimeException("Impossible de modifier un rendez-vous annulé");
        }

        if (updateDto.dateRendezVous() != null) {
            LocalDateTime debut = updateDto.dateRendezVous().minusMinutes(30);
            LocalDateTime fin = updateDto.dateRendezVous().plusMinutes(30);
            List<RendezVous> conflits = rendezVousRepository.findConflitsRendezVous(
                    rendezVous.getPatient().getId(), debut, fin, StatutRdv.ANNULÉ);


            conflits.removeIf(r -> r.getId().equals(id));

            if (!conflits.isEmpty()) {
                throw new RuntimeException("Conflit de rendez-vous pour ce patient avec la nouvelle date");
            }
        }


        RendezVousMapper.updateEntity(rendezVous, updateDto);


        RendezVous updatedRdv = rendezVousRepository.save(rendezVous);


        return RendezVousMapper.toDto(updatedRdv);
    }

    @Override
    public List<RendezVousResponseDto> getRendezVousByStatut(StatutRdv statut) {
        return rendezVousRepository.findByStatut(statut)
                .stream()
                .map(RendezVousMapper::toDto)
                .toList();
    }

    @Override
    public List<RendezVousResponseDto> getRendezVousByPatientAndStatut(Long patientId, StatutRdv statut) {
        return rendezVousRepository.findByPatientIdAndStatut(patientId, statut)
                .stream()
                .map(RendezVousMapper::toDto)
                .toList();
    }
}