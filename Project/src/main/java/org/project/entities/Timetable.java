package org.project.entities;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "timetable", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Timetable.findByDay", query = "select e from Timetable e where e.day=?1")
})
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "id")
    int id;

    @Column(name = "day")
    int day;

    @Column(name = "timetable", columnDefinition = "text[]")
    ArrayList<String> timetable;

    public Timetable() {
    }

    public Timetable(int day, ArrayList<Date> timetable) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        this.day = day;
        this.timetable = new ArrayList<>();
        for (Date d : timetable) {
            this.timetable.add(formatter.format(d).toString());
        }
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getTimetable() {
        return timetable;
    }

    public int getDay() {
        return day;
    }
}
