package com.PatientSpring.patient_spring;

import com.PatientSpring.patient_spring.services.PatientService;
import com.PatientSpring.patient_spring.services.RendezVousService;
import com.PatientSpring.patient_spring.views.PatientView;
import com.PatientSpring.patient_spring.views.RendezVousView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Autowired
    private PatientService patientService;

    @Autowired
    private RendezVousService rendezVousService;

    private Scanner sc;

    @Override
    public void run(String... args) throws Exception {
        sc = new Scanner(System.in);


        PatientView.getInstance().setScanner(sc);
        PatientView.getInstance().setPatientService(patientService);

        RendezVousView.getInstance().setScanner(sc);
        RendezVousView.getInstance().setServices(rendezVousService, patientService);

        int choixPrincipal;
        do {
            System.out.println("\nMENU PRINCIPAL");
            System.out.println("1. Gestion des patients");
            System.out.println("2. Gestion des rendez-vous");
            System.out.print("3. Quitter\n> ");

            choixPrincipal = sc.nextInt();
            sc.nextLine();

            switch (choixPrincipal) {
                case 1:
                    menuPatients();
                    break;
                case 2:
                    menuRendezVous();
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choixPrincipal != 3);

        sc.close();
    }

    private void menuPatients() {
        int choix;
        do {
            System.out.println("\nGestion des patients");
            System.out.println("1. Créer un patient");
            System.out.println("2. Lister les patients");
            System.out.print("3. Retour\n> ");

            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    var patient = PatientView.getInstance().readPatient();
                    patientService.creerPatient(patient);
                    System.out.println("Patient créé avec succès !");
                    break;
                case 2:
                    var patients = patientService.listerPatients();
                    PatientView.getInstance().affichePatient(patients);
                    break;
                case 3:
                    System.out.println("Retour au menu principal");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 3);
    }

    private void menuRendezVous() {
        int choix;
        do {
            RendezVousView.getInstance().afficherMenuRendezVous();
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1:
                    var rdv = RendezVousView.getInstance().readRendezVous();
                    if (rdv != null) {
                        rendezVousService.creerRendezVous(rdv);
                        System.out.println("Rendez-vous créé avec succès !");
                    }
                    break;
                case 2:
                    var tousRdv = rendezVousService.listerRendezVous();
                    RendezVousView.getInstance().afficherRendezVous(tousRdv);
                    break;
                case 3:
                    System.out.print("ID du patient: ");
                    Long patientId = sc.nextLong();
                    sc.nextLine();
                    var rdvPatient = rendezVousService.listerRendezVousParPatient(patientId);
                    RendezVousView.getInstance().afficherRendezVous(rdvPatient);
                    break;
                case 4:
                    System.out.print("Date (JJ/MM/AAAA): ");
                    String dateStr = sc.nextLine();
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate date = LocalDate.parse(dateStr, formatter);
                        var rdvParDate = rendezVousService.listerRendezVousParDate(date);
                        RendezVousView.getInstance().afficherRendezVous(rdvParDate);
                    } catch (DateTimeParseException e) {
                        System.out.println("Format de date invalide. Utilisez JJ/MM/AAAA");
                    }
                    break;
                case 5:
                    System.out.print("ID du rendez-vous à confirmer: ");
                    Long idConfirmer = sc.nextLong();
                    rendezVousService.confirmerRendezVous(idConfirmer);
                    System.out.println("Rendez-vous confirmé !");
                    break;
                case 6:
                    System.out.print("ID du rendez-vous à annuler: ");
                    Long idAnnuler = sc.nextLong();
                    rendezVousService.annulerRendezVous(idAnnuler);
                    System.out.println("Rendez-vous annulé !");
                    break;
                case 7:
                    System.out.println("Retour au menu principal");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choix != 7);
    }
}