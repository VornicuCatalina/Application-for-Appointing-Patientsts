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

import javax.print.Doc;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Main {
    public static void main(String[] args) throws ParseException {
        DoctorRepository doctorRepository=new DoctorRepository();
        PatientRepository patientRepository=new PatientRepository();
        TimetableRepository timetableRepository= new TimetableRepository();
        FinalResultRepository finalResultRepository = new FinalResultRepository();

        //FinalResult finalResult = new FinalResult(6,2,new Date());
        //finalResultRepository.save(finalResult);

        /*Doctor doctor = new Doctor("George");
        doctorRepository.save(doctor);
        System.out.println(doctor.getId());*/
        Patient patient = patientRepository.findById(2);

        /*ArrayList<Date> dates = new ArrayList<>();
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

        //FunctionsDB functionsDB = new FunctionsDB();
        //functionsDB.showPatient(2);
        //functionsDB.showDoctor(1);
        Algorithm algorithm = new Algorithm();
        algorithm.setPatient(patient);
        algorithm.returnAcceptedDate(new Date(),new Date().getDay(),6);
        //Date d = new Date("Wed May 24 14:16:57 EEST 2023");
        //System.out.println(algorithm.getHelperDates().get(0).toString());
        //System.out.println(algorithm.returnAcceptedDate(new Date(),new Date().getDay(),1).toString());

        /*ArrayList<Date> dates = new ArrayList<>();
        dates.add(new Date());
        TimetableRepository timetableRepository = new TimetableRepository();
        Timetable timetable = new Timetable(0, dates);
        DoctorRepository doctorRepository = new DoctorRepository();
        Doctor doctor = doctorRepository.findById(6);
        doctor.addTimetableList(timetable);
        timetableRepository.save(timetable);
        doctorRepository.save(doctor);
        System.out.println(doctor.getTimetableList().get(0).getId());*/
        //String date="12/12/2023";
        /*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Date d=new Date();
        System.out.println(formatter.format(d));
        Date d2=new Date("29/05/2023 09:20");
        System.out.println(d2);*/
        //System.out.println(date);
        //Date date1=formatter.parse(date);
    }
}
