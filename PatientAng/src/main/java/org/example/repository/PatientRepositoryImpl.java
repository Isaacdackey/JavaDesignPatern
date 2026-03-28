package org.example.repository;

import org.example.entity.Patient;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {

    private EntityManager em;
    public PatientRepositoryImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public Patient insert(Patient patient) {
        this.em.getTransaction().begin();
        try {
            this.em.persist(patient);
            this.em.getTransaction().commit();
            return patient;
        } catch (Exception e){
            this.em.getTransaction().rollback();
            throw new RuntimeException("Erreur lors de l'insertion du patient: "+e.getMessage());
        }
    }

    @Override
    public List<Patient> selectAll() {
        String jpql = "select pat from Patient pat";
        TypedQuery<Patient> query = em.createQuery(jpql, Patient.class);
        return query.getResultList();
    }
}
