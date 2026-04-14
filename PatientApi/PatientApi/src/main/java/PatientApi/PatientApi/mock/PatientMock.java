package PatientApi.PatientApi.mock;


import PatientApi.PatientApi.patient.data.entity.Patient;
import PatientApi.PatientApi.patient.data.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PatientMock implements CommandLineRunner {

    private PatientRepository patientRepository;
    public PatientMock(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (patientRepository.count() == 0) {
            Patient p1 = new Patient();
            //Creation de patients
            p1.setNumero("PAT-001");
            p1.setNom("Fatou");
            p1.setPrenom("Jeanne");
            p1.setAntecedents("Diabete");
            p1.setAdresse("Fass");
            p1.setTelephone("77 123 45 67");
            patientRepository.save(p1);

            Patient p2 = new Patient();
            p2.setNumero("PAT-002");
            p2.setNom("Sarah");
            p2.setPrenom("Bamba");
            p2.setAntecedents("Diabete");
            p2.setAdresse("Fass");
            p2.setTelephone("77 123 77 77");
            patientRepository.save(p2);

            Patient p3 = new Patient();
            p3.setNumero("PAT-003");
            p3.setNom("Jules");
            p3.setPrenom("Jean");
            p3.setAntecedents("Diabete");
            p3.setAdresse("Fass");
            p3.setTelephone("77 123 33 77");
            patientRepository.save(p3);

        }

    }
}