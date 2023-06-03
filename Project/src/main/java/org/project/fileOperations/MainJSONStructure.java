package org.project.fileOperations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.project.entities.Doctor;
import org.project.entities.Patient;
import org.project.entities.Timetable;
import org.project.functions.Algorithm;
import org.project.functions.FunctionsDoc;
import org.project.functions.FunctionsPatients;
import org.project.repositories.DoctorRepository;
import org.project.repositories.PatientRepository;
import org.project.repositories.TimetableRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainJSONStructure {
    @JsonProperty("doctors")
    private List<DoctorsJSON> doctorsJSONList;
    @JsonProperty("patients")
    private List<PatientsJSON> patientsJSONList;

    public MainJSONStructure() {
        doctorsJSONList = new ArrayList<>();
        patientsJSONList = new ArrayList<>();
    }

    //GETTERS
    public List<PatientsJSON> getPatientsJSONS() {
        return patientsJSONList;
    }

    public List<DoctorsJSON> getDoctorsJSONList() {
        return doctorsJSONList;
    }

    //SETTERS

    public void setDoctorsJSONList(List<DoctorsJSON> doctorsJSONList) {
        this.doctorsJSONList = doctorsJSONList;
    }

    public void setPatientsJSONS(List<PatientsJSON> patientsJSONS) {
        this.patientsJSONList = patientsJSONS;
    }

    //FUNCTIONS
    public void savingPeopleInDB() {
        savingDoctors();
        System.out.println("The doctors were saved. Write 'show doctors' for further information!");
        savingPatients();
        System.out.println("The patients were saved. Write 'show patients' for further information!");
    }

    private void savingDoctors() {
        //creating the doctor repository & a timetable repository
        DoctorRepository doctorRepository = new DoctorRepository();
        TimetableRepository timetableRepository = new TimetableRepository();

        //creating the doc functions class
        FunctionsDoc functionsDoc = new FunctionsDoc();

        //looping through the list of docs
        for (DoctorsJSON doctorsJSON : doctorsJSONList) {
            //creating each time a doctor entity
            Doctor doctor = new Doctor(doctorsJSON.getName());

            for (TimetablesJSON timetablesJSON : doctorsJSON.getTimetable()) {
                //creating the arraylist for dates
                ArrayList<Date> docTimetable = functionsDoc.createAll(timetablesJSON.getStart(), timetablesJSON.getFinish());

                //creating a timetable entity
                Timetable timetable = new Timetable(timetablesJSON.getDay(), docTimetable);

                //saving all
                timetableRepository.save(timetable);
                doctor.addTimetableList(timetable);
            }

            //saving the doc and his connection to his timetables
            doctorRepository.save(doctor);
        }
    }

    private void savingPatients() {
        //creating the patient repository
        PatientRepository patientRepository = new PatientRepository();

        //creating the patient functions class
        FunctionsPatients functionsPatients = new FunctionsPatients();

        //looping through the found patients in the json
        for (PatientsJSON patientsJSON : patientsJSONList) {
            //checking the list of preferences if it is good
            ArrayList<Integer> resultIds = functionsPatients.listOfDoctorsForJSONFile((ArrayList<Integer>) patientsJSON.getPreferences());

            //checking if it is null/empty
            if (resultIds.isEmpty()) {
                System.out.println("SORRY , but the list of doctors is wrong! No id matches a doctor.");
            } else {
                //creating the patient
                Patient patient = new Patient(patientsJSON.getName(), resultIds);

                //saving the patient
                patientRepository.save(patient);

                //assigning him to a doctor
                Algorithm algorithm = new Algorithm();
                algorithm.setPatient(patient);
                algorithm.addingInTable();
            }
        }
    }
}
