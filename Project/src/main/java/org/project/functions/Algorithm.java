package org.project.functions;

import org.project.entities.Doctor;
import org.project.entities.FinalResult;
import org.project.entities.Patient;
import org.project.entities.Timetable;
import org.project.repositories.DoctorRepository;
import org.project.repositories.FinalResultRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Algorithm {
    Patient patient;
    private int millis = 60000;
    private ArrayList<Date> helperDates;
    private DoctorRepository doctorRepository = new DoctorRepository();
    private FinalResultRepository finalResultRepository = new FinalResultRepository();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

    public void setPatient(Patient patient) {
        this.patient = patient;
        helperDates = new ArrayList<>();
    }

    public void addingInTable() {
        Date date = patient.getDateSignUp();
        long time = date.getTime() + millis * 60 * 24;
        Date newDate = new Date(time);
        System.out.println(date + " " + newDate);
        boolean verify = false;

        //getting doctors in order
        for (Integer i : patient.getPreferences()) {
            //we will see if the first doctor is available in the next 3 days
            Date result = returnAcceptedDate(newDate, i);

            if (result != null) {
                FinalResult finalResult = new FinalResult(i, patient.getId(), result);
                verify = true;
                finalResultRepository.save(finalResult);
                break;
            }
        }

        if (!verify) {
            Date result = Collections.min(helperDates);
            int docIdx = helperDates.indexOf(result);
            FinalResult finalResult = new FinalResult(patient.getPreferences().get(docIdx), patient.getId(), result);
            finalResultRepository.save(finalResult);
        }
    }

    private Date returnAcceptedDate(Date date, int idDoctor) {
        /*
        1. Searching for the appointments of the specific doctor (idDoctor) in the table 'assignations'
        2. Searching in table 'timetable' for the specific day that the patient starts looking for its checkup "dayPatient"
        3. When the day is found (if it exists) -> We get the timetable of that day by calling '.getTimetable()'
        4. We loop through the available hours of the specific day
        5. We loop through the result from point 1, to see if he has an available hour during that day
            - same day in the month, same day of the week and same month such as in 'date'
            - same hour and same minutes
        6. IF 3 days are not enough for finding an available hour/time -> return null; otherwise return date;
        7. Moreover, the loop will keep continuing until we find an available time, for further complications
         */

        Date resultDate = null;
        int dayPatient = date.getDay();
        //1.
        List<FinalResult> finalResults = finalResultRepository.findByIdDoc(idDoctor);

        //2.
        Doctor doctor = doctorRepository.findById(idDoctor);
        List<Timetable> result = doctor.getTimetableList();
        int ok = 1;
        int counter = 0;
        int found_day = 0;
        while (true) {
            for (Timetable t : result) {
                if (t.getDay() == dayPatient) {
                    //3.
                    List<String> hours = t.getTimetable();

                    //4.
                    for (String hour : hours) {
                        Date date1;
                        try {
                            date1 = sdf.parse(hour);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }

                        //5.
                        for (FinalResult f : finalResults) {
                            Date checker = f.getDate();
                            if (checker.getDay() == dayPatient) {
                                if (checker.getMonth() == date.getMonth() &&
                                        checker.getDate() == date.getDate() &&
                                        checker.getHours() == date1.getHours() &&
                                        checker.getMinutes() == date1.getMinutes()) {
                                    ok = 0;
                                    break;
                                }
                            }
                        }

                        //6.
                        date1.setMonth(date.getMonth());
                        date1.setDate(date.getDate());
                        if (ok == 1 && counter < 3) {
                            return date1;
                        } else if (ok == 1) {
                            //7.
                            helperDates.add(date1);
                            return null;
                        }
                        ok = 1;
                    }
                }
            }
            dayPatient = (dayPatient + 1) % 7;
            long time = date.getTime() + millis * 60 * 24;
            date = new Date(time);
            counter++;
        }
    }

    private ArrayList<Date> getHelperDates() {
        return helperDates;
    }
}
