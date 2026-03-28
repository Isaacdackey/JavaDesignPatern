package org.example.views;

import org.example.entity.Patient;
import org.example.services.PatientService;

import java.util.List;
import java.util.Scanner;

public final class PatientView {

    private static PatientView instance;
    public static PatientView getInstance() {
        if (instance == null) {
            instance=new PatientView();
        }

        return instance;
    }

    private PatientView(){

    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    private PatientService patientService;
    private static Scanner sc;




    public void setScanner(Scanner sc) {
        this.sc = sc;
    }

    public Patient readPatient() {
        Patient patient = new Patient();
        System.out.print("Saisir les informations du Patient: ");
        System.out.print("Numero: ");
        patient.setNumero(sc.next());
        System.out.print("Nom et Prenom: ");
        sc.nextLine();
        patient.setNomComplet(sc.nextLine());
        System.out.print("Telephone: ");
        patient.setTel(sc.next());
        return patient;
    }


    public void affichePatient(List<Patient> patients) {
        patients.forEach((p)->{
            System.out.println(p);
        });
    }



}
