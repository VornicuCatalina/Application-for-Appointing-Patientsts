package org.project.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "timetable" , schema = "public")
public class Timetable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY , generator = "id")
    @Column(name = "id")
    int id;

    @Column(name = "id_doc")
    int id_doc;

    @Column(name = "day")
    int day;

    @Column(name = "timetable" , columnDefinition = "text[]")
    ArrayList<String> timetable;

    public Timetable(){}

    public Timetable(int id_doc,int day , ArrayList<Date> timetable){
        this.id_doc=id_doc;
        this.day=day;
        this.timetable=new ArrayList<>();
        for(Date d : timetable){
            this.timetable.add(d.toString());
        }
    }

}
