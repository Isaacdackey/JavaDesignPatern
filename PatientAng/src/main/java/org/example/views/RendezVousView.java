package org.example.views;

import org.example.entity.*;
import org.example.services.PatientService;
import org.example.services.RendezVousService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class RendezVousView {

    private static RendezVousView instance;
    private RendezVousService rendezVousService;
    private PatientService patientService;
    private Scanner sc;

    private RendezVousView() {}

    public static RendezVousView getInstance() {
        if (instance == null) {
            instance = new RendezVousView();
        }
        return instance;
    }

    public void setServices(RendezVousService rendezVousService, PatientService patientService) {
        this.rendezVousService = rendezVousService;
        this.patientService = patientService;
    }

    public void setScanner(Scanner sc) {
        this.sc = sc;
    }

    public RendezVous readRendezVous() {
        RendezVous rdv = new RendezVous();

        System.out.println("\nCréation d'un rendez-vous");


        List<Patient> patients = patientService.listerPatients();
        if (patients.isEmpty()) {
            System.out.println("Aucun patient disponible. Veuillez d'abord créer un patient.");
            return null;
        }

        System.out.println("Liste des patients disponibles:");
        patients.forEach(p -> System.out.println(p.getId() + " - " + p.getNomComplet()));

        System.out.print("Sélectionnez l'ID du patient: ");
        Long patientId = sc.nextLong();
        sc.nextLine();

        Patient patient = new Patient();
        patient.setId(patientId);
        rdv.setPatient(patient);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean dateValide = false;
        while (!dateValide) {
            try {
                System.out.print("Date du rendez-vous (JJ/MM/AAAA): ");
                String dateStr = sc.nextLine();
                rdv.setDate(LocalDate.parse(dateStr, dateFormatter));
                dateValide = true;
            } catch (DateTimeParseException e) {
                System.out.println("Format de date invalide. Utilisez JJ/MM/AAAA");
            }
        }


        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        boolean heureValide = false;
        while (!heureValide) {
            try {
                System.out.print("Heure du rendez-vous (HH:MM): ");
                String heureStr = sc.nextLine();
                rdv.setHeure(LocalTime.parse(heureStr, timeFormatter));
                heureValide = true;
            } catch (DateTimeParseException e) {
                System.out.println("Format d'heure invalide. Utilisez HH:MM");
            }
        }


        System.out.println("Spécialités disponibles:");
        for (Specialite s : Specialite.values()) {
            System.out.println("- " + s);
        }
        System.out.print("Spécialité: ");
        String specialiteStr = sc.nextLine().toUpperCase();
        try {
            rdv.setSpecialite(Specialite.valueOf(specialiteStr));
        } catch (IllegalArgumentException e) {
            System.out.println("Spécialité invalide, utilisation de GENERALISTE par défaut");
            rdv.setSpecialite(Specialite.GENERALISTE);
        }

        rdv.setStatut(StatutRDV.EN_ATTENTE);

        return rdv;
    }

    public void afficherRendezVous(List<RendezVous> rendezVous) {
        if (rendezVous == null || rendezVous.isEmpty()) {
            System.out.println("Aucun rendez-vous trouvé.");
            return;
        }

        System.out.println("\nListe des rendez-vous");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        rendezVous.forEach(rdv -> {
            System.out.println("ID: " + rdv.getId());
            System.out.println("Patient: " + rdv.getPatient().getNomComplet());
            System.out.println("Date: " + rdv.getDate().format(dateFormatter));
            System.out.println("Heure: " + rdv.getHeure().format(timeFormatter));
            System.out.println("Spécialité: " + rdv.getSpecialite());
            System.out.println("Statut: " + rdv.getStatut());
        });
    }

    public void afficherMenuRendezVous() {
        System.out.println("\nGestion des rendez-vous");
        System.out.println("1. Créer un rendez-vous");
        System.out.println("2. Lister tous les rendez-vous");
        System.out.println("3. Lister les rendez-vous par patient");
        System.out.println("4. Lister les rendez-vous par date");
        System.out.println("5. Confirmer un rendez-vous");
        System.out.println("6. Annuler un rendez-vous");
        System.out.print("7. Retour au menu principal\n> ");
    }
}