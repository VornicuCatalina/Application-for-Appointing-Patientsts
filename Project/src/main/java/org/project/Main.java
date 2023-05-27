package org.project;

import org.project.entities.Doctor;
import org.project.entities.Patient;
import org.project.entities.Timetable;
import org.project.functions.FunctionsDoc;
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
        System.out.println(doctor.getId());*/

        /*ArrayList<Integer> docs = new ArrayList<>();
        docs.add(1);
        Patient patient = new Patient("Emilian",docs );
        patientRepository.save(patient);

        ArrayList<Date> dates = new ArrayList<>();
        dates.add(new Date());
        Timetable timetable = new Timetable( 0 , dates);
        timetableRepository.save(timetable);
        doctor.addTimetableList(timetable);
        doctorRepository.save(doctor);*/

        /*FunctionsDoc functionsDoc = new FunctionsDoc();
        ArrayList<Date> test = functionsDoc.createAll("10:30","14:30");
        for(Date d:test){
            System.out.println(d.toString());
        }*/
    }
}
