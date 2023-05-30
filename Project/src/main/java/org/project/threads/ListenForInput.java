package org.project.threads;

import org.project.databases.Database;
import org.project.entities.Patient;
import org.project.functions.FunctionsPatients;
import org.project.repositories.DoctorRepository;
import org.project.repositories.PatientRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ListenForInput implements Runnable {
    FunctionsPatients functionsPatients = new FunctionsPatients();
    PatientRepository patientRepository = new PatientRepository();
    DoctorRepository doctorRepository = new DoctorRepository();

    public void run() {
        boolean ok = true;
        Scanner scanner = new Scanner(System.in);
        while (ok) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Exiting...");
                ok = false;
            } else if (input.equals("help")) {
                System.out.println("Here are the available commands:" +
                        "\n\texit - exits the program" +
                        "\n\thelp - shows the available commands" +
                        "\n\tshow doctors - shows the available doctors" +
                        "\n\tshow patients - shows the available patients" +
                        "\n\tshow [id] [patient|doctor]" +
                        "\n\tregister patient [list of preferences] [fullName]" +
                        "\n\t\t\t[list of preferences] is not allowed to have ' ' between ids : ex: 1,2,3 (correct) 1, 3, 4 (wrong");
            } else if (input.equals("show doctors")) {
                System.out.println("Here are the available doctors:");
                try (Statement stmt = Database.getConnection().createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getString("id_doctor") + " NAME: " + rs.getString("fullName"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (input.equals("show patients")) {
                System.out.println("Here are the available patients:");
                try (Statement stmt = Database.getConnection().createStatement()) {
                    ResultSet rs = stmt.executeQuery("SELECT * FROM patients");
                    while (rs.next()) {
                        System.out.println("ID: " + rs.getString("id_patient") + " NAME: " + rs.getString("fullName"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (input.startsWith("register patient")) {
                String[] splitCommand = input.split(" ");
                ArrayList<Integer> idDocs = functionsPatients.listOfDoctorsId(splitCommand[2]);
                StringBuilder helper = new StringBuilder();
                for (int i = 3; i < splitCommand.length; i++) {
                    helper.append(splitCommand[i]);
                    helper.append(" ");
                }
                String fullName = helper.toString();
                if (idDocs.isEmpty()) {
                    System.out.println("Sorry but the preferences are invalid!\n" +
                            "Check the list of doctors : 'show doctors'");
                } else {
                    Patient patient = new Patient(fullName, idDocs);
                    patientRepository.save(patient);
                }
            } else {
                System.out.println("Invalid command! Type help for the available commands.");
            }
        }
        System.exit(0);
    }
}
