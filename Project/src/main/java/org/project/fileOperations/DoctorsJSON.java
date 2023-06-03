package org.project.fileOperations;

import java.util.List;

public class DoctorsJSON {
    private String name;
    private List<TimetablesJSON> timetable;

    //GETTERS

    public String getName() {
        return name;
    }

    public List<TimetablesJSON> getTimetable() {
        return timetable;
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setTimetable(List<TimetablesJSON> timetable) {
        this.timetable = timetable;
    }

    public void addTimetable(TimetablesJSON timetablesJSON) {
        timetable.add(timetablesJSON);
    }
}
