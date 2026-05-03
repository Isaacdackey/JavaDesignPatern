package PatientApiV2.PatientApiV2.auth.repository;

import PatientApiV2.PatientApiV2.auth.entity.Role;
import PatientApiV2.PatientApiV2.auth.entity.RoleType;
import PatientApiV2.PatientApiV2.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRole (RoleType  role);

}
