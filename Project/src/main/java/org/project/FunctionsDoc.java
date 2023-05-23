package org.project;

import java.util.ArrayList;
import java.util.Date;

public class FunctionsDoc {
    private int millis = 60000;
    public ArrayList<Date> getTimetable(Date date1, Date date2){
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
}
