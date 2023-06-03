package org.project.threads;

import org.project.databases.Database;
import org.project.entities.Doctor;
import org.project.entities.Patient;
import org.project.entities.Timetable;
import org.project.fileOperations.ReadingFiles;
import org.project.functions.Algorithm;
import org.project.functions.FunctionsDB;
import org.project.functions.FunctionsDoc;
import org.project.functions.FunctionsPatients;
import org.project.repositories.DoctorRepository;
import org.project.repositories.PatientRepository;
import org.project.repositories.TimetableRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ListenForInput implements Runnable {
    FunctionsPatients functionsPatients = new FunctionsPatients();
    PatientRepository patientRepository = new PatientRepository();
    DoctorRepository doctorRepository = new DoctorRepository();
    TimetableRepository timetableRepository = new TimetableRepository();
    FunctionsDB functionsDB = new FunctionsDB();
    FunctionsDoc functionsDoc = new FunctionsDoc();
    Thread deletingStuff;
    Thread updatingStuff;

    ReadingFiles readingFiles = new ReadingFiles();
    ArrayList<String> days = new ArrayList<>();

    public void setThreads(Thread t, Thread t2) {
        deletingStuff = t;
        updatingStuff = t2;
    }

    private void initialiseDays() {
        days.add("Sunday");
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
    }

    public void run() {
        boolean ok = true;
        initialiseDays();
        Scanner scanner = new Scanner(System.in);
        while (ok) {
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                System.out.println("Exiting...");
                deletingStuff.interrupt();
                updatingStuff.interrupt();
                ok = false;
            } else if (input.equals("help")) {
                help();
            } else if (input.equals("show doctors")) {
                showDoctors();
            } else if (input.equals("show patients")) {
                showPatients();
            } else if (input.startsWith("show statistics")) {
                showStatistics(input);
            } else if (input.startsWith("show")) {
                showPerson(input);
            } else if (input.startsWith("register patient")) {
                registerPatient(input);
            } else if (input.startsWith("register doctor")) {
                registerDoctor(input);
            } else if (input.startsWith("timetable")) {
                timetableRegistrations(input);
            } else if (input.startsWith("file")) {
                if (input.contains(".json")) {
                    readingJSON(input);
                } else if (input.contains(".json")) {
                    //csv func
                } else {
                    System.out.println("Wrong extension...");
                }
            } else if (input.startsWith("create") && (input.contains(".json") || input.contains(".csv"))) {
                creatingFile(input);
            } else {
                System.out.println("Invalid command! Type help for the available commands.");
            }
        }
        System.exit(0);
    }

    private void help() {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("************************************* COMMANDS *************************************");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Here are the available commands:" +
                "\n\texit - exits the program" +
                "\n\thelp - shows the available commands" +
                "\n\tshow doctors - shows the available doctors" +
                "\n\tshow patients - shows the available patients" +
                "\n\tshow [id] [patient|doctor]" +
                "\n\tshow statistics [id] - shows the weekly stats for the doctor with the mentioned id" +
                "\n\tregister patient [list of preferences] [fullName]");
        System.out.println("\tcreate <fileName.extension>" +
                "\n\tfile <fileName.extension>");
        System.out.println("\tregister doctor [fullName]" +
                "\n\ttimetable [id_doctor] [day] [hour_start] [hour_finish]");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("************************************* DETAILS *************************************");
        System.out.println("[list of preferences] is not allowed to have ' ' between ids : ex: 1,2,3 (correct) 1, 3, 4 (wrong)");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("[day] - a number between 0-6 [where 0 is Sunday] " +
                "\n\tOR" +
                "\n\twrite the day with a Capital letter, \texample: Monday (correct) , monday (wrong)" +
                "\n[hour_start] & [hour_finish] have the following format:" +
                "\n[hour]:[minutes] -> hour a number between 0-23 & -> minutes a number between 0-59");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("file <fileName.extension> To populate the database with new information (doctors & patients)");
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("AVAILABLE EXTENSIONS:\t .json \t .csv");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void showDoctors() {
        System.out.println("Here are the available doctors:");
        try (Statement stmt = Database.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id_doctor") + " NAME: " + rs.getString("fullName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showPatients() {
        System.out.println("Here are the available patients:");
        try (Statement stmt = Database.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM patients");
            while (rs.next()) {
                System.out.println("ID: " + rs.getString("id_patient") + " NAME: " + rs.getString("fullName"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showStatistics(String input) {
        String[] splitCommand = input.split(" ");
        int id = Integer.parseInt(splitCommand[2]);
        FunctionsDB functionsDB = new FunctionsDB();
        String result = functionsDB.checkStatistics(id);
        System.out.println(result);
    }

    private void showPerson(String input) {
        String[] splitCommand = input.split(" ");
        int id = Integer.parseInt(splitCommand[1]);
        if (splitCommand[2].equals("patient")) {
            FunctionsDB functionsDB = new FunctionsDB();
            functionsDB.showPatient(id);
        } else if (splitCommand[2].equals("doctor")) {
            FunctionsDB functionsDB = new FunctionsDB();
            functionsDB.showDoctor(id);
        } else {
            System.out.println("Invalid command! Type help for the available commands.");
        }
    }

    private void registerPatient(String input) {
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
        } else if (fullName.isEmpty()) {
            System.out.println("Please write your name after writing the command 'register patient [list_of_preferences]' " +
                    "+ [fullName]");
            System.out.println("Example: register patient 1,2,3 Michele Teodor");
        } else {
            Patient patient = new Patient(fullName, idDocs);
            patientRepository.save(patient);
            System.out.println("This is your id: " + patient.getId());
            Algorithm algorithm = new Algorithm();
            algorithm.setPatient(patient);
            algorithm.addingInTable();
            System.out.println("You also got assigned to a doctor, check your timetable by writing 'show " + patient.getId() + " patient'");
        }
    }

    private void registerDoctor(String input) {
        String[] splitCommand = input.split(" ");
        StringBuilder helper = new StringBuilder();
        for (int i = 2; i < splitCommand.length; i++) {
            helper.append(splitCommand[i]);
            helper.append(" ");
        }
        String fullName = helper.toString();
        if (fullName.isEmpty()) {
            System.out.println("Please write your name after writing the command 'register doctor' + [fullName]");
            System.out.println("Example: register doctor Georg Brade");
        } else {
            Doctor doctor = new Doctor(fullName);
            doctorRepository.save(doctor);
            System.out.println("Now add your timetable please!" +
                    "\nWrite the commands:" +
                    "\ttimetable [your_id] [day] [start_hour] [finish_hour]");
            System.out.println("For further details write 'help'");
            System.out.println("This is your id: " + doctor.getId());
        }
    }

    private void timetableRegistrations(String input) {
        String[] splitCommand = input.split(" ");

        //0 -> commands timetable
        //1 -> id_doctor
        int idDoc = Integer.parseInt(splitCommand[1]);

        //verifying if it really exists
        int personType = functionsDB.checkExistence(idDoc);
        if (personType == 1) {
            //2 -> day
            int day;
            try {
                day = Integer.parseInt(splitCommand[2]);
            } catch (IllegalArgumentException e) {
                day = days.indexOf(splitCommand[2]);
            }
            //3 -> hour_start
            //4 -> hour_finish
            if (splitCommand[4].contains(":") && splitCommand[3].contains(":")) {
                ArrayList<Date> timetableDoc = functionsDoc.createAll(splitCommand[3], splitCommand[4]);
                Timetable timetable = new Timetable(day, timetableDoc);
                timetableRepository.save(timetable);
                Doctor doctor = doctorRepository.findById(idDoc);
                doctor.addTimetableList(timetable);
                doctorRepository.save(doctor);
                System.out.println("The timetable was registered");
            } else {
                System.out.println("INVALID HOURS! Example: 10:30 (correct)  ;  121312 (WRONG)!!!");
            }
        } else {
            System.out.println("We are sorry , but no doctor has that id!");
        }
    }

    private void readingJSON(String input) {
        String[] command = input.split(" ");
        //1.file
        //2. .json file
        readingFiles.readJSON(command[1]);
    }

    private void creatingFile(String input) {
        String[] command = input.split(" ");
        //1.file
        //2. name file
        readingFiles.createFile(command[1]);
    }
}
