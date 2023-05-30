package org.project;

import org.project.entities.Doctor;
import org.project.entities.FinalResult;
import org.project.entities.Patient;
import org.project.entities.Timetable;
import org.project.functions.Algorithm;
import org.project.functions.FunctionsDB;
import org.project.functions.FunctionsDoc;
import org.project.repositories.DoctorRepository;
import org.project.repositories.FinalResultRepository;
import org.project.repositories.PatientRepository;
import org.project.repositories.TimetableRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Main {
    public static void main(String[] args) throws ParseException {
        DoctorRepository doctorRepository = new DoctorRepository();
        PatientRepository patientRepository = new PatientRepository();
        TimetableRepository timetableRepository = new TimetableRepository();
        FinalResultRepository finalResultRepository = new FinalResultRepository();
        /*Patient patient=patientRepository.findById(2);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(6);
        patient.setPreferences(arrayList);
        patientRepository.save(patient);*/
        //System.out.println(patient.getDateSignUp().getHours());

        /*FinalResult finalResult = new FinalResult(1,2,new Date());
        finalResultRepository.save(finalResult);

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

        /*FunctionsDB functionsDB = new FunctionsDB();
        functionsDB.showDoctor(6);
        functionsDB.showPatient(2);*/

        Thread listenForInput = new Thread(new ListenForInput());
        listenForInput.start();

        /*FinalResultRepository finalResultRepository = new FinalResultRepository();
        FinalResult finalResult = new FinalResult(6,5, new Date());
        finalResultRepository.save(finalResult);*/

        /*Algorithm algorithm = new Algorithm();
        algorithm.setPatient(patient);
        algorithm.addingInTable();*/
        /*Date d = algorithm.returnAcceptedDate(new Date(),6);
        if(d==null){
            System.out.println("NULL");
            System.out.println(algorithm.getHelperDates().get(0));
        }
        else{
            System.out.println(d.toString());
        }*/

       // Date d = new Date("29/05/2024 09:33");
        /*SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date d=sdf.parse("29/05/2023 09:33");
        System.out.println(d.toString());*/
    }
}
