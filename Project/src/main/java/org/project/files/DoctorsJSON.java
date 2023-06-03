package org.project.files;

public class DoctorsJSON {
    private String name;
    private TimetablesJSON timetable;

    //GETTERS

    public String getName() {
        return name;
    }

    public TimetablesJSON getTimetable() {
        return timetable;
    }

    //SETTERS

    public void setName(String name) {
        this.name = name;
    }

    public void setTimetable(TimetablesJSON timetable) {
        this.timetable = timetable;
    }
}
