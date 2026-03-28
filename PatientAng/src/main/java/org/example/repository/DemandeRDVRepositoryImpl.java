package org.example.repository;

import org.example.entity.DemandeRDV;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DemandeRDVRepositoryImpl implements DemandeRDVRepository {

    private EntityManager em;

    public DemandeRDVRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public DemandeRDV insert(DemandeRDV demande) {
        em.getTransaction().begin();
        try {
            em.persist(demande);
            em.getTransaction().commit();
            return demande;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de l'insertion de la demande: " + e.getMessage());
        }
    }

    @Override
    public List<DemandeRDV> selectAll() {
        String jpql = "SELECT d FROM DemandeRDV d JOIN FETCH d.patient";
        TypedQuery<DemandeRDV> query = em.createQuery(jpql, DemandeRDV.class);
        return query.getResultList();
    }

    @Override
    public List<DemandeRDV> findByPatientId(Long patientId) {
        String jpql = "SELECT d FROM DemandeRDV d WHERE d.patient.id = :patientId";
        TypedQuery<DemandeRDV> query = em.createQuery(jpql, DemandeRDV.class);
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    @Override
    public List<DemandeRDV> findEnAttente() {
        String jpql = "SELECT d FROM DemandeRDV d ORDER BY d.dateDemande DESC";
        TypedQuery<DemandeRDV> query = em.createQuery(jpql, DemandeRDV.class);
        return query.getResultList();
    }
}