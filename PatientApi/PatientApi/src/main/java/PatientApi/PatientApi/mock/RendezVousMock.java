package PatientApi.PatientApi.mock;

import PatientApi.PatientApi.patient.data.entity.Patient;
import PatientApi.PatientApi.patient.data.entity.RendezVous;
import PatientApi.PatientApi.patient.data.entity.StatutRdv;
import PatientApi.PatientApi.patient.data.repository.PatientRepository;
import PatientApi.PatientApi.patient.data.repository.RendezVousRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
@Order(2)
public class RendezVousMock implements CommandLineRunner {

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;

    public RendezVousMock(RendezVousRepository rendezVousRepository,
                          PatientRepository patientRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (rendezVousRepository.count() == 0) {
            Patient patient1 = patientRepository.findById(1L).orElse(null);
            Patient patient2 = patientRepository.findById(2L).orElse(null);

            if (patient1 != null) {
                RendezVous rdv1 = new RendezVous();
                rdv1.setPatient(patient1);
                rdv1.setDateRendezVous(LocalDateTime.now().plusDays(1).withHour(10).withMinute(0));
                rdv1.setMotif("Consultation générale");
                rdv1.setStatut(StatutRdv.EN_ATTENTE);
                rendezVousRepository.save(rdv1);

                RendezVous rdv2 = new RendezVous();
                rdv2.setPatient(patient1);
                rdv2.setDateRendezVous(LocalDateTime.now().plusDays(3).withHour(14).withMinute(30));
                rdv2.setMotif("Suivi diabète");
                rdv2.setStatut(StatutRdv.CONFIRMÉ);
                rendezVousRepository.save(rdv2);
            }

            if (patient2 != null) {
                RendezVous rdv3 = new RendezVous();
                rdv3.setPatient(patient2);
                rdv3.setDateRendezVous(LocalDateTime.now().plusDays(2).withHour(9).withMinute(0));
                rdv3.setMotif("Vaccination");
                rdv3.setStatut(StatutRdv.EN_ATTENTE);
                rendezVousRepository.save(rdv3);
            }
        }
    }
}