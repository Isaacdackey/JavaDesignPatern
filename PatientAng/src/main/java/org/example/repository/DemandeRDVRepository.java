package org.example.repository;

import org.example.entity.DemandeRDV;
import java.util.List;

public interface DemandeRDVRepository {
    DemandeRDV insert(DemandeRDV demande);
    List<DemandeRDV> selectAll();
    List<DemandeRDV> findByPatientId(Long patientId);
    List<DemandeRDV> findEnAttente();
}