package org.project.files;

public class TimetablesJSON {
    private int day;
    private String start;
    private String finish;

    //GETTERS

    public int getDay() {
        return day;
    }

    public String getFinish() {
        return finish;
    }

    public String getStart() {
        return start;
    }

    //SETTERS

    public void setDay(int day) {
        this.day = day;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
