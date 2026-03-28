package org.example;

import org.example.repository.*;
import org.example.services.*;
import org.example.views.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PUDEMANDERV");
        EntityManager em = emf.createEntityManager();

        Scanner sc = new Scanner(System.in);

        PatientRepository patientRepository = new PatientRepositoryImpl(em);
        RendezVousRepository rendezVousRepository = new RendezVousRepositoryImpl(em);
        DemandeRDVRepository demandeRDVRepository = new DemandeRDVRepositoryImpl(em);

        PatientService patientService = new PatientServiceImpl(patientRepository);
        RendezVousService rendezVousService = new RendezVousServiceImpl(rendezVousRepository);
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
                    menuPatients(patientService, sc);
                    break;
                case 2:
                    menuRendezVous(rendezVousService, patientService, sc);
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (choixPrincipal != 3);

        em.close();
        emf.close();
        sc.close();
    }

    private static void menuPatients(PatientService patientService, Scanner sc) {
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

    private static void menuRendezVous(RendezVousService rendezVousService,
                                       PatientService patientService, Scanner sc) {
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