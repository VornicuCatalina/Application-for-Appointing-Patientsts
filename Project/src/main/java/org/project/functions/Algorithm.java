package org.project.functions;

import org.project.entities.Doctor;
import org.project.entities.FinalResult;
import org.project.entities.Patient;
import org.project.entities.Timetable;
import org.project.repositories.DoctorRepository;
import org.project.repositories.FinalResultRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Algorithm {
    Patient patient;
    private int millis = 60000;
    private ArrayList<Date> helperDates;
    private DoctorRepository doctorRepository = new DoctorRepository();
    private FinalResultRepository finalResultRepository = new FinalResultRepository();

    public void setPatient(Patient patient) {
        this.patient = patient;
        helperDates = new ArrayList<>();
    }

    public void addingInTable() {
        Date date = patient.getDateSignUp();
        long time = date.getTime() + millis * 60 * 24;
        Date newDate = new Date(time);
        System.out.println(date + " " + newDate);
        int day_patient = (date.getDay() + 1) % 6;

        //getting doctors in order
        /*for(Integer i:patient.getPreferences()){
            //we will see if the first doctor is available in the next 3 days
        }*/
    }

    public Date returnAcceptedDate(Date date, int dayPatient, int idDoctor) {
        Doctor doctor = doctorRepository.findById(idDoctor);
        List<Timetable> result = doctor.getTimetableList();
        int ok = 1;
        int counter = 0;
        while (true) {
            for (Timetable t : result) {
                if (t.getDay() == dayPatient) {
                    List<FinalResult> finalResults = finalResultRepository.findByIdDoc(idDoctor);
                    List<String> hours = t.getTimetable();
                    //unu cate un
                    Date d = new Date(hours.get(0));
                    for (FinalResult f : finalResults) {
                        Date checker = f.getDate();
                        if (checker.getDay() == dayPatient) {
                            if (checker.getMonth() == date.getMonth() &&
                                    checker.getDate() == date.getDate() &&
                                    checker.getHours() == date.getHours() &&
                                    checker.getMinutes() == date.getMinutes()) {
                                ok = 0;
                                break;
                            }
                        }


                    }
                    if (ok == 1 && counter < 3) {
                        return date;
                    } else if (ok == 1) {
                        helperDates.add(date);
                        return null;
                    }
                }
            }
            dayPatient = (dayPatient + 1) % 6;
            long time = date.getTime() + millis * 60 * 24;
            date = new Date(time);
            counter++;
        }
    }

    public ArrayList<Date> getHelperDates() {
        return helperDates;
    }
}
