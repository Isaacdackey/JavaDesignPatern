package PatientApi.PatientApi.patient.service;

import PatientApi.PatientApi.patient.data.entity.Patient;
import PatientApi.PatientApi.patient.data.entity.RendezVous;
import PatientApi.PatientApi.patient.data.entity.StatutRdv;
import PatientApi.PatientApi.patient.data.repository.PatientRepository;
import PatientApi.PatientApi.patient.data.repository.RendezVousRepository;
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
    public RendezVous creerRendezVous(RendezVous rendezVous) {
        Patient patient = patientRepository.findById(rendezVous.getPatient().getId())
                .orElseThrow(() -> new RuntimeException("Patient non trouvé"));

        LocalDateTime debut = rendezVous.getDateRendezVous().minusMinutes(30);
        LocalDateTime fin = rendezVous.getDateRendezVous().plusMinutes(30);
        List<RendezVous> conflits = rendezVousRepository.findConflitsRendezVous(
                patient.getId(), debut, fin, StatutRdv.ANNULÉ);

        if (!conflits.isEmpty()) {
            throw new RuntimeException("Conflit de rendez-vous pour ce patient");
        }

        if (rendezVous.getStatut() == null) {
            rendezVous.setStatut(StatutRdv.EN_ATTENTE);
        }

        rendezVous.setPatient(patient);
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public Optional<RendezVous> getRendezVousById(Long id) {
        return rendezVousRepository.findById(id);
    }

    @Override
    public List<RendezVous> getRendezVousByPatient(Long patientId) {
        return rendezVousRepository.findByPatientId(patientId);
    }

    @Override
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    @Override
    @Transactional
    public RendezVous annulerRendezVous(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));
        rendezVous.setStatut(StatutRdv.ANNULÉ);
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    @Transactional
    public RendezVous confirmerRendezVous(Long id) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));
        rendezVous.setStatut(StatutRdv.CONFIRMÉ);
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    @Transactional
    public RendezVous modifierRendezVous(Long id, RendezVous rendezVousDetails) {
        RendezVous rendezVous = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));

        if (rendezVousDetails.getDateRendezVous() != null) {
            LocalDateTime debut = rendezVousDetails.getDateRendezVous().minusMinutes(30);
            LocalDateTime fin = rendezVousDetails.getDateRendezVous().plusMinutes(30);
            List<RendezVous> conflits = rendezVousRepository.findConflitsRendezVous(
                    rendezVous.getPatient().getId(), debut, fin, StatutRdv.ANNULÉ);

            conflits.removeIf(r -> r.getId().equals(id));

            if (!conflits.isEmpty()) {
                throw new RuntimeException("Conflit de rendez-vous pour ce patient");
            }
            rendezVous.setDateRendezVous(rendezVousDetails.getDateRendezVous());
        }

        if (rendezVousDetails.getMotif() != null) {
            rendezVous.setMotif(rendezVousDetails.getMotif());
        }

        if (rendezVousDetails.getStatut() != null) {
            rendezVous.setStatut(rendezVousDetails.getStatut());
        }

        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public List<RendezVous> getRendezVousByStatut(StatutRdv statut) {
        return rendezVousRepository.findByStatut(statut);
    }

    @Override
    public List<RendezVous> getRendezVousByPatientAndStatut(Long patientId, StatutRdv statut) {
        return rendezVousRepository.findByPatientIdAndStatut(patientId, statut);
    }
}