package org.project.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors" , schema = "public")

public class Doctor {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY , generator = "id")
    @Column(name = "id_doctor")
    private int id;

    @Column(name = "fullName")
    String name;

    public Doctor(){
    }

    public Doctor(String name){
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @OneToMany
    @JoinColumn()
    List<Timetable> timetableList = new ArrayList<>();
}
