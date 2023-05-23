package org.project;

import org.project.entities.Doctor;
import org.project.entities.Patient;
import org.project.entities.Timetable;
import org.project.repositories.DoctorRepository;
import org.project.repositories.PatientRepository;
import org.project.repositories.TimetableRepository;

import java.util.ArrayList;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        /*DoctorRepository doctorRepository=new DoctorRepository();
        PatientRepository patientRepository=new PatientRepository();
        TimetableRepository timetableRepository= new TimetableRepository();

        Doctor doctor = new Doctor("George");
        doctorRepository.save(doctor);

        ArrayList<Integer> docs = new ArrayList<>();
        docs.add(1);
        Patient patient = new Patient("Emilian",docs );
        patientRepository.save(patient);

        ArrayList<Date> dates = new ArrayList<>();
        dates.add(new Date());
        Timetable timetable = new Timetable(1, 0 , dates);
        timetableRepository.save(timetable);*/

        FunctionsDoc functionsDoc= new FunctionsDoc();
        System.out.println(functionsDoc.getTimetable(new Date(2000,11,2,10,20),new Date(2000,11,2,21,30)));
    }
}
