package org.project.functions;

import java.util.ArrayList;
import java.util.Date;

public class FunctionsDoc {
    private int millis = 60000;
    private Date date1proj;
    private Date date2proj;
    private ArrayList<Date> getTimetable(Date date1, Date date2){
        ArrayList<Date> solution = new ArrayList<>();
        long dateHelper = date1.getTime() + (30*millis);
        int hours = date2.getHours();
        int mins = date2.getMinutes();
        boolean ok=false;
        solution.add(date1);
        while(true){
            date1 = new Date(dateHelper);
            int s_hour = date1.getHours();
            if(s_hour<hours){
                if(ok){
                    break;
                }
                solution.add(date1);
                dateHelper = dateHelper + (30*millis);
            }
            else if((s_hour == hours) && (date1.getMinutes()<mins)){
                solution.add(date1);
                dateHelper = dateHelper + (30*millis);
                ok=true;
            }
            else{
                break;
            }
        }
        return solution;
    }

    private void insertDates(String s1, String s2){
        String[] result_start=s1.split(":");
        String[] result_finish=s2.split(":");

        date1proj = new Date();
        date2proj = new Date();

        int hour_start = Integer.parseInt(result_start[0]);
        int min_start = Integer.parseInt(result_start[1]);

        int hour_finish = Integer.parseInt(result_finish[0]);
        int min_finish = Integer.parseInt(result_finish[1]);

        date1proj.setHours(hour_start);
        date1proj.setMinutes(min_start);
        date1proj.setHours(hour_start);
        date1proj.setMinutes(min_start);

    }

    public ArrayList<Date> createAll(String s1, String s2){
        insertDates(s1,s2);
        return getTimetable(date1proj,date2proj);
    }
}
