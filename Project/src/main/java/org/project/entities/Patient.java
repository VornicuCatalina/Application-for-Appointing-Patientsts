package org.project.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;

@Entity
@Table(name = "patients", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Patient.findById", query = "select e from Patient e where e.id = ?1")
}
)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "id_patient")
    private int id;

    @Column(name = "fullName")
    String name;

    @Column(name = "preferences", columnDefinition = "int[]")
    ArrayList<Integer> preferences;

    @Column(name = "dateSignUp")
    Date dateSignUp;

    @Column(name = "checked")
    boolean checked;

    public Patient() {
        this.checked = false;
        this.dateSignUp = new Date();
    }

    public Patient(String name, ArrayList<Integer> preferences) {
        this.name = name;
        this.preferences = preferences;
        this.dateSignUp = new Date();
        this.checked = false;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public Date getDateSignUp() {
        return dateSignUp;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setPreferences(ArrayList<Integer> preferences) {
        this.preferences = preferences;
    }

    public ArrayList<Integer> getPreferences() {
        return preferences;
    }
}
