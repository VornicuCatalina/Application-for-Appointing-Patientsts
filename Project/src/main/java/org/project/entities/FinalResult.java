package org.project.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "assignations", schema = "public")
@NamedQueries({
        @NamedQuery(name = "Result.findByIdDoc", query = "select e from FinalResult e where e.id_doctor = ?1"),
        @NamedQuery(name = "Result.findByIdPatient", query = "select e from FinalResult e where e.id_patient = ?1")
})
public class FinalResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "id")
    @Column(name = "id_assignation")
    private int id;

    @Column(name = "id_doctor")
    private int id_doctor;

    @Column(name = "id_patient")
    private int id_patient;

    @Column(name = "date_assignation")
    private Date date;

    public FinalResult() {
    }

    public FinalResult(int id_doctor, int id_patient, Date date) {
        this.id_doctor = id_doctor;
        this.id_patient = id_patient;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_doctor() {
        return id_doctor;
    }

    public int getId_patient() {
        return id_patient;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

    public void setId_patient(int id_patient) {
        this.id_patient = id_patient;
    }
}
