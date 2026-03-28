package org.example.repository;

import org.example.entity.RendezVous;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class RendezVousRepositoryImpl implements RendezVousRepository {

    private EntityManager em;

    public RendezVousRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public RendezVous insert(RendezVous rendezVous) {
        em.getTransaction().begin();
        try {
            em.persist(rendezVous);
            em.getTransaction().commit();
            return rendezVous;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de l'insertion du rendez-vous: " + e.getMessage());
        }
    }

    @Override
    public List<RendezVous> selectAll() {
        String jpql = "SELECT r FROM RendezVous r JOIN FETCH r.patient";
        TypedQuery<RendezVous> query = em.createQuery(jpql, RendezVous.class);
        return query.getResultList();
    }

    @Override
    public RendezVous findById(Long id) {
        return em.find(RendezVous.class, id);
    }

    @Override
    public List<RendezVous> findByPatientId(Long patientId) {
        String jpql = "SELECT r FROM RendezVous r WHERE r.patient.id = :patientId";
        TypedQuery<RendezVous> query = em.createQuery(jpql, RendezVous.class);
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    @Override
    public List<RendezVous> findByDate(LocalDate date) {
        String jpql = "SELECT r FROM RendezVous r WHERE r.date = :date";
        TypedQuery<RendezVous> query = em.createQuery(jpql, RendezVous.class);
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public RendezVous update(RendezVous rendezVous) {
        em.getTransaction().begin();
        try {
            RendezVous merged = em.merge(rendezVous);
            em.getTransaction().commit();
            return merged;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la mise à jour du rendez-vous: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        try {
            RendezVous rendezVous = em.find(RendezVous.class, id);
            if (rendezVous != null) {
                em.remove(rendezVous);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de la suppression du rendez-vous: " + e.getMessage());
        }
    }
}