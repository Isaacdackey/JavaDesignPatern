package PatientApiV2.PatientApiV2.mock;

import PatientApiV2.PatientApiV2.auth.entity.Role;
import PatientApiV2.PatientApiV2.auth.entity.RoleType;
import PatientApiV2.PatientApiV2.auth.repository.RoleRepository;
import PatientApiV2.PatientApiV2.patient.data.entity.Patient;
import PatientApiV2.PatientApiV2.patient.data.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Order(1)
public class PatientMock implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public PatientMock(PatientRepository patientRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.patientRepository = patientRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {


        if (roleRepository.findByRole(RoleType.PATIENT).isEmpty()) {
            var role = new Role();
            role.setRole(RoleType.PATIENT);
            roleRepository.save(role);
        }


        if (roleRepository.findByRole(RoleType.ADMIN).isEmpty()) {
            var role = new Role();
            role.setRole(RoleType.ADMIN);
            roleRepository.save(role);
        }

        if (patientRepository.count() == 0) {
            var rolePatient = roleRepository.findByRole(RoleType.PATIENT).orElseThrow();
            var rolePatientAdmin = roleRepository.findByRole(RoleType.ADMIN).orElseThrow();

            Patient p1 = new Patient();
            p1.setNumero("PAT-001");
            p1.setPassword(passwordEncoder.encode("passer@123"));
            p1.setEmail("p1@gmail.com");
            p1.setNom("Durand");
            p1.setPrenom("Pierre");
            p1.setTelephone("771234567");
            p1.setAdresse("Dakar, Plateau");
            p1.setAntecedents("Hypertension");
            p1.setRoles(new HashSet<>(Set.of(rolePatient, rolePatientAdmin)));
            patientRepository.save(p1);

            Patient p2 = new Patient();
            p2.setNumero("PAT-002");
            p2.setEmail("Sarah@gmail.com");
            p2.setPassword(passwordEncoder.encode("password345"));
            p2.setNom("Sarah");
            p2.setPrenom("Bamba");
            p2.setAntecedents("Diabete");
            p2.setAdresse("Fass");
            p2.setTelephone("77 123 77 77");
            p2.setRoles(new HashSet<>(Set.of(rolePatient)));
            patientRepository.save(p2);

            Patient p3 = new Patient();
            p3.setNumero("PAT-003");
            p3.setNom("Jules");
            p3.setEmail("Jean@gmail.com");
            p3.setPassword(passwordEncoder.encode("password456"));
            p3.setPrenom("Jean");
            p3.setAntecedents("Diabete");
            p3.setAdresse("Fass");
            p3.setTelephone("77 123 33 77");
            p3.setRoles(new HashSet<>(Set.of(rolePatient)));
            patientRepository.save(p3);
        }
    }
}