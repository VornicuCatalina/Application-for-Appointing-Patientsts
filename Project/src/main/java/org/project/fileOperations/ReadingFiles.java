package org.project.fileOperations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.project.entities.Doctor;
import org.project.entities.Patient;
import org.project.entities.Timetable;
import org.project.functions.FunctionsDB;
import org.project.functions.FunctionsDoc;
import org.project.repositories.DoctorRepository;
import org.project.repositories.PatientRepository;
import org.project.repositories.TimetableRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ReadingFiles {
    //private String positionFolder = "C:\\Users\\User\\Documents\\Project-Java\\Project\\src\\main\\java\\org\\project\\files\\";
    private String positionFolder = "E:\\FACULTATE\\Advanced_Programming_(Java)\\Project\\Project-Java\\Project\\src\\main\\java\\org\\project\\files\\";

    public void createFile(String filename) {
        //creating file position
        String file = positionFolder + filename;

        //checking if it exists
        File fileJson = new File(file);
        if (fileJson.exists()) {
            System.out.println("The file already exists!!!");
            return;
        }

        try {
            if (fileJson.createNewFile()) {
                System.out.println("File was created!");
            } else {
                System.out.println("Some error occurred...");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readJSON(String filename) {
        //creating file position
        String file = positionFolder + filename;

        //checking if the file exists
        File fileJson = new File(file);
        if (!fileJson.exists()) {
            System.out.println("Create the file first, by writing 'create <name.extension>'");
            return;
        }

        //checking if it is empty
        if (fileJson.length() == 0) {
            System.out.println("IT IS AN EMPTY FILE!");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(file);
        try {
            MainJSONStructure mainJSONStructure = objectMapper.readValue(fileJson, MainJSONStructure.class);
            mainJSONStructure.savingPeopleInDB();
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCSV(String filename) throws CsvValidationException, IOException {
        String filePath = positionFolder + filename;

        File fileCSV = new File(filePath);
        if (!fileCSV.exists()) {
            System.out.println("Create the file first, by writing 'create <name.extension>'");
            return;
        }

        if (fileCSV.length() == 0) {
            System.out.println("IT IS AN EMPTY FILE!");
            return;
        }

        CSVReader reader = new CSVReader(new FileReader(filePath));
        String[] line;
        while ((line = reader.readNext()) != null) {
            String personType = line[0];
            if (personType.equals("doctor")) {
                System.out.println("Doctor detected!");
                Doctor doctor = new Doctor(line[1]);
                DoctorRepository doctorRepository = new DoctorRepository();
                //doctorRepository.save(doctor);
                TimetableRepository timetableRepository = new TimetableRepository();
                FunctionsDoc functionsDoc = new FunctionsDoc();
                FunctionsDB functionsDB = new FunctionsDB();
                String[] days = line[2].split(",");
                int i=0;
                for (String day : days) {
                    if (day.equals("-")) {
                        i++;
                        continue;
                    }
                    String[] hours = day.split("-");
                    ArrayList<Date> workingHours = functionsDoc.createAll(hours[0], hours[1]);
                    Timetable timetable = new Timetable(i, workingHours);
                    i++;
                    timetableRepository.save(timetable);
                    doctor.addTimetableList(timetable);
                    doctorRepository.save(doctor);
                }
            }
            else if (personType.equals("patient")) {
                System.out.println("Patient detected!");
                Patient patient = new Patient();
                patient.setName(line[1]);
                String[] preferences=line[2].split(",");
                ArrayList<Integer> preferencesList = new ArrayList<>();
                for (String preference : preferences) {
                    preferencesList.add(Integer.parseInt(preference));
                }
                patient.setPreferences(preferencesList);
                PatientRepository patientRepository = new PatientRepository();
                patientRepository.save(patient);
            }
            else {
                System.out.println("ERROR: The person type is not correct!");
            }
        }
    }
}
